package com.exmaple.controllers;

import com.exmaple.models.Customer;
import com.exmaple.models.Product;
import com.exmaple.services.ICustomerService;
import com.exmaple.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ICustomerService custService;

    @Autowired
    IProductService prodService;

    @GetMapping("/showMyProducts/{id}") // url address -> localhost:8080/customer/showMyProducts/{id}
    public String getShowMyProductsByCustId(@PathVariable(name = "id") int id, Model model) {
        try {
            model.addAttribute("innerObjectProd", custService.getAllPurchasedProductsByCustomerId(id));
            model.addAttribute("innerObjectCustName", custService.selectOneCustomerById(id).getName());
            return "show-all-customer-products-page"; // show-all-customer-products-page.html
        } catch (Exception e) {
            return "error";
        }
    }

    // showAllCustomers

    @GetMapping("/showAllCustomers") //url address -> localhost:8080/customer/showAllCustomers
    public String getShowAllCustomers(Model model) {
        model.addAttribute("innerObject", custService.selectAllCustomers());

        return "show-all-customers-page"; // show-all-customers-page.html
    }

    //insertNewCustomer
    @GetMapping("/insertNewCustomer") //url address -> localhost:8080/customer/insertNewCustomer
    public String getInsertNewCustomer(Customer customer) { // empty customer for filling data
        return "insert-new-customer-page"; // insert-new-customer-page.html
    }

    @PostMapping("/insertNewCustomer")
    public String postInsertNewCustomer(@Valid Customer customer, BindingResult result) {
        System.out.println(customer);

        if (result.hasErrors()) {
            return "insert-new-customer-page";
        }
        custService.register(customer.getName(), customer.getAge());
        return "redirect:/customer/showAllCustomers";
    }

    //buyProducts
    @GetMapping("/buyProducts/{id}") //url address -> localhost:8080/customer/buyProducts
    public String getBuyByCustId(@PathVariable(name = "id") int id, Model model, Customer customer) {
        try {
            model.addAttribute("innerObjectCustName", custService.selectOneCustomerById(id).getName());
            model.addAttribute("allCustomerProducts", prodService.selectAllProducts());
            return "buy-products-page"; // buy-products-page.html
        } catch (Exception e) {
            return "error";
        }

    }

    @PostMapping("/buyProducts/{id}")
    public String postBuyByCustId(@PathVariable(name = "id") int id, Customer customer) {
        for (Product prod : customer.getAllCustomerProducts()) {
            System.out.println(prod.getTitle() + " " + prod.getPrice());
        }

        try {
            custService.buyProducts(customer.getAllCustomerProducts(), id);
            return "redirect:/customer/showMyProducts/" + id; // ja ir redirect, jānorāda konkrēts url
            // localhost:8080/customer/showMyProducts/2
        } catch (Exception e) {
            return "error";
        }
    }
}