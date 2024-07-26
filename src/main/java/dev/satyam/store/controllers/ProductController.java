package dev.satyam.store.controllers;

import dev.satyam.store.models.Product;
import dev.satyam.store.models.ProductDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import dev.satyam.store.services.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    public boolean isLoggedIn(HttpSession session) {
        String userSession = (String) session.getAttribute("userName");

        return userSession != null;
    }

    @GetMapping({"", "/"})
    public String getProducts(Model model, HttpSession session) {

        if (isLoggedIn(session)) {
            List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            model.addAttribute("products", products);
            return "products/index";
        }

        return "redirect:/login";
    }

    @GetMapping("/add")
    public String showAddProduct(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {

        if (productDto.getProduct_image().isEmpty()) {
            result.addError(new FieldError("productDto", "product_image", "Image file is required!"));
        }

        if (result.hasErrors()) {
            return "products/addProduct";
        }

        MultipartFile image = productDto.getProduct_image();
        Date createdDate = new Date();
        String storageFileName = createdDate.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/uploads/";

            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                InputStream inputStream = image.getInputStream();
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception ex) {
                System.out.println("Exception" + ex.getMessage());
            }


        } catch (Exception ex) {
            System.out.println("Exception" + ex.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setProduct_image(storageFileName);
        product.setCreated_at(createdDate);

        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit")
    public String showEditProduct(Model model, @RequestParam int id) {
        try {
            Product product = productRepository.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setPrice(product.getPrice());
            productDto.setDescription(product.getDescription());

            model.addAttribute("productDto", productDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }
        return "products/editProduct";

    }

    @PostMapping("edit")
    public String doEditProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result) {

        try {
            Product product = productRepository.findById(id).get();
            model.addAttribute("product", product);

            if (result.hasErrors()) {
                return "products/editProduct";
            }

            if (!productDto.getProduct_image().isEmpty()) {
                String uploadDir = "public/uploads/";
                Path oldImagePath = Paths.get(uploadDir + product.getProduct_image());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                MultipartFile image = productDto.getProduct_image();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }

                product.setProduct_image(storageFileName);

                product.setName(productDto.getName());
                product.setBrand(productDto.getBrand());
                product.setCategory(productDto.getCategory());
                product.setPrice(productDto.getPrice());
                product.setDescription(productDto.getDescription());

                productRepository.save(product);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {

        try {
            Product product = productRepository.findById(id).get();
            Path imagePath = Paths.get("public/uploads/" + product.getProduct_image());

            try {
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            productRepository.delete(product);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/products";
    }
}
