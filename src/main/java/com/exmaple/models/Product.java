package com.exmaple.models;

import com.exmaple.enums.ProductType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="ProductTable")
public class Product {

    @Column(name="Title")
    @Size(min = 3, max = 20)
    @Pattern(regexp = "[a-zA-Z\\s]+$", message = "Only letteres and spaces allowed")
    private String title;

    @Column(name="Price")
    @Min(0)
    @Max(1000)
    private float price;

    @Column(name="ProdType")
    private ProductType type;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="Id")
    private int id;

    @ManyToOne
    @JoinColumn(name="Cid") // lined to Cid
    private Customer customer;



    // no argument constructor
    public Product() {

    }

    public Product(String title, float price, ProductType type) {
        this.title = title;
        this.price = price;
        this.type = type;
    }

    public Product(String title, float price, ProductType type, Customer customer) {
        this.title = title;
        this.price = price;
        this.type = type;
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id + "." + title + " " + price + " eur, " + type;
    }
}
