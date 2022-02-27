package com.microcommerce.ecommerce.web.controllers;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.microcommerce.ecommerce.web.dao.ProductsDao;
import com.microcommerce.ecommerce.model.Product;
import com.microcommerce.ecommerce.web.exception.NotFoundProductException;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductsController {

    private final ProductsDao productsDao;

    public ProductsController (ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    @GetMapping("/products")
    public MappingJacksonValue getAllProducts () {
        Iterable<Product> products =productsDao.findAll();
        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("buy_price");
        FilterProvider myFilterList = new SimpleFilterProvider().addFilter("dynamicFilter", myFilter);
        MappingJacksonValue productsFilter = new MappingJacksonValue(products);
        productsFilter.setFilters(myFilterList);
        return productsFilter;
    }

    @GetMapping("/products/{id}")
    public Product getProduct (@PathVariable int id) {
        Product product = productsDao.findById(id);
        if (product == null) throw new NotFoundProductException("Le produit d'id + id + est introuvable");
        return product;
    }

    @PostMapping("/products")

    public ResponseEntity addOneProduct (@Valid @RequestBody Product product) {
        Product addedproduct = productsDao.save(product);

        if (Objects.isNull(addedproduct)) {
            ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                        .path("/{id}")
                                .buildAndExpand(addedproduct.getId())
                                        .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/products")
    public Product editProduct (@RequestBody Product product) {
        return productsDao.save(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct (@PathVariable int id) {
        productsDao.deleteById(id);
    }

    @GetMapping("/test/products/{limit}")
    public MappingJacksonValue getProductsGreaterThan (@PathVariable int limit) {
        Iterable<Product> products =productsDao.findByPriceGreaterThan(limit);
        SimpleBeanPropertyFilter myFilter = SimpleBeanPropertyFilter.serializeAllExcept("buy_price");
        FilterProvider myFilterList = new SimpleFilterProvider().addFilter("dynamicFilter", myFilter);
        MappingJacksonValue productsFilter = new MappingJacksonValue(products);
        productsFilter.setFilters(myFilterList);
        return productsFilter;

    }

    @GetMapping("/AdminProduits")
    public HashMap<String, Integer> calculMargeProduit () {
        HashMap<String, Integer> productMargeList = new HashMap<>();
        List<Product> products = productsDao.findAll();
        for (Product product: products) {
            productMargeList.put("Product{id="+product.getId()+", name="+product.getName()+", price="+product.getPrice()+"}", product.getPrice()-product.getBuy_price());
        }
        return productMargeList;
    }

    @GetMapping("/products/orderByNameAsc")
    public List<Product> trierProduitsParOrdreAlphabetique () {
        return productsDao.findAllByOrderByNameAsc();
    }
}
