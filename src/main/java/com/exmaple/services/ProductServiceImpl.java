package com.exmaple.services;

import com.exmaple.enums.ProductType;
import com.exmaple.models.Customer;
import com.exmaple.models.Product;
import com.exmaple.repos.ICustomerRepo;
import com.exmaple.repos.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    IProductRepo prodRepo;

    @Autowired
    ICustomerRepo custRepo;

    @Override
    public Product selectOneProductByID(int id) throws Exception {
        if (id > 0) {
            if(prodRepo.existsById(id)) {
                return prodRepo.findById(id).get(); // vajag get(), ja censas atgut byID
            }
        }
        throw new Exception("Id is not correct and there is not product with that id in system");
    }

    @Override
    public ArrayList<Product> selectAllProducts() {
        return (ArrayList<Product>)prodRepo.findAll();
    }

    @Override
    public boolean insertNewProduct(String title, float price, ProductType type) {
        if (prodRepo.existsByTitleAndPriceAndType(title, price, type)) {
            return false;
        }
        Product prod = new Product(title, price, type);
        prodRepo.save(prod);
        return true;
    }

    @Override
    public boolean insertNewProductByObject(Product product) {
        if (prodRepo.existsByTitleAndPriceAndType(product.getTitle(), product.getPrice(), product.getType())) {
            return false;
        }
        Product prod = new Product(product.getTitle(), product.getPrice(), product.getType());
        prodRepo.save(prod);
        return true;
    }

    @Override
    public boolean updateProductById(int id, String title, float price) {
        if (id > 0) {
            if (prodRepo.existsById(id)) {
                Product productToUpdate = prodRepo.findById(id).get();
                productToUpdate.setTitle(title);
                productToUpdate.setPrice(price);
                // TODO set also a new type
                prodRepo.save(productToUpdate);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateProductObjectById(int id, Product product) {
        if (id > 0) {
            if (prodRepo.existsById(id)) {
                Product productToUpdate = prodRepo.findById(id).get();
                productToUpdate.setTitle(product.getTitle());
                productToUpdate.setPrice(product.getPrice());
                // TODO set also a new type
                prodRepo.save(productToUpdate);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteProductById(int id) {
        if (id > 0) {
            if (prodRepo.existsById(id)) {
                prodRepo.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Product> selectProductsWherePriceLessThan(float price) {
        ArrayList<Product> productsWithPriceLessThanTreshold = prodRepo.findByPriceLessThan(price);
        if (productsWithPriceLessThanTreshold != null) {
            return  productsWithPriceLessThanTreshold;
        }
        // TODO throw Exception
        return new ArrayList<>();
    }

    @Override
    public void saveTestingData() {

        Customer cust1 = new Customer("Janis", 32);
        Customer cust2 = new Customer("Baiba", 76);

        custRepo.save(cust1);
        custRepo.save(cust2);


        prodRepo.save(new Product("bread", 1.99f, ProductType.BakeryType, cust1));
        prodRepo.save(new Product("window", 56.67f, ProductType.OtherType));
        prodRepo.save(new Product("apple", 0.56f, ProductType.OtherType, cust2));
    }


}
