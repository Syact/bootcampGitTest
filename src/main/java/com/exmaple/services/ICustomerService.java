package com.exmaple.services;

import com.exmaple.models.Customer;
import com.exmaple.models.Product;

import java.util.ArrayList;
import java.util.Collection;

public interface ICustomerService {

    //register
    boolean register(String name, int age);

    //  getAllPurchasedProducts
    ArrayList<Product> getAllPurchasedProductsByCustomerId(int id) throws Exception;

    // buyProducts
    boolean buyProducts(Collection<Product> purchasedProducts, int id) throws Exception;

    //getOneCustomer
    Customer selectOneCustomerById(int id) throws Exception;

    // getAllCustomers
    ArrayList<Customer> selectAllCustomers();
}
