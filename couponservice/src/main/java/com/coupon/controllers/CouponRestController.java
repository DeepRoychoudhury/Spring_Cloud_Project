package com.coupon.controllers;

import com.coupon.model.Coupon;
import com.coupon.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
public class CouponRestController {

    @Autowired
    public CouponRepo repo;

    @RequestMapping(value = "/coupons",method = RequestMethod.POST)
    public Coupon create(@RequestBody Coupon coupon){
        return repo.save(coupon);
    }

    @RequestMapping(value="/coupons/{code}",method = RequestMethod.GET)
    public Coupon getCoupon(@PathVariable("code") String code){
        System.out.println("Server 2");
        return repo.findByCode(code);
    }
}
