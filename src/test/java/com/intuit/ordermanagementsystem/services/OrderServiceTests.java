package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.OrderCreateParams;
import com.intuit.ordermanagementsystem.models.request.OrderItemParams;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTests {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private VendorProductRelationService vendorProductRelationService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void failOnCreateOrderMoreThanAvailableTest() {
        OrderCreateParams params = new OrderCreateParams();
        Product product = new Product();
        product.setUuid(UUID.randomUUID());
        UUID vendorUuid = UUID.randomUUID();
        UUID addressUuid = UUID.randomUUID();

        setOrderCreateParams(params, addressUuid, product.getUuid(), vendorUuid);

        VendorProductRelationDTO relationDTO = getRelationDTOforProductVendorOrigin(vendorUuid, addressUuid);
        when(productRepository.findById(product.getUuid())).thenReturn(Optional.of(product));
        when(vendorProductRelationService.getAvailableRelationByProductVendorAndOrigin(product, vendorUuid, addressUuid))
                .thenReturn(relationDTO);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(params);
        });
        assertEquals("Cannot create order for quantity more than available quantity with vendor",
                exception.getMessage());

    }

    private VendorProductRelationDTO getRelationDTOforProductVendorOrigin(UUID vendorUuid, UUID addressUuid) {
        VendorProductRelationDTO relationDTO = new VendorProductRelationDTO();
        relationDTO.setVendorUuid(vendorUuid);
        relationDTO.setAvailableQuantity(90.0);
        relationDTO.setVendorOriginAddressUuid(addressUuid);
        return relationDTO;
    }

    private void setOrderCreateParams(OrderCreateParams params, UUID addressUuid, UUID productUuid, UUID vendorUuid) {
        params.setOrderItemParams(new ArrayList<>());
        params.getOrderItemParams().add(new OrderItemParams());
        OrderItemParams itemParams = params.getOrderItemParams().get(0);
        itemParams.setOriginAddressUuid(addressUuid);
        itemParams.setProductUuid(productUuid);
        itemParams.setQuantity(100.0);
        itemParams.setVendorUuid(vendorUuid);
    }

}
