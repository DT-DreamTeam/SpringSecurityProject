package com.example.Zeta.controller;

import com.example.Zeta.dto.ProductDto;
import com.example.Zeta.model.Category;
import com.example.Zeta.service.CategoryService;
import com.example.Zeta.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<ProductDto> productDtoList=productService.findAll();
        model.addAttribute("title","Товары");
        model.addAttribute("products",productDtoList);
        model.addAttribute("size",productDtoList.size());
        return "products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories",categories);
        model.addAttribute("product",new ProductDto());
        return "add-product";
    }
    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product")ProductDto productDto,
                              @RequestParam("imageProduct")MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try{
            productService.save(imageProduct,productDto);
            attributes.addFlashAttribute("success","Товар успешно добавлен");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Ошибка");
        }
        return "redirect:/products";
    }
}
