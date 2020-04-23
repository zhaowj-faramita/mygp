package com.zhaowenjie.mygp.service.impl;


import com.zhaowenjie.mygp.bean.Customer;
import com.zhaowenjie.mygp.dao.ICustomerDao;
import com.zhaowenjie.mygp.exception.CustomerException;
import com.zhaowenjie.mygp.service.ICustomerService;
import com.zhaowenjie.mygp.utils.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao iCustomerDao;

    @Override
    public Customer addCustomer(Customer customer) throws CustomerException {
        if (customer == null) {
            throw new CustomerException(CodeUtil.DEADLY_CODE, "addCustomer:参数为空");
        } else {
            customer.setRole("Guest");
            return iCustomerDao.save(customer);
        }
    }

    @Override
    public void deleteCustomer(int id) throws CustomerException {
        iCustomerDao.deleteById(id);
    }

    @Override
    public void deleteCustomerByUsername(String username) throws CustomerException {
        iCustomerDao.deleteByUsername(username);
    }

    @Override
    public Customer queryCustomerById(int id) throws CustomerException {
        return iCustomerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() throws CustomerException {
        return iCustomerDao.findAll();
    }

    @Override
    public Customer queryCustomerByUsername(String username) throws CustomerException {
        return iCustomerDao.findByUsername(username);
    }
}
