package com.zhaowenjie.mygp.service;


import com.zhaowenjie.mygp.bean.Customer;
import com.zhaowenjie.mygp.exception.CustomerException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICustomerService {

    Customer addCustomer(Customer customer) throws CustomerException;

    void deleteCustomer(int id) throws CustomerException;

    void deleteCustomerByUsername(String username)throws CustomerException;

    Customer queryCustomerById(int id) throws CustomerException;

    List<Customer> findAll() throws CustomerException;

    Customer queryCustomerByUsername(String username)throws CustomerException;
}
