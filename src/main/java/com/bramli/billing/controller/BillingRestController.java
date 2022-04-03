package com.bramli.billing.controller;

import com.bramli.billing.entities.Bill;
import com.bramli.billing.feign.FeignClientCustomer;
import com.bramli.billing.feign.FeignClientProduct;
import com.bramli.billing.model.Customer;
import com.bramli.billing.model.Product;
import com.bramli.billing.repository.BillRepository;
import com.bramli.billing.repository.ProductItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class BillingRestController {
    @Autowired
    private BillRepository billRepo;
    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private FeignClientCustomer feignClientCustomer;
    @Autowired
    private FeignClientProduct feignClientProduct;

    @GetMapping("/fullBill/{id}")
    public Bill getBill (@PathVariable(name = "id") Long id){
      Bill bill = billRepo.findById(id).get();
        Customer customer = feignClientCustomer.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(productItem -> {
            Product product = feignClientProduct.getProductById(productItem.getProductID());
            productItem.setProduct(product);
        });

        return bill;
     }
}
