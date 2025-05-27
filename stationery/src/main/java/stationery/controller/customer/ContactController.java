package stationery.controller.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import stationery.model.CategoryModel;
import stationery.model.ContactModel;
import stationery.repository.CategoryRepository;
import stationery.repository.ContactRepository;

@Controller
@RequestMapping("/customer")
public class ContactController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/contact")
    public String index(Model model) {
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "customer/pages/contact";
    }

    // Thêm message cho contact
    @PostMapping("/contact")
    public String saveContact(@RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {

        // truyen list categries sang view
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        ContactModel contact = new ContactModel();
        contact.setName(name);
        contact.setEmail(email);
        contact.setMessage(message);
        contact.setCreatedAt(LocalDateTime.now());
        contactRepository.save(contact);

        // truyền thông báo thành công về giao diện
        model.addAttribute("successMessage", "Cảm ơn bạn đã liên hệ!");
        model.addAttribute("categories", categories);
        return "customer/pages/contact"; 
    }
}
