package stationery.controller.admin;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import stationery.repository.CategoryRepository;
import stationery.repository.ContactRepository;
import stationery.repository.ProductRepository;
import stationery.repository.ReviewRepository;
import stationery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stationery.model.CategoryModel;
import stationery.model.OrderModel;
import stationery.model.ProductModel;
import stationery.model.ReviewModel;
import stationery.model.ContactModel;

@Controller
@RequestMapping("/admin")
public class HomeAdminController { 
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/home")
    public String showAllCate(Model model) {
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<ProductModel> products = productRepository.findAll();
        model.addAttribute("products", products);

        List<OrderModel> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);

        List<ReviewModel> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);

        List<ContactModel> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "admin/pages/home";
    }
 
}