package com.demosecurity.demosecurity.controllers;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demosecurity.demosecurity.dto.ResponseData;
import com.demosecurity.demosecurity.entities.Account;
import com.demosecurity.demosecurity.handlerexc.DocumentNotFoundException;
import com.demosecurity.demosecurity.service.MainService;

@SuppressWarnings("unchecked")
@RestController
public class MainController {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public MainService mainService;

    @GetMapping("/getUser")
    public String getUser() {
        return "Hello User";
    }

    @GetMapping("/getAdmin")
    public String getAdmin() {
        return "Hello Admin";
    }

    @GetMapping("/get")
    public String getData() {
        return "Hello anyone";
    }

    @PostMapping("/create")
    public String createData(@RequestBody Account acc) throws InterruptedException, ExecutionException {
        return mainService.createData(acc);
    }

    @GetMapping("/getData")
    public ResponseData<Object> getDataSpec(@RequestParam String name)
            throws InterruptedException, ExecutionException, DocumentNotFoundException, BeansException {
        ResponseData<Object> responseData = applicationContext.getBean(ResponseData.class);
        try {
            responseData.setMessage("Success");
            responseData.setStatus("200");
            responseData.setPayload(mainService.getData(name));
            return responseData;
        } catch (InterruptedException | ExecutionException | DocumentNotFoundException | BeansException e) {
            responseData.setMessage("Fail");
            responseData.setStatus("4xx");
            responseData.setPayload(e.getMessage());
            return responseData;
        }
    }

    @GetMapping("/getAllData")
    public ResponseData<Object> getAllData()
            throws InterruptedException, ExecutionException, DocumentNotFoundException, BeansException {
        ResponseData<Object> responseData = applicationContext.getBean(ResponseData.class);
        responseData.setMessage("Success");
        responseData.setStatus("200");
        responseData.setPayload(mainService.getAllData());
        List<Account> data = mainService.getAllData();
        System.out.println(data.size());
        return responseData;

    }
}
