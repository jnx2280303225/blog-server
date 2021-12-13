package com.jnx.blogserver.generator;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 蒋楠鑫
 * @since 2021/6/23
 */
public class CustomInjectionConfig extends InjectionConfig {

    /**
     * 注入自定义 Map 对象，针对所有表的全局参数
     */
    @Override
    public void initMap() {
        // 解析配置的xml
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read("./src/test/java/com/popicorns/scm/generator/ColumnTypeMapping.xml");
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("ColumnTypeMapping.xml解析出现错误");
            return;
        }
        if (document == null) {
            return;
        }
        Element rootElement = document.getRootElement();
        List<Element> mappingList = rootElement.elements("mapping");
        // 取出每一条配置信息，将表字段名和java实体类属性的映射关系加入map
        Map<String, Pair<String, String>> map = new HashMap<>(mappingList.size());
        mappingList.forEach(mapping -> {
            String columnName = mapping.attributeValue("columnName");
            String fieldType = mapping.attributeValue("fieldType");
            String className = mapping.attributeValue("className");
            map.put(columnName, Pair.of(fieldType, className));
        });
        ConfigBuilder config = super.getConfig();
        // 遍历每张表的每个字段，将指定的表字段转换成对应的类型
        config.getTableInfoList().forEach(tableInfo -> tableInfo.getFields().forEach(tableField -> {
            if (map.containsKey(tableField.getName())) {
                // xml中配置的java类型
                Pair<String, String> pair = map.get(tableField.getName());
                System.out.println("字段名称：" + tableField.getName() + "，对应的Java类型：" + pair.getLeft());
                tableField.setColumnType(new IColumnType() {
                    @Override
                    public String getType() {
                        return pair.getLeft();
                    }

                    @Override
                    public String getPkg() {
                        return pair.getRight();
                    }
                });
            }
        }));
    }
}
