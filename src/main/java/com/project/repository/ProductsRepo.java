package com.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.entity.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
    
    List<Products> findByIsDeletedOrQuantityIsLessThanEqual(Boolean isDeleted, Integer quantity);
    List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity);
    Page<Products> findByProductTypeSlug(String slug,Pageable Page);
    Page<Products> findByNameIsLike(String name,Pageable Page);
    List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity, Pageable page);
    List<Products> findByIsDeletedAndProductTypeIdAndQuantityGreaterThan(Boolean isDelete, Long TypeId,Integer quantity);
    @Query("select p from Products p where p.productType.id = ?1")
    List<Products> findByProductTypeId(Long id);
    
    @Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET quantity = ? WHERE id = ?", nativeQuery = true)
	void updateQuantity(Integer newQuantity, Long productId);
    
    @Query(value = "select * from products where isDeleted = 0 and quantity > 0",nativeQuery = true)
    List<Products> findAllAvailable();
}
