package com.arsenyvekshin.lab1_backend.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TokenAuthFilter> myFilterRegistrationBean() {
        FilterRegistrationBean<TokenAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TokenAuthFilter());
        registrationBean.addUrlPatterns("/route/*");
        return registrationBean;
    }
}
