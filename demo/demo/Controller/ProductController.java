package com.example.demo.Controller;

import com.example.demo.Model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    private static List<Product> products = new ArrayList<>();

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", products);
        return "products/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("product") Product product,
                     BindingResult result,
                     @RequestParam("file") MultipartFile file,
                     RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            try {
                String uploadDir = "demo/src/main/resources/static/images/";
                File saveFile = new File(uploadDir + fileName);
                file.transferTo(saveFile);
                product.setImage(fileName);
            } catch (IOException e) {
                result.rejectValue("image", null, "Lỗi upload hình ảnh");
            }
        }
        if (result.hasErrors()) {
            return "products/add";
        }
        products.add(product);
        redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công!");
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Product product = products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
        if (product == null) return "redirect:/products";
        model.addAttribute("product", product);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable String id,
                      @Valid @ModelAttribute("product") Product product,
                      BindingResult result,
                      @RequestParam("file") MultipartFile file,
                      RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            try {
                String uploadDir = "demo/src/main/resources/static/images/";
                File saveFile = new File(uploadDir + fileName);
                file.transferTo(saveFile);
                product.setImage(fileName);
            } catch (IOException e) {
                result.rejectValue("image", null, "Lỗi upload hình ảnh");
            }
        }
        if (result.hasErrors()) {
            return "products/edit";
        }
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, product);
                break;
            }
        }
        redirectAttributes.addFlashAttribute("success", "Cập nhật sản phẩm thành công!");
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        products.removeIf(p -> p.getId().equals(id));
        redirectAttributes.addFlashAttribute("success", "Xóa sản phẩm thành công!");
        return "redirect:/products";
    }
}
