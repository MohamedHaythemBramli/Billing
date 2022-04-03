package com.bramli.billing;

import com.bramli.billing.entities.Bill;
import com.bramli.billing.entities.ProductItem;
import com.bramli.billing.feign.FeignClientCustomer;
import com.bramli.billing.feign.FeignClientProduct;
import com.bramli.billing.model.Customer;
import com.bramli.billing.model.Product;
import com.bramli.billing.repository.BillRepository;
import com.bramli.billing.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;


@SpringBootApplication
@EnableFeignClients
public class BillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BillRepository billRepo,
							ProductItemRepository itemRepo,
							FeignClientProduct feignClientProduct,
							FeignClientCustomer feignClientCustomer){
		return args -> {
			Customer customer= feignClientCustomer.getCustomerById(1L);
			Bill bill =billRepo.save(new Bill(null,new Date(),null,customer.getId(),null));
			/*System.out.println(customer.getId());
			System.out.println(customer.getName());
			System.out.println(customer.getEmail());*/
			PagedModel<Product> productPagedModel=feignClientProduct.pageProducts(0,3);
			productPagedModel.forEach(product -> {
				ProductItem productItem = new ProductItem();
				productItem.setPrice(product.getPrice());
				productItem.setQuantity(1+new Random().nextInt(100));
				productItem.setProductID(product.getId());
				productItem.setBill(bill);
				itemRepo.save(productItem);
			});

		};
 }
}
