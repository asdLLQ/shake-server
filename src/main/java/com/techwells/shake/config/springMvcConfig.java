package com.techwells.shake.config;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;

/**
 * springmvc的配置类
 */
@Configuration
public class springMvcConfig {

    //注入返回Json格式的日期格式
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        ObjectMapper objectMapper=new ObjectMapper();  //创建ObjectMapper
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));  //指定返回Json数据的日期格式
        MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();//创建转化器
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    //添加跨域的功能
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
//                        .allowedOrigins("http://192.168.1.97")
//                        .allowedMethods("GET", "POST")
//                        .allowCredentials(false).maxAge(3600);
                ;   //对所有的路径都支持跨域的访问
            }
        };
    }
    
    
    
//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(
//                dispatcherServlet);
//        registration.addUrlMappings("*.do");
//        return registration;
//    }
//    
    
//    @Bean
//    public PathMatchConfigurer configurePathMatch() {
//    	PathMatchConfigurer configurer=new PathMatchConfigurer();
//        configurer.setUseSuffixPatternMatch(true)
//              .setUseTrailingSlashMatch(true);
//        return configurer;
//      }
    
    
   

    





}
