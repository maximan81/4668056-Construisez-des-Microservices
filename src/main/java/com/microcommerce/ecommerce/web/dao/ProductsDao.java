package com.microcommerce.ecommerce.web.dao;

import com.microcommerce.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsDao extends JpaRepository<Product, Integer> {
   Product findById (int id);
   @Query(value = "SELECT new Product(id, name, price, buy_price) FROM Product p WHERE p.price > :limit")
   List<Product> findByPriceGreaterThan(@Param("limit") int price);
   List<Product> findAllByOrderByNameAsc();
}
