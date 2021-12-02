//package com.jnx.blogserver.config;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.google.common.collect.Maps;
//import com.popicorns.scm.infrastructure.commons.IBaseEnum;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * 自定义枚举序列化器
// *
// * @author 蒋楠鑫
// * @since 2021/6/30
// */
//public class JsonEnumSerializer extends JsonSerializer<IBaseEnum> {
//
//    @Override
//    public void serialize(IBaseEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//        if (Objects.isNull(value)) {
//            return;
//        }
//        Map<String, Object> map = Maps.newHashMapWithExpectedSize(2);
//        map.put("code", value.key());
//        map.put("value", value.value());
//        gen.writeString(JSONObject.toJSONString(map));
//    }
//}
