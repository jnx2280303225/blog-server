package com.jnx.blogserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * JacksonConfig
 *
 * @author 蒋楠鑫
 * @since 2021/12/2
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> {
            builder.locale(Locale.CHINA);
            builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
            builder.modules(new JavaTimeSimpleModule());
            builder.serializerByType(Long.class, ToStringSerializer.instance);
        };
    }

    @Bean
    public HttpMessageConverter<?> httpMessageConverter(ObjectMapper objectMapper) {
        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(IBaseEnum.class, new JsonEnumSerializer());
//        simpleModule.addDeserializer(Enum.class, new JsonEnumDeserializer());
        objectMapper.registerModule(simpleModule);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}
