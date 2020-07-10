package com.exmaple.repos;

import com.exmaple.models.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerRepo extends CrudRepository<Customer, Integer> {

    boolean existsByNameAndAge(String name, int age);
}
