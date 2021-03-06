package com.intuit.ordermanagementsystem.repositories;

import com.intuit.ordermanagementsystem.models.Product;
import com.intuit.ordermanagementsystem.models.VendorProductRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorProductRelationRepository extends JpaRepository<VendorProductRelation, UUID> {

    Optional<VendorProductRelation> findFirstByProductAndStatusOrderByVendorPriceAsc(Product product, VendorProductRelation.VendorProductRelationStatus status);

    Optional<VendorProductRelation> findFirstByProductAndVendorUuidAndVendorOriginAddressUuidAndStatus(Product product, UUID vendorUuid, UUID vendorOriginAddressUuid,
                                                                                                       VendorProductRelation.VendorProductRelationStatus status);

}
