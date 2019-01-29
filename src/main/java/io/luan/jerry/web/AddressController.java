package io.luan.jerry.web;

import io.luan.jerry.address.domain.Address;
import io.luan.jerry.address.domain.DeliveryAddress;
import io.luan.jerry.address.service.DeliveryAddressService;
import io.luan.jerry.address.vm.DeliveryAddressVM;
import io.luan.jerry.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
public class AddressController {

    private final DeliveryAddressService addressService;

    @Autowired
    public AddressController(DeliveryAddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/address")
    public ModelAndView addressList() {
        var user = SecurityUtils.getCurrentUser();
        var list = addressService.findAllByUserId(user.getId());

        var vmList = list.stream().map(DeliveryAddressVM::new).collect(Collectors.toList());
        var mav = new ModelAndView("addressList");
        mav.addObject("addresses", vmList);
        return mav;
    }

    @GetMapping("/address/new")
    public ModelAndView newAddress() {
        var user = SecurityUtils.getCurrentUser();

        var mav = new ModelAndView("addOrEditAddress");
        mav.addObject("request", new DeliveryAddressVM());
        mav.addObject("action", "/address/new");
        return mav;
    }

    @GetMapping("/address/edit")
    public ModelAndView editAddress(@RequestParam("id") Long id) {
        var user = SecurityUtils.getCurrentUser();

        var address = addressService.findById(id);

        var mav = new ModelAndView("addOrEditAddress");
        mav.addObject("request", new DeliveryAddressVM(address));
        mav.addObject("action", "/address/edit");
        return mav;
    }

    @PostMapping("/address/new")
    public ModelAndView postAddress(@ModelAttribute DeliveryAddressVM request) {
        var user = SecurityUtils.getCurrentUser();

        System.out.println(request);

        var address = new DeliveryAddress();
        address.setUserId(user.getId());
        address.setReceiver(request.getReceiver());
        address.setPhone(request.getPhone());
        address.setAddress(new Address(request.getAddress()));
        address.setIsDefault(request.getIsDefault());

        addressService.save(address);

        var mav = new ModelAndView("redirect:/address");
        return mav;
    }

    @PostMapping("/address/edit")
    public ModelAndView updateAddress(@ModelAttribute DeliveryAddressVM request) {
        var user = SecurityUtils.getCurrentUser();

        var address = addressService.findById(request.getId());
        address.setReceiver(request.getReceiver());
        address.setPhone(request.getPhone());
        address.setAddress(new Address(request.getAddress()));
        address.setIsDefault(request.getIsDefault());

        addressService.save(address);

        var mav = new ModelAndView("redirect:/address");
        return mav;
    }
}
