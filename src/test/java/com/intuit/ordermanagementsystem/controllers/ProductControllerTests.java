package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.dto.ProductDTO;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ProductControllerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void FailOnInvalidCreateParamsTest() {
        ProductCreateParams params = new ProductCreateParams();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.createProduct(params);
        });
        assertEquals("Product Name must be present", exception.getMessage());

        params.setName("Iphone");
        exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.createProduct(params);
        });
        assertEquals("Category UUID must be present", exception.getMessage());

        params.setCategoryUuid(UUID.randomUUID());
        params.setBasePrice(-1000.0);
        exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.createProduct(params);
        });
        assertEquals("Product price must be positive", exception.getMessage());
    }

    @Test
    void CreateOnValidCreateParamsTest() {
        ProductCreateParams params = new ProductCreateParams();

        params.setName("Iphone");
        params.setCategoryUuid(UUID.randomUUID());
        params.setBasePrice(1000.0);

        when(productService.createProduct(params)).thenReturn(new ProductDTO(new Product(params)));

        ResponseEntity<ProductDTO> response = productController.createProduct(params);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

}
