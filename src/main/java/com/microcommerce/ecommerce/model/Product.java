package com.microcommerce.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;

    @Size(min = 3, max = 20)
    private String name;

    @Min(1)
    private int price;


    private int buy_price;
    
    public Product () {}
    
    public Product (int id, String name, int price, int buy_price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.buy_price = buy_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(int buy_price) {
        this.buy_price = buy_price;
    }

    public String toString () {
        return "Product {" +
                "id= '" + id + "'" +
                ", name=" + name +
                ", price=" + price +
                ", buy_price=" + buy_price +
                "}";

    }
}
