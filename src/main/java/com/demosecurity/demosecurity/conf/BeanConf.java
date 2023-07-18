package com.demosecurity.demosecurity.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demosecurity.demosecurity.dto.ResponseData;

@Configuration
public class BeanConf {

    @Bean
    public ResponseData<Object> response() {
        return new ResponseData<Object>();
    }
}
