package com.itdr.controllers.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.OreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping("/portal/order/")
public class OrderController {

    @Autowired
    OreateService oreateService;

    //创建订单======================================================================
    @RequestMapping("create.do")
    //创建方法
    public ServerResponse createOrder(HttpSession session, Integer shippingId ){
        //获取Session中的信息
        Users attribute = (Users) session.getAttribute(Const.LOGINUSER);
        if (attribute == null){
            //判断信息是不行空
            return ServerResponse.defeatedRs(Const.UsersEnum.NO_LOGIN.getDesc());
        }
        //调用数据称的方法
        ServerResponse sr = oreateService.createOrder(attribute.getId(), shippingId);
        return sr;
    }
    @RequestMapping("get_order_cart_product")
    public ServerResponse getOrderCartProduct(HttpSession session,
                                              @RequestParam(value = "orderNo",required =false) Long orderNo){
        Users users = (Users)session.getAttribute(Const.LOGINUSER);
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        ServerResponse rs  = oreateService.getOrderCartProduct(users.getId(),orderNo);
        return rs;
    }

    @RequestMapping("list.do")
    public ServerResponse getList(HttpSession session,
                                  @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                                  @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum){

        Users users = (Users)session.getAttribute(Const.LOGINUSER);
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        ServerResponse rs  = oreateService.getList(users.getId(),pageSize,pageNum);
        return rs;
    }

    //取消订单
    @RequestMapping("countermand_order.do")
    public ServerResponse countermandOrder(HttpSession session, Long orderNo){
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NEED_LOGIN.getDesc());
        }
        ServerResponse sr =  oreateService.countermandOrder(users.getId(),orderNo);
        return sr;
    }

    //删除订单
    @RequestMapping("delete_order.do")
    public ServerResponse deleteOrder(HttpSession session, Long orderNo){
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users==null){
            return ServerResponse.defeatedRs(Const.UsersEnum.NO_LOGIN);
        }
        ServerResponse sr =  oreateService.deleteOrder(users.getId(),orderNo);
        return sr;
    }

}
