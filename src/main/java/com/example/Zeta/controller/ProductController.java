package com.example.Zeta.controller;

import com.example.Zeta.dto.ProductDto;
import com.example.Zeta.model.Category;
import com.example.Zeta.model.Product;
import com.example.Zeta.service.CategoryService;
import com.example.Zeta.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id,Model model , Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","Изменить товар");
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories",categories);
        model.addAttribute("productDto",productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes attributes){
        try{
            productService.update(imageProduct,productDto);
            attributes.addFlashAttribute("success","Изменения успешны!");
        }catch(Exception e){

            e.printStackTrace();
            attributes.addFlashAttribute("error","Ошибибка при редактировании");
        }
        return  "redirect:/products";

    }

    @RequestMapping(value = "/enabled-product/{id}", method = {RequestMethod.PUT,RequestMethod.GET})
    public String enabledProduct(@PathVariable("id")Long id,RedirectAttributes attributes){
        try{
            productService.enableById(id);
            attributes.addFlashAttribute("success","Успешно добавлен! ");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Ошибка в добавлении!");
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/delete-product/{id}",method = {RequestMethod.PUT,RequestMethod.GET})
    public String deleteProduct(@PathVariable("id")Long id,RedirectAttributes attributes){
        try{
            productService.deleteById(id);
            attributes.addFlashAttribute("success","Успешно удален!");
        }catch(Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error","Ошибка при удалении!");
        }
        return "redirect:/products";
    }

}
