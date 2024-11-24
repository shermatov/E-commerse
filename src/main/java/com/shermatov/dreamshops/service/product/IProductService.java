package com.shermatov.dreamshops.service.product;

import com.shermatov.dreamshops.model.Product;
import com.shermatov.dreamshops.request.AddProductRequest;
import com.shermatov.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest updates, Long productId);
    Long countProductsByBrandAndName(String brand, String name);


    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

}
