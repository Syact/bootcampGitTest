package com.exmaple.services;

import com.exmaple.enums.ProductType;
import com.exmaple.models.Product;

import java.util.ArrayList;

public interface IProductService {

    // select
    Product selectOneProductByID(int id) throws Exception;
    ArrayList<Product> selectAllProducts();


    // create
    boolean insertNewProduct(String title, float price, ProductType type);
    boolean insertNewProductByObject(Product product);

    // update
    boolean updateProductById(int id, String title, float price);
    boolean updateProductObjectById(int id, Product product);

    // delete
    boolean deleteProductById(int id);
    
    //filtering
    ArrayList<Product> selectProductsWherePriceLessThan(float price);

    // save testing data
    void saveTestingData();


}
