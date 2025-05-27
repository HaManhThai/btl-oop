package stationery.controller.admin;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import stationery.model.ContactModel;
import stationery.repository.ContactRepository;


import java.util.List;

@Controller
@RequestMapping("admin/contact")
public class ContactAdminController {

    @Autowired
    private ContactRepository contactRepository;

    
    // 1 - Show all
    @GetMapping
    public String index(Model model) {
        List<ContactModel> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "admin/pages/contact/index";
    }

  
}
