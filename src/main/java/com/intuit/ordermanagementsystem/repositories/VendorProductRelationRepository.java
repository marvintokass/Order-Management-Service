package com.intuit.ordermanagementsystem.repositories;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorProductRelationRepository extends JpaRepository<VendorProductRelation, UUID> {

    Optional<VendorProductRelation> findFirstByProductOrderByVendorPriceAsc(Product product);

    Optional<VendorProductRelation> findFirstByProductAndVendorUuidAndVendorOriginAddressUuid(Product product, UUID vendorUuid, UUID vendorOriginAddressUuid);

}
