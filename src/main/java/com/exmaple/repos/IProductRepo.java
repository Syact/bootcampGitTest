package com.exmaple.repos;

import com.exmaple.enums.ProductType;
import com.exmaple.models.Customer;
import com.exmaple.models.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface IProductRepo extends CrudRepository<Product, Integer> {

    boolean existsByTitleAndPriceAndType(String title, float price, ProductType type);

    Product findByTitleAndPrice(String title, float price);

    ArrayList<Product> findByPriceLessThan(float price);

    ArrayList<Product> findByPrice(float price);

    ArrayList<Product> findByCustomer(Customer customer);

}
