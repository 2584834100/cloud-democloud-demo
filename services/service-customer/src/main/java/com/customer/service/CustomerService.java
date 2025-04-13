package com.customer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.customer.entity.Customer;
import customer.dto.CustomerDTO;

public interface CustomerService extends IService<Customer> {

    CustomerDTO getCustomerById(Integer id);
}
