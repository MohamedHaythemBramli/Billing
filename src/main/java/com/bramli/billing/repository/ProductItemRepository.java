package com.bramli.billing.repository;

import com.bramli.billing.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
Collection<ProductItem> findByBillId(Long id);
}
