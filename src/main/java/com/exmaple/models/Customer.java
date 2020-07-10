package com.exmaple.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name="CustomerTable")
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="Cid")
    private int cId;

    @Column(name="Name")
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-zA-Z\\s]+$", message = "Only letteres and spaces allowed")
    private String name;

    @Column(name="Age")
    @Min(5)
    @Max(110)
    private int age;

    @OneToMany(mappedBy="customer") // salinko ar customer, kas ir Product klase
    private Collection<Product> allCustomerProducts;



    public Customer() {
    }

    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Collection<Product> getAllCustomerProducts() {
        return allCustomerProducts;
    }

    public void setAllCustomerProducts(Collection<Product> allCustomerProducts) {
        this.allCustomerProducts = allCustomerProducts;
    }

    public int getcId() {
        return cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", cId=" + cId +
                '}';
    }
}
