package com.example.maktabproject1.servicemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("web_controller")
public class WebController {

    @GetMapping("/users/update")
    public String userUpdate() { return "users/user-update"; }

    @GetMapping("/users/register")
    public String userRegister() { return "users/user-register"; }

    @GetMapping("/users/list")
    public String userList() { return "users/user-list"; }

    @GetMapping("/users/image")
    public String userImage() { return "users/user-image-upload"; }

    @GetMapping("/users/get")
    public String userGet() { return "users/user-get"; }

    @GetMapping("/users/delete")
    public String userDelete() { return "users/user-delete"; }

    @GetMapping("/sub-services/update")
    public String subServiceUpdate() { return "sub-services/sub-service-update"; }

    @GetMapping("/sub-services/list")
    public String subServiceList() { return "sub-services/sub-service-list"; }

    @GetMapping("/sub-services/get")
    public String subServiceGet() { return "sub-services/sub-service-get"; }

    @GetMapping("/sub-services/delete")
    public String subServiceDelete() { return "sub-services/sub-service-delete"; }

    @GetMapping("/sub-services/create")
    public String subServiceCreate() { return "sub-services/sub-service-create"; }

    @GetMapping("/specialists/update")
    public String specialistUpdate() { return "specialists/specialist-update"; }

    @GetMapping("/specialists/listTest")
    public String specialistList() { return "specialists/specialist-list"; }

    @GetMapping("/specialists/image-upload")
    public String specialistImageUpload() { return "specialists/specialist-image-upload"; }

    @GetMapping("/specialists/get")
    public String specialistGet() { return "specialists/specialist-get"; }

    @GetMapping("/specialists/delete")
    public String specialistDelete() { return "specialists/specialist-delete"; }

    @GetMapping("/specialists/add")
    public String specialistAdd() { return "specialists/specialist-add"; }

    @GetMapping("/service-categories/create")
    public String serviceCategoryCreate() { return "service-categories/service-category-create"; }

    @GetMapping("/service-categories/delete")
    public String serviceCategoryDelete() { return "service-categories/service-category-delete"; }

    @GetMapping("/service-categories/list")
    public String serviceCategoryList() { return "service-categories/service-category-list"; }

    @GetMapping("/service-categories/get")
    public String serviceCategoryGet() { return "service-categories/service-category-get"; }

    @GetMapping("/service-categories/update")
    public String serviceCategoryUpdate() { return "service-categories/service-category-update"; }

    @GetMapping("/orders/create")
    public String orderCreate() { return "orders/order-create"; }

    @GetMapping("/orders/delete")
    public String orderDelete() { return "orders/order-delete"; }

    @GetMapping("/orders/get")
    public String orderGet() { return "orders/order-get"; }

    @GetMapping("/orders/list")
    public String orderList() { return "orders/order-list"; }

    @GetMapping("/orders/update")
    public String orderUpdate() { return "orders/order-update"; }
}