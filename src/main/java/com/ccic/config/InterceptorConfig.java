package com.ccic.config;

import com.ccic.convert.pesonalargumentresolvertest.PersonalArgumentResolver;
import com.ccic.convert.PersonalHttpMessageConvert;
import com.ccic.interceptor.AjaxRedirectInterceptor;
import com.ccic.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author jinbin
 * @date 2018-07-08 22:33
 */

/**
 * webConfig配置文件 可配置Intceptor  messageConvert
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private PersonalHttpMessageConvert personalHttpMessageConvert;

    @Autowired
    private PersonalArgumentResolver personalArgumentResolver;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(ajaxRedirectInterceptor())
//                .addPathPatterns("/*").order(2);
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**").
                excludePathPatterns("/login","/activiti/*","/auth","/scripts/**","/style/**","/images/**","/test","/test1");
//                .addResourceLocations("classpath:/js/");    // 拦截所有请求，判断是否有token

    }
    //添加messageConvert
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(personalHttpMessageConvert);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {

    }

//    @Ignore
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(personalArgumentResolver);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {

    }


    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

    @Bean
    public AjaxRedirectInterceptor ajaxRedirectInterceptor(){
        return new AjaxRedirectInterceptor();
    }



    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    @Nullable
    @Override
    public Validator getValidator() {
        return null;
    }

    @Nullable
    @Override
    public MessageCodesResolver getMessageCodesResolver() {
        return null;
    }


}
