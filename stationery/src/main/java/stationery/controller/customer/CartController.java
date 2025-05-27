package stationery.controller.customer;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import stationery.model.CartItem;
import stationery.model.CategoryModel;
import stationery.model.ProductModel;
import stationery.repository.CategoryRepository;
import stationery.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Hiển thị giỏ hàng
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("cartItems", cart);

        BigDecimal totalPrice = cart.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalPrice", totalPrice);

        return "customer/pages/cart";
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id,
            @RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity,
            HttpSession session) {
        ProductModel product = productRepository.findById(id).get();
                
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new CartItem(product, quantity));

        }

        session.setAttribute("cart", cart);
        return "redirect:/customer/cart";
    }

    // Xoá sản phẩm khỏi giỏ hàng
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProduct().getId().equals(id));
        }
        session.setAttribute("cart", cart);
        return "redirect:/customer/cart";
    }

}
