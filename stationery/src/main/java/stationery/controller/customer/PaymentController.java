package stationery.controller.customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import stationery.model.CartItem;
import stationery.model.CategoryModel;
import stationery.model.OrderDetailModel;
import stationery.model.OrderModel;
import stationery.repository.CategoryRepository;
import stationery.repository.OrderDetailRepository;
import stationery.repository.OrderRepository;

@Controller
@RequestMapping("/customer")
public class PaymentController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/payment")
    public String checkout(Model model, HttpSession session) {
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/customer/cart"; // không cho checkout nếu giỏ hàng trống
        }

        model.addAttribute("cartItems", cart);

        BigDecimal totalPrice = cart.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalPrice", totalPrice);

        return "customer/pages/payment"; 
    }

    // dat hang - luu vao orders table
    @PostMapping("/place-order")
    public String placeOrder(
            @RequestParam("recipientName") String recipientName,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            return "redirect:/customer/cart";
        }

        BigDecimal totalPrice = cart.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderModel order = new OrderModel();
        order.setRecipient_name(recipientName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setTotal_price(totalPrice);
        order.setCreatedAt(LocalDateTime.now());
        
        orderRepository.save(order);

        // Lưu từng sản phẩm vào bảng order_details
        for (CartItem item : cart) {
            OrderDetailModel detail = new OrderDetailModel();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setQuantity(item.getQuantity());
            detail.setUnit_price(item.getProduct().getPrice());

            orderDetailRepository.save(detail);
        }

        // Sau khi lưu thì xóa giỏ hàng
        session.removeAttribute("cart");

        redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");

        return "redirect:/customer/cart"; // tạo trang này để thông báo
    }

}
