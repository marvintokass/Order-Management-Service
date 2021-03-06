package com.intuit.ordermanagementsystem.services;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.externalrequests.UserManagementServiceCommunicator;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationCreateParams;
import com.intuit.ordermanagementsystem.models.request.VendorProductRelationUpdateParams;
import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.repositories.VendorProductRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public VendorProductRelationDTO updateRelation(VendorProductRelationUpdateParams params) {
        Optional<VendorProductRelation> optionalRelation = vendorProductRelationRepository.findById(params.getRelationUuid());
        if(!optionalRelation.isPresent())
            throw new ResourceNotFoundException("Vendor Product Relation not found with UUID: " + params.getRelationUuid());
        VendorProductRelation relation = optionalRelation.get();

        if(params.getAvailableQuantity() != null && params.getAvailableQuantity() != relation.getAvailableQuantity())
            relation.setAvailableQuantity(params.getAvailableQuantity());
        if(params.getStatus() != null && params.getStatus() != relation.getStatus())
            relation.setStatus(params.getStatus());
        if(params.getTaxSlab() != null && params.getTaxSlab() != relation.getTaxSlab())
            relation.setTaxSlab(params.getTaxSlab());
        if(params.getVendorPrice() != null && params.getVendorPrice() != relation.getVendorPrice())
            relation.setVendorPrice(params.getVendorPrice());
        if(params.getVendorOriginAddressUuid() != null && params.getVendorOriginAddressUuid() != relation.getVendorOriginAddressUuid())
            relation.setVendorOriginAddressUuid(params.getVendorOriginAddressUuid());

        vendorProductRelationRepository.save(relation);
        return new VendorProductRelationDTO(relation);

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public VendorProductRelationDTO getRelationByProductVendorAndOrigin(Product product, UUID vendorUuid, UUID vendorOriginAddressUuid){
        Optional<VendorProductRelation> optionalRelation = vendorProductRelationRepository.findFirstByProductAndVendorUuidAndVendorOriginAddressUuid(product, vendorUuid, vendorOriginAddressUuid);
        if (!optionalRelation.isPresent())
            throw new ResourceNotFoundException("Vendor Product Relation not found");
        return new VendorProductRelationDTO(optionalRelation.get());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public VendorProductRelationDTO getRelationWithLowestProductPrice(Product product) {
        Optional<VendorProductRelation> optionalRelation = vendorProductRelationRepository.findFirstByProductOrderByVendorPriceAsc(product);
        if(!optionalRelation.isPresent())
            throw new ResourceNotFoundException("Price quote not found for product: " + product.getUuid());
        return new VendorProductRelationDTO(optionalRelation.get());
    }

}
