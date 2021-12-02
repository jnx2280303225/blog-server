//package com.jnx.blogserver.config;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.BeanProperty;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
//import com.popicorns.scm.infrastructure.commons.IBaseEnum;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.compress.utils.Sets;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Objects;
//
///**
// * 自定义枚举反序列化器
// *
// * @author 蒋楠鑫
// * @since 2021/6/29
// */
//@Slf4j
//public class JsonEnumDeserializer extends JsonDeserializer<Enum<?>> implements ContextualDeserializer {
//
//    @Setter
//    private Class<?> clazz;
//
//    @Override
//    public Enum<?> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//        Class<?> enumType = clazz;
//        if (Objects.isNull(enumType) || !enumType.isEnum()) {
//            return null;
//        }
//        // 只处理实现了IBaseEnum接口的枚举
//        Class<?>[] interfaces = enumType.getInterfaces();
//        HashSet<Class<?>> classes = Sets.newHashSet(interfaces);
//        if (!classes.contains(IBaseEnum.class)) {
//            return null;
//        }
//        String text = p.getText();
//        Enum<?>[] enumConstants = (Enum<?>[]) enumType.getEnumConstants();
//        // 根据key进行反序列化
//        for (Enum<?> e : enumConstants) {
//            IBaseEnum baseEnum = (IBaseEnum) e;
//            if (Objects.equals(baseEnum.key().toString(), text)) {
//                return e;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
//        Class<?> rawCls = ctxt.getContextualType().getRawClass();
//        JsonEnumDeserializer converter = new JsonEnumDeserializer();
//        converter.setClazz(rawCls);
//        return converter;
//    }
//}
