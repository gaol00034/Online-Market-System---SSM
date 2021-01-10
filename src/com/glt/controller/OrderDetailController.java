package com.glt.controller;

import com.glt.po.ItemCategory;
import com.glt.po.ItemOrder;
import com.glt.po.OrderDetail;
import com.glt.po.Item;
import com.glt.service.OrderDetailService;
import com.glt.service.ItemService;
import com.glt.service.ItemOrderService;
import com.glt.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 订单详情c层
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemOrderService itemOrderService;

    @RequestMapping("/ulist")
    public String ulist(OrderDetail orderDetail, Model model){
        //分页查询
        String sql = "select * from order_detail  where order_id="+orderDetail.getOrderId();
        Pager<OrderDetail> pagers = orderDetailService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",orderDetail);
        return "orderDetail/ulist";
    }

    /**
     * 添加订单
     */
    @RequestMapping(value = "/add")
    public String add(int orderId, Model model){
        //ItemOrder itemOrder = itemOrderService.load(id);
        //orderDetail.setOrderId(itemOrder.getId());
        String sql = "select * from item where isDelete=0 order by id";
        List<Item> l_item = itemService.listBySqlReturnEntity(sql);
        model.addAttribute("types",l_item);
        model.addAttribute("orderId",orderId);
        return "orderDetail/add";
    }

    /**
     * 新订单保存
     */
    @RequestMapping("/exAdd")
    public String exAdd(OrderDetail orderDetail){
        Item item = itemService.load(orderDetail.getItemId());
        double total = Double.parseDouble(item.getPrice())*(((double)item.getZk())/10)*((double)orderDetail.getNum());
        orderDetail.setTotal(String.valueOf(total));
        orderDetail.setStatus(0);
        orderDetailService.insert(orderDetail);
        ItemOrder itemOrder = itemOrderService.load(orderDetail.getOrderId());
        if(itemOrder.getTotal()!=null)//累计添加商品
            total = Double.valueOf(itemOrder.getTotal())+total;
        String sql = "update item_order set total="+String.valueOf(total)+" where id="+orderDetail.getOrderId();
        itemOrderService.updateBysql(sql);
        return "redirect:/orderDetail/ulist?orderId="+orderDetail.getOrderId();
    }

    /**
     * 更新商品列表
     */
    @RequestMapping(value = "/update")
    public String update(int id, Model model){
        OrderDetail obj = orderDetailService.load(id);
        //更新商品列表中的商品需要先删除此条商品原价格
        ItemOrder itemOrder = itemOrderService.load(obj.getOrderId());
        double total = Double.parseDouble(itemOrder.getTotal());
        total = total - Double.parseDouble(obj.getTotal());
        itemOrder.setTotal(String.valueOf(total));
        itemOrderService.updateById(itemOrder);
        //返回商品下拉栏
        String sql = "select * from item where isDelete=0 order by id";
        List<Item> l_item = itemService.listBySqlReturnEntity(sql);
        model.addAttribute("types",l_item);
        model.addAttribute("obj",obj);
        return "orderDetail/update";
    }

    /**
     * 执行更新商品列表
     */
    @RequestMapping(value = "/exUpdate")
    public String exUpdate(OrderDetail orderDetail){
        Item item = itemService.load(orderDetail.getItemId());
        //计算总价
        double total = Double.parseDouble(item.getPrice())*(((double)item.getZk())/10)*((double)orderDetail.getNum());
        orderDetailService.updateById(orderDetail);
        orderDetailService.updateBysql("update order_detail set total="+String.valueOf(total)+" where id="+orderDetail.getId());
        ItemOrder itemOrder = itemOrderService.load(orderDetail.getOrderId());
        //将修改后生成的总价加入到整个订单中
        total = Double.parseDouble(itemOrder.getTotal())+total;
        String sql = "update item_order set total="+String.valueOf(total)+" where id="+orderDetail.getOrderId();
        itemOrderService.updateBysql(sql);
        return "redirect:/orderDetail/ulist?orderId="+orderDetail.getOrderId();
    }

    /**
     * 删除该商品
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        OrderDetail orderDetail = orderDetailService.load(id);
        ItemOrder itemOrder = itemOrderService.load(orderDetail.getOrderId());
        //订单总价需要更新，减去被删除商品
        Double total = Double.valueOf(itemOrder.getTotal())-Double.parseDouble(orderDetail.getTotal());
        itemOrder.setTotal(String.valueOf(total));
        itemOrderService.updateById(itemOrder);
        //删除此商品
        orderDetailService.deleteByEntity(orderDetail);
        return "redirect:/orderDetail/ulist?orderId="+orderDetail.getOrderId();
    }
}
