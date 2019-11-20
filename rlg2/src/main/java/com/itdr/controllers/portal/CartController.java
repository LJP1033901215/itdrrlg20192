package com.itdr.controllers.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/cart/")
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping("add.do")
    //添加一个商品到购物车
    public ServerResponse addList(Integer productId, Integer count, HttpSession session){
       //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.addList(productId,count,users.getId());
        return sr;
    }


    @RequestMapping("list.do")
    //购物车List列表
    public ServerResponse listCart( HttpSession session){
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.listCart(users.getId());
        return sr;
    }

    @RequestMapping("update.do")
    //.更新购物车某个产品数量
    public ServerResponse updateCart(Integer productId, Integer count, HttpSession session){
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.updateCart(productId,count,users.getId());
        return sr;
    }
    @RequestMapping("delete_product.do")
    //删除购物车
    public ServerResponse deleteProduct(String productIds ,HttpSession session){
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.deleteProduct(productIds,users.getId());
        return sr;
    }
    @RequestMapping("get_cart_product_count.do")
    //返回购物车中的购物数量
    public ServerResponse getCartProductCount(HttpSession session){
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.getCartProductCount(users.getId());
        return sr;
    }
    @RequestMapping("select_all.do")
    //购物车全选,可能需要ID,全选状态码1或着0,还有listpid
    public ServerResponse selectAll(HttpSession session,
                                    @RequestParam(value = "integer",required =false ,defaultValue = "1")Integer checked,
                                    String listPid){
        System.out.println(listPid);
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }

        //调用业务层的方法
        ServerResponse sr =  cartService.selectAll(users.getId(),checked,listPid);
        return sr;
    }

    @RequestMapping("un_select_all.do")
    //购物车取消全选,可能需要ID,全选状态码1或着0,还有listpid
    public ServerResponse unSelectAll(HttpSession session,
                                    @RequestParam(value = "integer",required =false ,defaultValue = "0")Integer checked,
                                    String listPid){
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.selectAll(users.getId(),checked,listPid);
        return sr;
    }
    @RequestMapping("select.do")
    //根据传入的ID对商品进行选中,可能需要ID,全选状态码1或着0,还有listpid
    public ServerResponse select(HttpSession session,
                                   @RequestParam(value = "integer",required =false ,defaultValue = "1")Integer checked,
                                   String listPid){
        System.out.println("选中"+listPid);
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //判断传入的listPid不能为空
        if (listPid==null||listPid.equals("")){
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.selectAll(users.getId(),checked,listPid);
        return sr;
    }


    @RequestMapping("un_select.do")
    //根据传入的ID对商品取消选中,可能需要ID,全选状态码1或着0,还有listpid
    public ServerResponse unSelect(HttpSession session,
                                 @RequestParam(value = "integer",required =false ,defaultValue = "0")Integer checked,
                                 String listPid){
        System.out.println("非选中"+listPid);
        //获取session中保存的用户的信息
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        //判断用户信息是否为空
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        //判断传入的listPid不能为空
        if (listPid==null||listPid.equals("")){
            return ServerResponse.defeatedRs(Const.NOT_PARAMETER);
        }
        //调用业务层的方法
        ServerResponse sr =  cartService.selectAll(users.getId(),checked,listPid);
        return sr;
    }



}
