package stationery.controller.admin;

import org.springframework.ui.Model;

import stationery.model.OrderModel;
import stationery.model.OrderDetailModel;

import stationery.repository.OrderRepository;
import stationery.repository.OrderDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/order")
public class OrderAdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    
    // 1 - Show all
    @GetMapping
    public String index(Model model) {
        List<OrderModel> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin/pages/order/index";
    }

    // 2 - hien thị chi tiết từng đơn hàng
    @GetMapping("/{id}")
    public String detailOrder(@PathVariable Long id, Model model) {
        List<OrderDetailModel> order_details = orderDetailRepository.findByOrderId(id);
        model.addAttribute("order_details", order_details);
        return "admin/pages/order/order_detail";
    }
}
