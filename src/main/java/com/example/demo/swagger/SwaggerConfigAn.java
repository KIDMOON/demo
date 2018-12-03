package com.example.demo.swagger;

import com.example.demo.swagger.SwaggerConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ImportAutoConfiguration({SwaggerConfig.class})
public @interface SwaggerConfigAn {
}
