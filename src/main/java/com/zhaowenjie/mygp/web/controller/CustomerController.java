package com.zhaowenjie.mygp.web.controller;


import com.zhaowenjie.mygp.bean.Customer;
import com.zhaowenjie.mygp.config.Message;
import com.zhaowenjie.mygp.config.MessageUtil;
import com.zhaowenjie.mygp.service.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@Api(description = "用户处理器")
@CrossOrigin
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    @ApiOperation("添加新的用户或者修改一个已有的用户")
    @PostMapping("/addOrUpdate")
    @ApiImplicitParam(name = "id",value = "提供id以修改已有的用户",paramType = "query",dataType = "int")
    public Message addCustomer(Customer customer) {
        iCustomerService.addCustomer(customer);
        return MessageUtil.success();
    }

    @ApiOperation("通过id删除用户")
    @GetMapping("/deleteCustomer")
    @ApiImplicitParam(name = "id",value = "要删除用户的主键",paramType = "query",required = true)
    public Message deleteCustomer(int id) {
        iCustomerService.deleteCustomer(id);
        return MessageUtil.success();
    }

    @ApiOperation("通过用户名删除用户")
    @GetMapping("/deleteCustomerByUsername")
    @ApiImplicitParam(name = "username",value = "要删除用户的用户名",paramType = "query",required = true)
    public Message deleteCustomerByUsername(String username) {
        iCustomerService.deleteCustomerByUsername(username);
        return MessageUtil.success();
    }

    @ApiOperation("根据主键查询用户")
    @GetMapping("/query/ById")
    @ApiImplicitParam(name = "id", value = "要查询用户的主键", paramType = "query", required = true)
    public Message<Customer> queryCustomerById(int id) {
        return MessageUtil.success(iCustomerService.queryCustomerById(id));
    }

    @ApiOperation("查询所有用户")
    @GetMapping("/find/All")
    public Message<List<Customer>> findAllCustomer() {
        return MessageUtil.success(iCustomerService.findAll());
    }

}
