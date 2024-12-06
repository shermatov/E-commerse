package com.shermatov.dreamshops.service.product;

import com.shermatov.dreamshops.dto.ImageDto;
import com.shermatov.dreamshops.dto.ProductDto;
import com.shermatov.dreamshops.exceptions.AlreadyExistsException;
import com.shermatov.dreamshops.exceptions.ProductNotFoundException;
import com.shermatov.dreamshops.model.Category;
import com.shermatov.dreamshops.model.Image;
import com.shermatov.dreamshops.model.Product;
import com.shermatov.dreamshops.repository.CategoryRepository;
import com.shermatov.dreamshops.repository.ImageRepository;
import com.shermatov.dreamshops.repository.ProductRepository;
import com.shermatov.dreamshops.request.AddProductRequest;
import com.shermatov.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor// annotation used of constructor injection
public class ProductService implements IProductService{

    private final ProductRepository productRepository;// always use final for injection when using requiredargsconstructor

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category found in the database
        // if yes set it as new product category
        // if no than save it as a new category
        // than set it as the new product category
        if (productExists(request.getName(),request.getBrand())){
            throw new AlreadyExistsException(request.getBrand()+" "+request.getName()+" already exists, You may update this product instead");
        }
        Category category= Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private boolean productExists(String name,String brand){
        return productRepository.existsByNameAndBrand(name,brand);
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );

    }
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()->{throw new ProductNotFoundException("product not found");});

    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(exisitingProduct-> updateExistingProduct(exisitingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()->new ProductNotFoundException("product not found"));
    }

    private Product updateExistingProduct(Product exisitingProduct, ProductUpdateRequest request){
        exisitingProduct.setName(request.getName());
        exisitingProduct.setBrand(request.getBrand());
        exisitingProduct.setPrice(request.getPrice());
        exisitingProduct.setInventory(request.getInventory());
        exisitingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        exisitingProduct.setCategory(category);
        return  exisitingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}