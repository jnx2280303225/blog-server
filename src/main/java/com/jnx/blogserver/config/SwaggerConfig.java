package com.jnx.blogserver.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.LinkedList;
import java.util.List;

/**
 * Swagger API文档相关配置
 *
 * @author 蒋楠鑫
 * @since 2021/12/2
 */
@Configuration
@EnableKnife4j
@Profile({"!prod"})
public class SwaggerConfig {

    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.jnx.blogserver.api.user")
                .title("博客系统后台服务")
                .description("博客系统后台服务接口文档")
                .contactName("blog-server")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }

    @Bean
    public Docket createRestApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        //添加head参数start
        RequestParameterBuilder tokenPar = new RequestParameterBuilder();
        List<RequestParameter> pars = new LinkedList<>();
        tokenPar.name("client_ip").description("本机局域网IP").in(ParameterType.HEADER).required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("博客系统后台服务")
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build().globalRequestParameters(pars);
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .build();
    }

    @Data
    @Builder
    public static class SwaggerProperties {
        /**
         * API文档生成基础路径
         */
        private String apiBasePackage;
        /**
         * 是否要启用登录认证
         */
        private boolean enableSecurity;
        /**
         * 文档标题
         */
        private String title;
        /**
         * 文档描述
         */
        private String description;
        /**
         * 文档版本
         */
        private String version;
        /**
         * 文档联系人姓名
         */
        private String contactName;
        /**
         * 文档联系人网址
         */
        private String contactUrl;
        /**
         * 文档联系人邮箱
         */
        private String contactEmail;
    }
}
