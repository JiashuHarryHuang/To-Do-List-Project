package com.to_do_list.config;

import com.to_do_list.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * Add our own JacksonObjectMapper to the list of converters
     * @param converters List of converters
     */
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("Adding message converter");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //把新转换器加入converters集合，并设置其优先级为最高
        converters.add(0, messageConverter);

    }

    /**
     * Static resource mapping
     * @param registry Mapping object
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("static resource mapping");
        //Map all access paths to resources under static folder
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
}
