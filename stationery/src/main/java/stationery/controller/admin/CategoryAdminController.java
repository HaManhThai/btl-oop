package stationery.controller.admin;

import org.springframework.ui.Model;

import stationery.model.CategoryModel;

import stationery.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/category")
public class CategoryAdminController {

    @Autowired
    private CategoryRepository categoryRepository;

    
    // 1 - Show all
    @GetMapping
    public String index(Model model) {
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/pages/category/index";
    }

    // 2 - Show create
    @GetMapping("/create")
    public String createGet() {
        return "admin/pages/category/create";
    }

    // 3 - Create
    @PostMapping("/create")
    public String createPost(@RequestParam String name) {
        CategoryModel category = new CategoryModel();
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    // 4 - Show update
    @GetMapping("/update/{id}")
    public String updateGet(@PathVariable Long id, Model model) {
        CategoryModel category = categoryRepository.findById(id).get();
        model.addAttribute("category", category);      
        return "admin/pages/category/update";

    }

    // 5 - Update
    @PostMapping("/update/{id}")
    public String updatePost(@PathVariable Long id, @RequestParam String name) {
        CategoryModel category = categoryRepository.findById(id).get();            
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    // 6 - Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }
}
