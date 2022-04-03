package com.bramli.billing.feign;

import com.bramli.billing.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER")
public interface FeignClientCustomer {
    @GetMapping(path = "/customers/{id}")
    Customer getCustomerById(@PathVariable(name = "id") Long id);
}
