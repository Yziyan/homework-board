package run.hxtia.workbd.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import run.hxtia.workbd.common.filter.ErrorFilter;
import run.hxtia.workbd.common.prop.WorkBoardProperties;

import javax.servlet.Filter;

/**
 * Web应用配置
 * 1、跨域配置
 * 2、注册自定义拦截器
 */
@Configuration
@RequiredArgsConstructor
public class WebCfg implements WebMvcConfigurer {

    private final WorkBoardProperties properties;

    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(properties.getCfg().getCorsOrigins())
            .allowCredentials(true)
            .allowedHeaders("*")
            .exposedHeaders("Content-Type")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }

    /**
     * 注册Filter
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();

        // 设置Filter
        bean.setFilter(new ErrorFilter());
        bean.addUrlPatterns("/*");
        // 将其Filter排在第一个
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射 /upload/image/** 到文件系统中的 /home/work-board/upload/image/
        registry.addResourceHandler("/upload/image/**")
            .addResourceLocations(properties.getUpload().getImgMapPath());
    }

}
