package com.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.customer.entity.Customer;
import com.customer.mapper.CustomerMapper;
import com.customer.service.CustomerService;
import customer.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@DubboService
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public CustomerDTO getCustomerById(Integer id) {
        // test add 2
        Customer customer = customerMapper.selectById(id);
        if (Objects.nonNull(customer)) {
            CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
            redisTemplate.opsForValue().set("customerId-" + customerDTO.getId(), customerDTO);
            return customerDTO;
        }
        return null;
    }

}
