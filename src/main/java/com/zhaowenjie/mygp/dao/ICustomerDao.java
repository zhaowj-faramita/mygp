package com.zhaowenjie.mygp.dao;


import com.zhaowenjie.mygp.bean.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICustomerDao extends JpaRepository<Customer,Integer> {
    Customer findById(int id);
    Customer findByUsername(String username);
    void deleteByUsername(String username);
}
