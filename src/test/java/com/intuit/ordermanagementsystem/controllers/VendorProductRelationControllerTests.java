package com.intuit.ordermanagementsystem.controllers;

import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.services.VendorProductRelationService;
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
public class VendorProductRelationControllerTests {

    @Mock
    private VendorProductRelationService vendorProductRelationService;

    @InjectMocks
    private VendorProductRelationController vendorProductRelationController;

    private void failCreateWithoutVendorUUID(VendorProductRelationCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals("Vendor UUID must be present", exception.getMessage());
    }

    private void failCreateWithoutValidPrice(VendorProductRelationCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals("Valid price must be present", exception.getMessage());
    }

    private void failCreateWithoutValidTax(VendorProductRelationCreateParams params, String message) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals(message, exception.getMessage());
    }

    private void failCreateWithoutStatus(VendorProductRelationCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals("Status must must be present", exception.getMessage());
    }

    private void failCreateWithoutValidQuantity(VendorProductRelationCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals("Valid quantity must be present", exception.getMessage());
    }

    private void failCreateWithoutVendorAddressUUID(VendorProductRelationCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals("Vendor address UUID must be present", exception.getMessage());
    }

    private void failCreateWithoutProductUUID(VendorProductRelationCreateParams params) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            vendorProductRelationController.createVendorProductRelation(params);
        });
        assertEquals("Product UUID must be present", exception.getMessage());
    }


    @Test
    void FailOnInvalidCreateParamsTest() {
        VendorProductRelationCreateParams params = new VendorProductRelationCreateParams();

        failCreateWithoutVendorUUID(params);

        params.setVendorUuid(UUID.randomUUID());
        failCreateWithoutValidPrice(params);

        params.setVendorPrice(-1.0);
        failCreateWithoutValidPrice(params);

        params.setVendorPrice(1000.0);
        failCreateWithoutValidTax(params,"Tax slab must be present");

        params.setTax("ONE");
        failCreateWithoutValidTax(params, "No enum constant com.intuit.ordermanagementsystem.models.VendorProductRelation.TaxSlab.ONE");

        params.setTax("FIVE");
        failCreateWithoutStatus(params);

        params.setRelationStatus("AVAILABLE");
        failCreateWithoutValidQuantity(params);

        params.setAvailableQuantity(-100.0);
        failCreateWithoutValidQuantity(params);

        params.setAvailableQuantity(100.0);
        failCreateWithoutVendorAddressUUID(params);

        params.setVendorOriginAddressUuid(UUID.randomUUID());
        failCreateWithoutProductUUID(params);
    }

    @Test
    void CreateOnValidCreateParamsTest() {
        VendorProductRelationCreateParams params = new VendorProductRelationCreateParams();

        params.setVendorUuid(UUID.randomUUID());
        params.setVendorPrice(1000.0);
        params.setTax("FIVE");
        params.setRelationStatus("AVAILABLE");
        params.setAvailableQuantity(100.0);
        params.setVendorOriginAddressUuid(UUID.randomUUID());
        params.setProductUuid(UUID.randomUUID());
        when(vendorProductRelationService.createRelation(params)).thenReturn(new VendorProductRelationDTO(new VendorProductRelation(params)));

        ResponseEntity<VendorProductRelationDTO> response = vendorProductRelationController.createVendorProductRelation(params);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

}
