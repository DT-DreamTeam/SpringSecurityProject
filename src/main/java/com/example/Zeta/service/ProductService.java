package com.example.Zeta.service;

import com.example.Zeta.dto.ProductDto;
import com.example.Zeta.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {
    List<ProductDto> findAll();
    Product save(MultipartFile imageProduct, ProductDto productDto);
    Product update(ProductDto productDto);
    void deleteById(Long id);
    void enableById(Long id);
}
