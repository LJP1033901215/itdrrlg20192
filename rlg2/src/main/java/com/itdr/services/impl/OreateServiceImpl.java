package com.itdr.services.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.*;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.OrderItemListVO;
import com.itdr.pojo.vo.OrderItemVO;
import com.itdr.pojo.vo.OrderVO;
import com.itdr.services.OreateService;
import com.itdr.utils.BigDecimalUtils;
import com.itdr.utils.PoToVoUtil;
import com.itdr.utils.PropertiesUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OreateServiceImpl implements OreateService {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    Order_itemMapper orderItemMapper;
    @Autowired
    ShippingMapper shippingMapper;

    //创建订单======================================================================================
    @Override
    public ServerResponse createOrder(Integer id, Integer shippingId) {
        //对传入的地址进行非空判断
        if (shippingId == null || shippingId <= 0) {
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }

        //创建商品集合,方便创建商品详情的时候使用
        List<Product> productList = new ArrayList<>();

        //查询所有购物车的信息=======================================================
        //查询ID选中的购物车信息,返回集合
        List<Cart> li = cartMapper.selectByUidAll(id);
        //判断订单的商品不可以少于1件
        if (li.size() == 0) {
            return ServerResponse.defeatedRs("至少选中一件商品");
        }
        //==========================================================================

        //计算总价===================================================================
        //创建变量,接受单个价格的之间的总和
        BigDecimal payment = new BigDecimal("0");
        //对查询处出来商品进行循环
        for (Cart cart : li) {
            //接收商品的ID
            Integer productId = cart.getProductId();
            //通过商品ID,查询商品的详细信息
            Product p = productMapper.selectByProductIdAll(productId);
            //对商品存在与否进行校验
            if (p == null){
                return ServerResponse.defeatedRs("商品不存在");
            }
            //对商品是否存在进行校验
            if (p.getStatus() != 1) {
                return ServerResponse.defeatedRs(p.getName() + "商品已将下架");
            }
            //对商品的库存校验
            if (cart.getQuantity() > p.getStock()){
                return ServerResponse.defeatedRs("商品数量不足");
            }
            //计算单个商品的价格
            BigDecimal mul = BigDecimalUtils.mul(p.getPrice().doubleValue(), cart.getQuantity().doubleValue());
            //将单个的价格赋值给变量patment
            payment = BigDecimalUtils.add(payment.doubleValue(), mul.doubleValue());
            //将查询到的商品信息,放入到集合中
            productList.add(p);
        }
        //==========================================================================
        //根据ID获取用户地址,方便一会进行封装
         Shipping shipping =  shippingMapper.selectByIdAndUid(shippingId,id);
        //判断用户的收获地址是否存在
        if (shipping == null){
            return ServerResponse.defeatedRs("用户收获地址不存在");
        }
        //创建订单对象,没有问题需要存储到数据库中====================================
        //创建一个存储订单的方法,将用户ID和地址ID传入进去============================
        Order order = this.getOrder(id, shippingId,payment);
        //将订单POJO传入到数据库
        int insert = orderMapper.insert(order);
        //判断是否成功
        if (insert <= 0) {
            return ServerResponse.defeatedRs(order.getOrderNo() + "订单创建失败");
        }

        //创建订单详情,并创建很多条订单详情============================================
        //使用创建订单详情的封装类
        List<Order_item> oredrItem = this.getOredrItem(id,  order.getOrderNo(), productList, li);
        //将订单详情的集合传入到数据库
        System.out.println(Arrays.toString(oredrItem.toArray()));
        int i = orderItemMapper.insertAll(oredrItem);
        if (i<=0){
            return ServerResponse.defeatedRs(order.getOrderNo()+"订单详情创建失败");
        }
        //插入成功后减少库存==============================================================
        //对封装的进行遍历
        for (Order_item item: oredrItem) {
            //对里面的商品进行遍历
            for (Product product : productList) {
                //比较商品的ID和订单详情的ID是否相同
                if (item!=null||product!=null) {
                    if (item.getProductId().equals(product.getId())) {
                        //商品的库存减去订单中的数量
                        Integer count = product.getStock() - item.getQuantity();
                        if (count < 0) {
                            return ServerResponse.defeatedRs("库存不能为负");
                        }
                        //将减去的数量放入到对象中
                        product.setStock(count);
                        //使用SQL更新商品数据库中的数量
                        int inter = productMapper.updateById(product.getStock(), product.getId());

                        //删除数据库数据
                        if (inter <= 0) {
                            return ServerResponse.defeatedRs("更新商品库存失败");
                        }
                    }
                }
                
            }
        }
        //对购物车进行清空的操作==================================================
        int cartDelete =  cartMapper.deleteAllByAndUid(li,id);
        if (cartDelete <= 0){
            return ServerResponse.defeatedRs("清空购物车失败");
        }
        //返回数据进行封装
        List<OrderItemVO>  itemVOList =this.getOrderItemVOList(oredrItem);
        OrderVO orderVO = PoToVoUtil.OrderTOOrderVO(shipping, order, itemVOList);


        return ServerResponse.successRs(orderVO);
    }


    //获取订单详情=======================================================================================
    @Override
    public ServerResponse getOrderCartProduct(Integer id, Long orderNo) {
        OrderItemListVO orderItemListVO=null;
        //分为两种情况，当有订单编号的时候
        if (orderNo != null){
            List<Order_item> li = orderItemMapper.selectByUidAndOrderNo(id,orderNo);
            if (li.size()<=0){
                return ServerResponse.defeatedRs("根据订单号未查到相关订单");
            }
            //直接返回封装好的数据
            List<OrderItemVO> products = this.getOrderItemVOList(li);

            //根据订单号去订单数据库中查询总价
            Order o  = orderMapper.selectByorderNo(orderNo);

            //创建封装类
            orderItemListVO = new OrderItemListVO();
            orderItemListVO.setOrderItemVOlist(products);
            orderItemListVO.setImageHost(PropertiesUitl.getValue("imageHost"));
            orderItemListVO.setProductTotalPrice(o.getPayment());



//            //根据商品订单详情中的ID查询商品
//            for (Order_item order_item : li) {
//                Product product = productMapper.selectByProductId(order_item.getProductId(), 0, 0, 0);
//                products.add(product);
//            }
        }else{
            /*当没有传入订单号时就可以直接从购物车内拿数据
             * 从而进行页面的展示
             * */
            List<Product> productList = new ArrayList<>();
            //查询购物车内得商品
            List<Cart> li = cartMapper.selectByUidAll(id);
            //判断订单的商品不可以少于1件
            if (li.size() == 0) {
                return ServerResponse.defeatedRs("至少选中一件商品");
            }

            //计算总价===================================================================
            //创建变量,接受单个价格的之间的总和
            BigDecimal payment = new BigDecimal("0");
            //对查询处出来商品进行循环
            for (Cart cart : li) {
                //接收商品的ID
                Integer productId = cart.getProductId();
                //通过商品ID,查询商品的详细信息
                Product p = productMapper.selectByProductIdAll(productId);
                //对商品存在与否进行校验
                if (p == null){
                    return ServerResponse.defeatedRs("商品不存在");
                }
                //对商品是否存在进行校验
                if (p.getStatus() != 1) {
                    return ServerResponse.defeatedRs(p.getName() + "商品已将下架");
                }
                //对商品的库存校验
                if (cart.getQuantity() > p.getStock()){
                    return ServerResponse.defeatedRs("商品数量不足");
                }
                //计算单个商品的价格
                BigDecimal mul = BigDecimalUtils.mul(p.getPrice().doubleValue(), cart.getQuantity().doubleValue());
                //将单个的价格赋值给变量patment
                payment = BigDecimalUtils.add(payment.doubleValue(), mul.doubleValue());
                //将查询到的商品信息,放入到集合中
                productList.add(p);
            }
            //通过调用getOredrItem方法对订单详情进行封装。
            List<Order_item> oredrItem = this.getOredrItem(id, null, productList, li);
            List<OrderItemVO> products = this.getOrderItemVOList(oredrItem);

            orderItemListVO = new OrderItemListVO();
            orderItemListVO.setOrderItemVOlist(products);
            orderItemListVO.setImageHost(PropertiesUitl.getValue("imageHost"));
            orderItemListVO.setProductTotalPrice(payment);
        }
        return ServerResponse.successRs(orderItemListVO);
    }

    //获取订单的列表=====================================================================================
    @Override
    public ServerResponse getList(Integer uid, Integer pageSize, Integer pageNum) {
        //引入分类插件
        PageHelper.startPage(pageNum,pageSize);

        List<Order> order =  orderMapper.selectByUid(uid);

       List<OrderVO> OrderVo = new ArrayList<>();
       //分页插件的是哟个
        PageInfo pf = new PageInfo(order);
        pf.setList(OrderVo);
        //分类插件引用结束


        for (Order order1 : order) {
            //根据订单查询订单详情
            List<Order_item> order_itemList = orderItemMapper.selectByUidAndOrderNo(uid,order1.getOrderNo());
//            for (Order_item order_item : order_itemList) {
////                //根据订单详情查找商品
////                Cart carts = cartMapper.selectByProductIdAndUserId(uid,order_item.getProductId());
////                Product products = productMapper.selectByProductId(order_item.getProductId(),0,0,0);
////                cartList.add(carts);
////                productList.add(products);
////            }
            //绑定数据
            List<OrderItemVO> orderItemVOList = getOrderItemVOList(order_itemList);
            //查询地址的数据
            Shipping shipping = shippingMapper.selectByIdAndUid( order1.getShippingId(),uid);
            //这里进行封装
            OrderVO orderVO = PoToVoUtil.OrderTOOrderVO(shipping, order1, orderItemVOList);
            OrderVo.add(orderVO);
        }

        return ServerResponse.successRs(pf);
    }

    //取消订单==========================================================================
    @Override
    public ServerResponse countermandOrder(Integer uid, Long orderNo) {
        if (orderNo==null||orderNo <= 0){
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        //查询有没有这个订单
        Order order =  orderMapper.selectByUidAndOrderNo(uid,orderNo);
        if (order==null){
            return ServerResponse.defeatedRs("无法查询到订单");
        }
        //修改订单状态
            order.setStatus(0);
        int i = orderMapper.updateByPrimaryKey(order);
        if (i<=0){
            return ServerResponse.defeatedRs("取消失败");
        }
        //修改商品中的数量
        List<Order_item> order_itemList = orderItemMapper.selectByUidAndOrderNo(uid, orderNo);
        for (Order_item orderItem : order_itemList) {
            Product product = productMapper.selectByProductId(orderItem.getProductId(), 0, 0, 0);
            Integer Stock = product.getStock()+ orderItem.getQuantity();
            int i1 = productMapper.updateById(Stock, product.getId());
            if (i1<=0){
                return ServerResponse.defeatedRs("更改商品失败");
            }
        }
        return ServerResponse.successRs("取消成功");
    }
    //删除订单============================================================================
    @Override
    public ServerResponse deleteOrder(Integer id, Long orderNo) {
        if (orderNo==null|| orderNo<=0){
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        Order order = orderMapper.selectByUidAndOrderNo(id, orderNo);
        if (order==null){
            return ServerResponse.defeatedRs("查无此订单");
        }
        if (order.getStatus()!=0&&order.getStatus()!=50&&order.getStatus()!=60){
            return ServerResponse.defeatedRs("非法删除订单");
        }
        int i = orderMapper.deleteByUidAndOrderNo(id,orderNo);
        int i2 = orderItemMapper.deleteByOrderNo(orderNo);
        if (i <= 0 || i2 <= 0){
            return ServerResponse.defeatedRs("删除失败");
        }
        return ServerResponse.successRs("删除成功");
    }


    //订单生成的方法,期间对数据进行封装
    public Order getOrder(Integer uid, Integer shippingId, BigDecimal bigDecimal) {
        //创建一个Order类
        Order o = new Order();
        o.setUserId(uid);//用户ID
        o.setOrderNo(this.getOrderNo());//订单号
        o.setShippingId(shippingId);//地址ID
        o.setPaymentType(Const.PaymentTypeEnum.ONLINE_PAYMENT.getCode());//支付类型
        o.setStatus(10);//订单状态
        o.setPayment(bigDecimal);
        //其他的暂时不考虑
        return o;
    }

    //生成订单编号的方法
    private Long getOrderNo() {
        //获取当前时间的毫秒数
        long l = System.currentTimeMillis();
        //使用MaTH.round方法创建
        //创建随机数+当前时间毫秒数
        long order = l + Math.round(Math.random() * 100);
        return order;
    }

    //创建点订单详情的封装类,可以复用
    private List<Order_item> getOredrItem(Integer uid,  Long orderNo, List<Product> productList, List<Cart> li) {
        //首先创建接收订单详情的集合
        List<Order_item> itemList = new ArrayList<>();


        //使用购物车事进行的封装

        //对订单中查询出来购物车详情进行遍历
        for (Cart cart : li) {
            //对商品详情进行遍历
            for (Product product : productList) {
                //判断商品的ID和购物车中的数据进行匹配,更加严谨
                if (product.getId().equals(cart.getProductId())) {
                    //创建订单详情的对象,以便存放对象
                    Order_item orderItem = new Order_item();
                    orderItem.setUserId(uid);//用户ID
                    orderItem.setOrderNo(orderNo);//订单号
                    orderItem.setProductId(product.getId());//商品ID
                    orderItem.setProductName(product.getName());//商品名称
                    orderItem.setProductImage(product.getMainImage());//商品图片
                    orderItem.setCurrentUnitPrice(product.getPrice());//商品单价
                    orderItem.setQuantity(cart.getQuantity());//数量
                    orderItem.setTotalPrice(BigDecimalUtils.mul(cart.getQuantity().doubleValue(), product.getPrice().doubleValue()));//单个单价
                    orderItem.setCreateTime(cart.getCreateTime());//创建时间
                    orderItem.setUpdateTime(cart.getUpdateTime());//更新时间
                    //将详情存放到集合中
                    itemList.add(orderItem);
                }
            }
        }
        //返回集合
        return itemList;
    }





    //这个方法可以返回订单详情得封装类
    public List<OrderItemVO> getOrderItemVOList(List<Order_item> oredrItem){
        List<OrderItemVO>  itemVOList = new ArrayList<>();
        for (Order_item order_item : oredrItem) {
            OrderItemVO orderItemVO = PoToVoUtil.orderItemTOorderItemVO(order_item);
            itemVOList.add(orderItemVO);
        }
        return itemVOList;
    }

}
