package com.shermatov.dreamshops.service.product;

import com.shermatov.dreamshops.dto.ProductDto;
import com.shermatov.dreamshops.model.Product;
import com.shermatov.dreamshops.request.AddProductRequest;
import com.shermatov.dreamshops.request.ProductUpdateRequest;

import java.util.List;
public interface IProductService {

    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductByCategory(String category);

    List<Product> getProductByBrand(String brand);

    List<Product> getProductByCategoryAndBrand(String category,String brand);

    List<Product> getProductByName(String name);

    List<Product> getProductByBrandAndName(String category,String name);

    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
