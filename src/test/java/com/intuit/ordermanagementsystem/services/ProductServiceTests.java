package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.externalrequests.UserManagementServiceCommunicator;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.dto.ProductDTO;
import com.intuit.ordermanagementsystem.models.dto.ProductPriceQuoteDTO;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserManagementServiceCommunicator umsCommunicator;

    @Mock
    private VendorProductRelationService vendorProductRelationService;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void passOnCreateActiveProductTest() {
        ProductCreateParams params = new ProductCreateParams();
        when(productRepository.save(any(Product.class))).thenAnswer(i -> i.getArguments()[0]);
        ProductDTO productDTO = productService.createProduct(params);
        assertEquals(Product.ProductStatus.ACTIVE, productDTO.getStatus());
    }

    @Test
    void failOnFetchProduct() {
        UUID uuid = UUID.randomUUID();
        when(productRepository.findById(uuid)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProduct(uuid);
        });
        assertEquals("Product not found with UUID: " + uuid.toString(), exception.getMessage());
    }

    @Test
    void passOnGetProductPriceQuoteWithVendorName() {
        UUID uuid = UUID.randomUUID();
        Product product = new Product();
        product.setVendorProductRelations(new ArrayList<>());
        VendorProductRelation relation = new VendorProductRelation();
        relation.setVendorUuid(uuid);
        product.getVendorProductRelations().add(relation);

        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        VendorProductRelationDTO relationDTO = new VendorProductRelationDTO();
        relationDTO.setVendorUuid(uuid);
        when(vendorProductRelationService.getRelationWithLowestProductPrice(product)).thenReturn(relationDTO);
        List<UserResponseDTO> list = new ArrayList<>();
        list.add(new UserResponseDTO("marvin", "email", "contanct", uuid));

        when(umsCommunicator.getUserDetails(any())).thenReturn(list);

        ProductPriceQuoteDTO response = productService.getProductPriceQuote(uuid);
        assertEquals(response.getPriceQuote().getVendorName(), "marvin");
    }


}
