package xyz.riocode.scoutpro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.riocode.scoutpro.interceptor.LogInterceptor;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public Validator validator(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
    }
    
}
