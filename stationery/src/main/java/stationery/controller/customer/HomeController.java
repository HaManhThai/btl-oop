package stationery.controller.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import stationery.repository.CategoryRepository;

import stationery.repository.ProductRepository;
import stationery.repository.ReviewRepository;
import stationery.model.CategoryModel;
import stationery.model.ProductModel;
import stationery.model.ReviewModel;

@Controller
@RequestMapping("/customer")
public class HomeController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    
// tra ve trang home
    @GetMapping("/home")
    public String getListProducts(Model model) {
        // truyen list categries sang view
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        // truyen list products sang view
        List<ProductModel> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "customer/pages/home";
    }

    // tra ve trang chi tiet san pham
    @GetMapping("/product/{id}")
    public String getDetailProduct(@PathVariable Long id, Model model) {
        // truyen list categries sang view
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        // truyen chi tiet san pham
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy danh mục với ID: " + id));
        model.addAttribute("product", product);

        // Lấy các review của sản phẩm
        List<ReviewModel> reviews = reviewRepository.findByProductId(id);
        model.addAttribute("reviews", reviews);
        return "customer/pages/detailProduct";

    }

    // Tìm kiếm sản phẩm theo danh mục
    @GetMapping("/home/{categoryName}")
    public String showProductsByCategory(@PathVariable String categoryName, Model model) {
        // truyen list categries sang view
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        
        CategoryModel selectedCategory = categoryRepository.findByName(categoryName);
        List<ProductModel> products = productRepository.findByCategory(selectedCategory);

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "customer/pages/home";

    }

    // Tìm kiếm sản phẩm theo kí tự trong tên
    @GetMapping("/product/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        // truyen list categries sang view
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<ProductModel> searchResults = productRepository.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("categories", categories);
        model.addAttribute("products", searchResults);
        return "customer/pages/home";
    }

    // Thêm review cho sản phẩm
    @PostMapping("/product/{id}")
    public String addReview(@PathVariable Long id,
            @RequestParam String name,
            @RequestParam String review,
            Model model) {
        // Lấy thông tin sản phẩm theo ID
        ProductModel product = productRepository.findById(id).get();
                
        // Tạo mới đối tượng review
        ReviewModel newReview = new ReviewModel();
        newReview.setProduct(product); // Gán đối tượng sản phẩm vào review
        newReview.setName(name);
        newReview.setReview(review);
        newReview.setCreatedAt(LocalDateTime.now());

        // Lưu review vào database
        reviewRepository.save(newReview);

        // Sau khi thêm review, chuyển về trang chi tiết sản phẩm
        return "redirect:/customer/product/" + id;
    }


}
