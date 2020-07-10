package com.exmaple.services;

import com.exmaple.models.Customer;
import com.exmaple.models.Product;
import com.exmaple.repos.ICustomerRepo;
import com.exmaple.repos.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    ICustomerRepo custRepo;

    @Autowired
    IProductRepo prodRepo;


    @Override
    public boolean register(String name, int age) { // name as an username which is unique
        if (custRepo.existsByNameAndAge(name, age)) {
            return false;
        }
        custRepo.save(new Customer(name, age));
        return true;
    }

    @Override
    public ArrayList<Product> getAllPurchasedProductsByCustomerId(int id) throws Exception {
        if (id > 0) {
            if (custRepo.existsById(id)) {
                Customer customer = custRepo.findById(id).get();
                ArrayList<Product> purchasedProduct = prodRepo.findByCustomer(customer);
                return purchasedProduct;
            }
        }
        throw new Exception("Id is not correct and there is no customer with that id in the system");

    }

    @Override
    public boolean buyProducts(Collection<Product> purchasedProducts, int id) throws Exception{
        if (id > 0) {
            if (custRepo.existsById(id)) {
                Customer cust = custRepo.findById(id).get(); // parbauda, vai sistema ir tads pircejs

                for(Product p: purchasedProducts) { // iet cauri prod sarakstam

                    Product prod = prodRepo.findByTitleAndPrice(p.getTitle(), p.getPrice()); // getting
                    prod.setCustomer(cust); // updating
                    prodRepo.save(prod); // saving
                    return true;
                }
            }
        }
        throw new Exception("Id is not correct and there is no customer with that id in the system");
    }

    @Override
    public Customer selectOneCustomerById(int id) throws  Exception{
        if (id > 0) {
            if(custRepo.existsById(id)) {
                return custRepo.findById(id).get(); // vajag get(), ja censas atgut byID, citadi ir Optional<Customer>
            }
        }
        throw new Exception("Id is not correct and there is no customer with that id in the system");
    }

    @Override
    public ArrayList<Customer> selectAllCustomers() {
        return (ArrayList<Customer>)custRepo.findAll();
    }
}
