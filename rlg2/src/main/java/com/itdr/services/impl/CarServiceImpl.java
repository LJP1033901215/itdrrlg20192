package com.itdr.services.impl;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CartMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.CartProductVO;
import com.itdr.pojo.vo.CartVO;
import com.itdr.services.CartService;
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
public class CarServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    //添加一个商品到购物车================================================================================
    @Override
    public ServerResponse addList(Integer productId, Integer count, Integer uid) {
        //进行非空判断
        if (productId == null || productId <= 0 || count == null || count <= 0) {
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }

        //通过商品ID和用户ID查询购物车内的商品
        Cart ct = cartMapper.selectByProductIdAndUserId(productId, uid);

        //判断库存的数据是否足够
        Product product = productMapper.selectByProductId(productId, 0, 0, 0);
        if (product.getStock() < count) {
            return ServerResponse.defeatedRs("库存不足");
        }

        //判断是否存在相同的商品，如果相同，数量+1，如果不同则重新创建
        if (ct != null) {
            //拿到的数据放入数据库中
            ct.setQuantity(count);
            //将订单对象放入到数据库中
            int i = cartMapper.updateByPrimaryKeySelective(ct);
            System.out.println(i);
        } else {
            //重新创建
            //创建一个新的对象
            Cart ct2 = new Cart();
            //将数据封装到Cart对象中
            ct2.setProductId(productId);
            ct2.setUserId(uid);
            ct2.setQuantity(count);
            //直接向数据库传入对应的对象，创建新的购物车数据
            int insert = cartMapper.insert(ct2);
        }
//        //对返回的数据进行封装
//        CartVO cartVO = getCartVO(uid);
//
//        //返回CartVO对象
//        return ServerResponse.successRs(cartVO);
        return listCart(uid);//方法的复用
    }


    //创建一个对封装的方法===================================================================================
    public CartVO getCartVO(Integer uid) {
        //创建封装的类的对象
        CartVO cartVO = new CartVO();
        //创建计算金额得BigDecimal，准备计算金额
        BigDecimal cartTotalPrice = new BigDecimal("0");
        //创建一个存放CartProductvo的对象(对数据进行的封装)
        List<CartProductVO> cartProductVOS = new ArrayList<CartProductVO>();

        //查询当前用户购物车的所有信息
        List<Cart> li = cartMapper.selectByUid(uid);
        //判断查询到的信息不为0
        if (li.size() != 0) {
            //对查到的信息进行遍历
            for (Cart cart : li) {
                //通过li集合中的pid查询商品
                Product product = productMapper.selectByProductId(cart.getProductId(), 0, 0, 0);

                //将用户的购物车中的信息和用户购物车中对应的商品传入到封装工具中进行封装，并返回CartProductVO
                CartProductVO one = PoToVoUtil.getOne(cart, product);

                //判断是否被选中，选中了执行加金额的操作
                if (cart.getChecked() == Const.Cart.CHECK) {
                    //计算购物车总结
                    cartTotalPrice = BigDecimalUtils.add(cartTotalPrice.doubleValue(), one.getProductTotalPrice().doubleValue());
                }
                //将数据添加到List集合中
                cartProductVOS.add(one);
            }
        }
        //对CartVO进行封装
        cartVO.setCartProductVOS(cartProductVOS);//查询到的集合
        cartVO.setAllChecked(this.checkAll(uid));//是否全选中
        cartVO.setCartTotalPrice(cartTotalPrice);//总金额
        cartVO.setImageHost(PropertiesUitl.getValue("imageHost"));//图片路径
        return cartVO;//返回数据
    }

    //判断用户购物车是否全选=========================================================================
    public boolean checkAll(Integer uid) {
        //判断用户是否是全选
        int i = cartMapper.selectByUidCheck(uid, Const.Cart.UNCHECK);
        if (i == 0) {
            return true;
        } else {
            return false;
        }
    }

    //购物车List列表=================================================================================
    @Override
    public ServerResponse<Product> listCart(Integer id) {
        CartVO cartVO = this.getCartVO(id);
        return ServerResponse.successRs(cartVO);
    }

    //.更新购物车某个产品数量===============================================================================
    @Override
    public ServerResponse updateCart(Integer productId, Integer count, Integer uid) {
        //进行非空判断
        if (productId == null || productId <= 0 || count == null || count <= 0) {
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        //通过商品ID和用户ID查询购物车内的商品
        Cart ct = cartMapper.selectByProductIdAndUserId(productId, uid);
        //查询product的库存数
        Product product = productMapper.selectByProductId(productId, 0, 0, 0);
        if (ct!=null) {
            if (product.getStock() >= count) {
                ct.setQuantity(count);
                int i = cartMapper.updateByPrimaryKeySelective(ct);
            } else {
                return ServerResponse.defeatedRs("库存不足");
            }
        }else {
            return ServerResponse.defeatedRs("购物车商品不存在");
        }
        return listCart(uid);//方法的复用
    }

    //删除购物车
    @Override
    public ServerResponse deleteProduct(String productIds, Integer id) {
        if (productIds == null || productIds.equals("")) {
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        String[] split = productIds.split(",");
        List<String> strings = Arrays.asList(split);
        int i = cartMapper.deleteByProducts(strings, id);
        if (i <= 0) {
            return ServerResponse.defeatedRs("操作失败");
        }
        //返回剩余的信息
        CartVO cartVO = this.getCartVO(id);
        return ServerResponse.successRs(cartVO);
    }

    //返回购物车数量
    @Override
    public ServerResponse getCartProductCount(Integer id) {
        List<Cart> carts = cartMapper.selectByUid(id);
        if (carts == null) {
            return ServerResponse.defeatedRs("出现异常");
        }
        if (carts.size() <= 0) {
            return ServerResponse.successRs(0);
        }
        return ServerResponse.successRs(carts.size());
    }

    //购物车全选,可能需要ID,全选状态码1或着0,还有listpid
    @Override
    public ServerResponse selectAll(Integer id, Integer checked, String listPid) {
        if (listPid == null||listPid.equals("")) {
            int i = cartMapper.updateByAllOrPid(id, checked, null);
            if (i <= 0) {
                return ServerResponse.defeatedRs("修改失败");
            }
        } else {
            String[] split = listPid.split(",");
            List<String> listpid = Arrays.asList(split);
            int i = cartMapper.updateByAllOrPid(id, checked, listpid);
            if (i <= 0) {
                return ServerResponse.defeatedRs("修改失败");
            }

        }
        CartVO cartVO = this.getCartVO(id);
        return ServerResponse.successRs(cartVO);
    }
}
