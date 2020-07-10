package com.exmaple.controllers;

import com.exmaple.models.Product;
import com.exmaple.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product") // pievieno url "product" vardu
public class ProductController {

    @Autowired
    IProductService prodService;

    @GetMapping("/showAllProducts") // url address -> localhost:8080/product/showAllProducts
    public String getShowAllProducts(Model model) {
        System.out.println("showAllProducts");
        model.addAttribute("innerObject", prodService.selectAllProducts());

        return "show-all-products-page"; // show-all-products-page.html

    }

    @GetMapping("/saveTestingData") // url address -> localhost:8080/product/saveTestingData
    public String getSaveTestingData() {
        System.out.println("saveTestingData");
        prodService.saveTestingData();
        return "hello-page"; // hello-page.html
    }

    @GetMapping("/gitTest")
    public String getGitTest(){
        return "hello-page"; // hello-page.html
    }

    // show one product by id
    @GetMapping("/showAllProducts/{id}") // url address -> localhost:8080/product/showAllProducts/{id}
    public String getShowAllProductsId(@PathVariable(name = "id") int id, Model model) {
        try {
            model.addAttribute("innerObject", prodService.selectOneProductByID(id));
            return "show-one-product-page"; // show-one-product-page.html
        } catch (Exception e) {
            return "error";
        }

    }

    //insert new product
    @GetMapping("/insertOneProduct") // url address -> localhost:8080/product/insertOneProduct
    public String getInsertOneProduct(Product product) { // it's empty product for data filling
        return "insert-one-product-page"; // insert-one-product-page.html
    }

    @PostMapping("/insertOneProduct")
    public String postInsertOneProduct(@Valid Product product, BindingResult result) {
        System.out.println(product);

        if (result.hasErrors()) {
            return "insert-one-product-page";
        }
        //prodService.insertNewProduct(product.getTitle(), product.getPrice());
        prodService.insertNewProductByObject(product);
        return "redirect:/product/showAllProducts";
    }

    //update product
    @GetMapping("/updateProduct/{id}") // url address -> localhost:8080/product/updateProduct/{id}
    public String getUpdateProductById(@PathVariable(name = "id") int id, Model model, Product product) {

        try {
            Product productForUpdate = prodService.selectOneProductByID(id);
            model.addAttribute("product", productForUpdate);
            return "update-one-product-page";

        } catch (Exception e) {
            return "error";
        }
    }

    @PostMapping("/updateProduct/{id}")// url address -> localhost:8080/product/updateProduct
    public String postUpdateProductById(@PathVariable(name = "id") int id, Product product) {

        // TODO add validation part
        System.out.println(product);
        prodService.updateProductObjectById(id, product);
        return "redirect:/product/showAllProducts";
    }

    // delete
    @GetMapping("/deleteProduct") // url address -> localhost:8080/product/deleteProduct
    public String getDeleteProduct(Product product)
    {
        return "delete-one-product-page";
    }

    @PostMapping("/deleteProduct") // url address -> localhost:8080/product/deleteProduct
    public String postDeleteProduct(@RequestParam(name="id") int id, Model model, Product product)
    {
        prodService.deleteProductById(id);

        return "redirect:/product/showAllProducts";
    }


}
