package com.mycart.mycart.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelmapConfig {
    @Bean
    public ModelMapper mp(){
        return new ModelMapper();
    }
}
