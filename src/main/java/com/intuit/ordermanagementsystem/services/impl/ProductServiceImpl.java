package com.intuit.ordermanagementsystem.services.impl;

import com.intuit.ordermanagementsystem.exceptions.ResourceNotFoundException;
import com.intuit.ordermanagementsystem.client.UserManagementServiceCommunicator;
import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.request.ProductCreateParams;
import com.intuit.ordermanagementsystem.models.dto.ProductDTO;
import com.intuit.ordermanagementsystem.models.dto.ProductPriceQuoteDTO;
import com.intuit.ordermanagementsystem.models.response.UserResponseDTO;
import com.intuit.ordermanagementsystem.models.dto.VendorProductRelationDTO;
import com.intuit.ordermanagementsystem.repositories.ProductRepository;
import com.intuit.ordermanagementsystem.services.ProductService;
import com.intuit.ordermanagementsystem.services.VendorProductRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VendorProductRelationService vendorProductRelationService;
    @Autowired
    private UserManagementServiceCommunicator umsCommunicator;

    @Override
    public ProductDTO createProduct(ProductCreateParams params) {
        Product product = new Product(params);
        productRepository.save(product);
        return new ProductDTO(product);
    }

    @Override
    public ProductDTO getProduct(UUID uuid) {
        Optional<Product> optionalProduct = productRepository.findById(uuid);
        if(!optionalProduct.isPresent())
            throw new ResourceNotFoundException("Product not found with UUID: " + uuid.toString());
        return new ProductDTO(optionalProduct.get());
    }

    @Override
    public ProductPriceQuoteDTO getProductPriceQuote(UUID uuid) {
        Optional<Product> optionalProduct = productRepository.findById(uuid);
        if(!optionalProduct.isPresent()) throw new ResourceNotFoundException("Product not found with UUID: " + uuid.toString());
        Product product = optionalProduct.get();
        VendorProductRelationDTO relation = vendorProductRelationService.getRelationWithLowestProductPrice(product);
        ProductPriceQuoteDTO quote = new ProductPriceQuoteDTO(new ProductDTO(product), relation);
        fetchAndSetVendorNamesForProductPriceDTO(quote);
        return quote;
    }

    private void fetchAndSetVendorNamesForProductPriceDTO(ProductPriceQuoteDTO quote) {
        List<UUID> userUUIDs = new ArrayList<>();
        userUUIDs.add(quote.getPriceQuote().getVendorUuid());
        for(int i=0;i<5 && i < quote.getProduct().getVendorProductRelations().size(); i++) {
            userUUIDs.add(quote.getProduct().getVendorProductRelations().get(i).getVendorUuid());
        }
        List<UserResponseDTO> users = umsCommunicator.getUserDetails(userUUIDs);
        addVendorNames(users, quote);
    }

    private void addVendorNames(List<UserResponseDTO> users, ProductPriceQuoteDTO quote) {
        addVendorNameForRelation(users, quote.getPriceQuote());
        for(int i = 0; i < 5 && i < quote.getProduct().getVendorProductRelations().size(); i++) {
            VendorProductRelationDTO relationDTO = quote.getProduct().getVendorProductRelations().get(i);
            addVendorNameForRelation(users, relationDTO);
        }
    }

    private void addVendorNameForRelation(List<UserResponseDTO> users, VendorProductRelationDTO relation) {
        UUID vendorUuid = relation.getVendorUuid();
        UserResponseDTO user = users.stream().filter(x -> x.getUuid().equals(vendorUuid)).findFirst().orElse(new UserResponseDTO(null, null, null, null));
        relation.setVendorName(user.getName());
    }



}
