package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.externalrequests.UserManagementServiceCommunicator;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.repositories.VendorProductRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class VendorProductRelationServiceImpl implements VendorProductRelationService {

    @Autowired
    private VendorProductRelationRepository vendorProductRelationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserManagementServiceCommunicator umsCommunicator;

    @Override
    public VendorProductRelationDTO createRelation(VendorProductRelationCreateParams params) {
        Optional<Product> optionalProduct = productRepository.findById(params.getProductUuid());
        if(!optionalProduct.isPresent()) throw new ResourceNotFoundException("product not found");
        params.setProduct(optionalProduct.get());
        VendorProductRelation relation = new VendorProductRelation(params);
        vendorProductRelationRepository.save(relation);
        VendorProductRelationDTO relationDTO = new VendorProductRelationDTO(relation);
        addVendorName(relationDTO);
        return relationDTO;
    }

    private void addVendorName(VendorProductRelationDTO relation) {
        List<UserResponseDTO> users = umsCommunicator.getUserDetails(new ArrayList<>(Arrays.asList(relation.getVendorUuid())));
        relation.setVendorName(
                users.stream().filter(x -> x.getUuid().equals(relation.getVendorUuid()))
                        .findFirst().orElse(new UserResponseDTO(null, null, null, null))
                        .getName()
        );
    }
}
