package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.client.UserManagementServiceCommunicator;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.repositories.VendorProductRelationRepository;
import com.intuit.ordermanagementsystem.services.impl.VendorProductRelationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VendorProductRelationTest {

    @Mock
    private VendorProductRelationRepository vendorProductRelationRepository;

    @Mock
    private UserManagementServiceCommunicator umsCommunicator;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private VendorProductRelationServiceImpl vendorProductRelationService;

    @Test
    void passOnCreateAvailableRelationTest() {
        VendorProductRelationCreateParams params = new VendorProductRelationCreateParams();
        UUID uuid = UUID.randomUUID();
        params.setProductUuid(uuid);
        params.setStatus(VendorProductRelation.VendorProductRelationStatus.AVAILABLE);
        Product product = new Product();
        List<UserResponseDTO> list = new ArrayList<>();
        list.add(new UserResponseDTO("marvin", "email", "contanct", uuid));

        when(umsCommunicator.getUserDetails(any())).thenReturn(list);
        when(productRepository.findById(uuid)).thenReturn(Optional.of(product));
        when(vendorProductRelationRepository.save(any(VendorProductRelation.class))).thenAnswer(i -> i.getArguments()[0]);

        VendorProductRelationDTO relationDTO = vendorProductRelationService.createRelation(params);
        assertEquals(VendorProductRelation.VendorProductRelationStatus.AVAILABLE, relationDTO.getStatus());
    }


    @Test
    void failOnGetRelationByVendorProductAddress() {
        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        when(vendorProductRelationRepository.
                findFirstByProductAndVendorUuidAndVendorOriginAddressUuidAndStatus(product, UUID.randomUUID(),
                        UUID.randomUUID(), VendorProductRelation.VendorProductRelationStatus.AVAILABLE)).
                thenReturn(Optional.empty());


        UUID vendorUuid = UUID.randomUUID();
        UUID addressUuid = UUID.randomUUID();

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            vendorProductRelationService.getAvailableRelationByProductVendorAndOrigin(product, vendorUuid, addressUuid);
        });
        assertEquals("Vendor Product Relation with AVAILABLE status not found for Product: " + product.getUuid() +
                " vendor: " + vendorUuid + " origin address: " + addressUuid, exception.getMessage());
    }

    @Test
    void failOnGetRelationWithLowestPrice() {
        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        when(vendorProductRelationRepository.
                findFirstByProductAndStatusOrderByVendorPriceAsc(product, VendorProductRelation.VendorProductRelationStatus.AVAILABLE)).
                thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            vendorProductRelationService.getRelationWithLowestProductPrice(product);
        });
        assertEquals("No Available vendor relations for product: " + product.getUuid(), exception.getMessage());
    }

}
