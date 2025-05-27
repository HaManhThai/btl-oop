package stationery.controller.admin;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import stationery.model.ReviewModel;
import stationery.repository.ReviewRepository;

import java.util.List;

@Controller
@RequestMapping("admin/review")
public class ReviewAdminController {

    @Autowired
    private ReviewRepository reviewRepository;

    
    // 1 - Show all
    @GetMapping
    public String index(Model model) {
        List<ReviewModel> reviews = reviewRepository.findAll();
        model.addAttribute("reviews", reviews);
        return "admin/pages/review/index";
    }

    // Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        reviewRepository.deleteById(id);
        return "redirect:/admin/review";
    }
}
