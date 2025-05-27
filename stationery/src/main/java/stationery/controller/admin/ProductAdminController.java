package stationery.controller.admin;

import org.springframework.ui.Model;

import stationery.model.CategoryModel;
import stationery.model.ProductModel;
import stationery.repository.CategoryRepository;
import stationery.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
// import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("admin/product")
public class ProductAdminController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // 1 - Show all
    @GetMapping
    public String index(Model model) {
        List<ProductModel> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin/pages/product/index";
    }


    // 2 - Show create
    @GetMapping("/create")
    public String createGet(Model model) {
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/pages/product/create";
    }



    // 3 - Create
    @PostMapping("/create")
    public String createProduct(@RequestParam String nameProduct,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam("image_url") MultipartFile image_url,
            @RequestParam Long categoryId) throws IOException {
        // Lưu tệp vào thư mục static/admin/assets/img
        String imageUrl = uploadFile(image_url);
        // Tạo đối tượng ProductModel mới
        ProductModel product = new ProductModel();
        product.setName(nameProduct);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setImage_url(imageUrl);
        // Lấy danh mục từ cơ sở dữ liệu
        CategoryModel category = categoryRepository.findById(categoryId).get();
        // Gán danh mục cho sản phẩm
        product.setCategory(category);
        // Lưu sản phẩm vào cơ sở dữ liệu
        productRepository.save(product);
        return "redirect:/admin/product"; // Quay lại danh sách sản phẩm
    }

        private String uploadFile(MultipartFile file) throws IOException {
            // Thư mục lưu tệp trong static/admin/assets/img
            String uploadDir = "src/main/resources/static/admin/assets/img/";
            // Lấy tên tệp gốc
            String fileName = file.getOriginalFilename();
            // Đường dẫn lưu tệp
            Path uploadPath = Path.of(uploadDir + fileName);
            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            Files.createDirectories(uploadPath.getParent());
            // Lưu tệp vào thư mục
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
            // Trả về đường dẫn URL tĩnh để lưu trong DB
            return fileName;
        }

    // // 4 - Show update
    @GetMapping("/update/{id}")
    public String updateGet(@PathVariable Long id, Model model) {
        ProductModel product = productRepository.findById(id).get();
        List<CategoryModel> categories = categoryRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "admin/pages/product/update";
    }

    // 5 - update
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id,
            @RequestParam String nameProduct,
            @RequestParam String description,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam(required = false) MultipartFile image_url, // Không bắt buộc phải có ảnh mới
            @RequestParam Long categoryId) throws IOException {
        // Lấy sản phẩm từ cơ sở dữ liệu
        ProductModel product = productRepository.findById(id).get();
        // Cập nhật thông tin sản phẩm
        product.setName(nameProduct);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        // Cập nhật ảnh nếu người dùng tải ảnh mới
        if (!image_url.isEmpty()) {
            // Lưu ảnh mới vào thư mục và lấy đường dẫn
            String imageUrl = uploadFile(image_url);
            product.setImage_url(imageUrl);
        }
        // Lấy danh mục từ cơ sở dữ liệu và cập nhật sản phẩm
        CategoryModel category = categoryRepository.findById(categoryId).get();          
        product.setCategory(category);
        // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
        productRepository.save(product);
        return "redirect:/admin/product"; // Quay lại danh sách sản phẩm
    }

    // 6 - Delete
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/product";
    }

}
