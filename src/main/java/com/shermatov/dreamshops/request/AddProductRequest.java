package com.shermatov.dreamshops.request;

import com.shermatov.dreamshops.model.Category;
import com.shermatov.dreamshops.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    private Category category;

    private List<Image> images;
}
