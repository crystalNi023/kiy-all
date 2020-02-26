package com.dec.pro.mapper.customer;

import org.apache.ibatis.annotations.Param;

import com.dec.pro.entity.customer.Customer;
import com.dec.pro.mapper.BaseMapper;

public interface CustomerMapper extends BaseMapper<Customer>{
	public Customer getCustomerByUserId(@Param("userId") String userId);
	public Customer getCustomerByPhone(@Param("phone") String phone);
}
