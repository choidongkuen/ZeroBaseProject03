package com.example.zerobaseproject03.configuration;

import com.example.zerobaseproject03.ZeroBaseProject03Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ZeroBaseProject03Application.class);
    }

}
