package com.product;

import com.product.model.Coupon;
import com.product.model.Product;
import com.product.repos.ProductRepo;
import com.product.restclients.CouponClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productapi")
public class ProductRestController {

    @Autowired
    CouponClient couponClient;

    @Autowired
    private ProductRepo repo;

    @RequestMapping(value = "/products",method = RequestMethod.POST)
    @Retry(name = "product-api",fallbackMethod = "handleError")
    public Product create(@RequestBody Product product){
        Coupon coupon = couponClient.getCoupon(product.getCouponCode());
        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return repo.save(product);
    }

    public Product handleError(Product product,Exception exception){
        System.out.println("Inside Handle Error");
        return product;
    }
}
