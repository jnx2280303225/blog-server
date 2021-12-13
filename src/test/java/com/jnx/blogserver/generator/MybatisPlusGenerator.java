package com.jnx.blogserver.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 蒋楠鑫
 * @since 2021/6/23
 */
public class MybatisPlusGenerator {

    /**
     * 数据库连接信息
     */
    private static final String URL = "jdbc:mysql://10.0.0.72:3306/ec-scm?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    /**
     * 要生成的数据库名
     */
    private static final String SCHEMA_NAME = "ec-scm";

    /**
     * 文件输出的根目录
     */
    private static final String OUTPUT_DIR = "/src/main/java";

    /**
     * 父级包名
     */
    private static final String PARENT_PACKAGE = "com.popicorns.scm";

    /**
     * 实体类包名
     */
    private static final String ENTITY_PACKAGE = "infrastructure.persistence.db.entity";

    /**
     * Mapper包名
     */
    private static final String MAPPER_PACKAGE = "infrastructure.persistence.db.mapper";

    /**
     * xml文件路径
     */
    private static final String XML_PACKAGE = "infrastructure.persistence.db.mapper.xml";

    /**
     * 生成指定的表名，使用英文逗号分割
     */
    private static final String[] INCLUDE = {
            "shipping_order_receipt_log", "sku_receipt_report"
//            "product_info", "product_sku_label"
//            "purchase_exception_detail"
//            "handle_plan", "purchase_exception_detail", "purchase_exception_img", "purchase_exception_info"
//            "shipping_order"
//            "shipping_order","shipping_order_logistics_info","shipping_order_sku_info",
//            "settlement_apply", "settlement_payment", "supplier_attachment", "supplier_communication", "supplier_info",
//            "supplier_payment_method", "supplier_price_history", "supplier_product", "supplier_product_price"
    };

    /**
     * 需要排除的表名，使用英文逗号分割
     */
    private static final String[] EXCLUDE = {};

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        initGlobalConfig(autoGenerator);
        // 数据源配置
        initDataSourceConfig(autoGenerator);
        // 包名配置
        initPackageConfig(autoGenerator);
        // 生成策略
        initStrategyConfig(autoGenerator);
        // 代码模板配置
        initTemplateConfig(autoGenerator);
        autoGenerator.execute();
        System.out.println("代码生成完成");
    }

    /**
     * 初始化全局配置
     *
     * @param autoGenerator 生成器
     */
    private static void initGlobalConfig(AutoGenerator autoGenerator) {
        GlobalConfig globalConfig = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + OUTPUT_DIR);
        System.out.println("生成的文件路径：" + projectPath + OUTPUT_DIR);
        globalConfig.setAuthor("code generator");
        globalConfig.setOpen(false);
        globalConfig.setIdType(IdType.ASSIGN_ID);
        // 重新生成是否覆盖旧文件
        globalConfig.setFileOverride(true);
        globalConfig.setDateType(DateType.TIME_PACK);
        autoGenerator.setGlobalConfig(globalConfig);
    }

    /**
     * 初始化数据源配置
     *
     * @param autoGenerator 生成器
     */
    private static void initDataSourceConfig(AutoGenerator autoGenerator) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(URL);
        dataSourceConfig.setUsername(USERNAME);
        dataSourceConfig.setPassword(PASSWORD);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setSchemaName(SCHEMA_NAME);
        autoGenerator.setDataSource(dataSourceConfig);
    }

    /**
     * 初始化包名配置
     *
     * @param autoGenerator 生成器
     */
    private static void initPackageConfig(AutoGenerator autoGenerator) {
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(PARENT_PACKAGE);
        packageConfig.setEntity(ENTITY_PACKAGE);
        packageConfig.setMapper(MAPPER_PACKAGE);
        packageConfig.setXml(XML_PACKAGE);
        autoGenerator.setPackageInfo(packageConfig);
    }

    /**
     * 初始化生成策略配置
     *
     * @param autoGenerator 生成器
     */
    private static void initStrategyConfig(AutoGenerator autoGenerator) {
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setFieldPrefix("is_");
        strategyConfig.setEntityLombokModel(true);
        // 生成指定的表名
        if (INCLUDE.length > 0) {
            strategyConfig.setInclude(INCLUDE);
        }
        // 忽略的表名
        if (EXCLUDE.length > 0) {
            strategyConfig.setExclude(EXCLUDE);
        }
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setSuperMapperClass("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        strategyConfig.setEntityTableFieldAnnotationEnable(true);
        strategyConfig.setVersionFieldName("version");
        strategyConfig.setTableFillList(getTableFillList());
        CustomInjectionConfig customInjectionConfig = new CustomInjectionConfig();
        autoGenerator.setCfg(customInjectionConfig);
        autoGenerator.setStrategy(strategyConfig);
    }

    /**
     * 自动填充的字段
     *
     * @return 自动填充的字段集合
     */
    private static List<TableFill> getTableFillList() {
        List<TableFill> list = new LinkedList<>();
//        // 创建人插入时自动填充
//        TableFill creatorId = new TableFill("creator_id", FieldFill.INSERT);
//        list.add(creatorId);
//        TableFill creatorName = new TableFill("creator_name", FieldFill.INSERT);
//        list.add(creatorName);
//        // 更新人插入和修改时都需要填充
//        TableFill updateUserId = new TableFill("update_user_id", FieldFill.INSERT_UPDATE);
//        list.add(updateUserId);
//        TableFill updateUserName = new TableFill("update_user_name", FieldFill.INSERT_UPDATE);
//        list.add(updateUserName);
        return list;
    }

    /**
     * 代码模板配置
     *
     * @param autoGenerator 生成器
     */
    private static void initTemplateConfig(AutoGenerator autoGenerator) {
        TemplateConfig templateConfig = new TemplateConfig();
        // 忽略controller和service
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
    }
}
