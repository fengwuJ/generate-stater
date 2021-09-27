package com.generator.generatestater.task;

import com.generator.generatestater.entity.ColumnInfo;
import com.generator.generatestater.entity.Constant;
import com.generator.generatestater.entity.IdStrategy;
import com.generator.generatestater.invoker.base.AbstractInvoker;
import com.generator.generatestater.task.base.AbstractTask;
import com.generator.generatestater.utils.*;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtoTask extends AbstractTask {
    /**
     * 业务表元数据
     */
    private List<ColumnInfo> tableInfos;

    public DtoTask(AbstractInvoker invoker) {
        this.invoker = invoker;
        this.tableInfos = invoker.getTableInfos();
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 构造Entity填充数据
        String className = null;
        String remarks = null;
        className = ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER, invoker.getClassName());
        Map<String, Object> dtoData = new HashMap<>();
        dtoData.put("Configuration", ConfigUtil.getConfiguration());
        dtoData.put("TableName", invoker.getTableName());
        dtoData.put("Properties", entityProperties(invoker));
        dtoData.put("Methods", entityMethods(invoker));

        // 生成Req文件
        String reqFilePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
                + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getReq());
        dtoData.put("ClassName", className+"Req");
        dtoData.put("dtoPath",ConfigUtil.getConfiguration().getPath().getReq());
        String reqFileName = ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER, className+"Req") + ".java";
        FileUtil.generateToJava(FreemarkerConfigUtil.TYPE_DTO, dtoData, reqFilePath, reqFileName);

        // 生成Res文件
        String resFilePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
                + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getRes());
        dtoData.put("ClassName", className+"Res");
        dtoData.put("dtoPath",ConfigUtil.getConfiguration().getPath().getRes());
        String resFileName = ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER, className+"Res") + ".java";
        FileUtil.generateToJava(FreemarkerConfigUtil.TYPE_DTO, dtoData, resFilePath, resFileName);

        // 生成Search文件
        String searchFilePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
                + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getSearch());
        dtoData.put("ClassName", className+"Search");
        dtoData.put("dtoPath",ConfigUtil.getConfiguration().getPath().getSearch());
        String searchFileName = ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER, className+"Search") + ".java";
        FileUtil.generateToJava(FreemarkerConfigUtil.TYPE_DTO, dtoData, searchFilePath, searchFileName);
    }

    /**
     * 生成实体类属性字段
     *
     * @param invoker 执行器
     * @return 属性代码段
     */
    public String entityProperties(AbstractInvoker invoker) {
        StringBuilder sb = new StringBuilder();
        tableInfos.forEach(ForEachUtil.withIndex((info, index) -> {
            if (info.getColumnName().equals(invoker.getForeignKey())) {
                return;
            }
            sb.append(index == 0 ? "" : Constant.SPACE_4);
            generateRemarks(sb, info);
            generateORMAnnotation(sb, info);
            sb.append(Constant.SPACE_4).append(String.format("private %s %s;\n", info.getPropertyType(), info.getPropertyName()));
            sb.append("\n");
        }));
        return sb.toString();
    }

    /**
     * 生成实体类存取方法
     *
     * @param invoker 执行器
     * @return 方法代码段
     */
    public String entityMethods(AbstractInvoker invoker) {
        if (ConfigUtil.getConfiguration().isLombokEnable()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        tableInfos.forEach(ForEachUtil.withIndex((info, index) -> {
            if (info.getColumnName().equals(invoker.getForeignKey())) {
                return;
            }
            String setter = String.format("public void set%s (%s %s) { this.%s = %s; } \n\n", StringUtil.firstToUpperCase(info.getPropertyName()),
                    info.getPropertyType(), info.getPropertyName(), info.getPropertyName(), info.getPropertyName());
            sb.append(index == 0 ? "" : Constant.SPACE_4).append(setter);
            String getter = null;
            if (info.getPropertyType().equals("boolean") || info.getPropertyType().equals("Boolean")) {
                getter = String.format("public %s is%s () { return this.%s; } \n\n", info.getPropertyType(),
                        StringUtil.firstToUpperCase(info.getPropertyName()), info.getPropertyName());
            } else {
                getter = String.format("public %s get%s () { return this.%s; } \n\n", info.getPropertyType(),
                        StringUtil.firstToUpperCase(info.getPropertyName()), info.getPropertyName());
            }
            sb.append(Constant.SPACE_4).append(getter);
        }));
        return sb.toString();
    }

    /**
     * 为实体属性生成注释
     *
     * @param sb   StringBuilder对象
     * @param info 列属性
     */
    public void generateRemarks(StringBuilder sb, ColumnInfo info) {
        sb.append("/**").append("\n");
        sb.append(Constant.SPACE_4).append(" * ").append(info.getRemarks()).append("\n");
        sb.append(Constant.SPACE_4).append(" */").append("\n");
    }

    /**
     * 为实体属性生成swagger注解
     * 我们不建议在entity（do）中使用swagger注解，在dto和vo中使用swagger注解更为优雅
     *
     * @param sb   StringBuilder对象
     * @param info 列属性
     */
    public void generateSwaggerAnnotation(StringBuilder sb, ColumnInfo info) {
        if (!ConfigUtil.getConfiguration().isSwaggerEnable()) {
            return;
        }
        sb.append(String.format("@ApiModelProperty(value = \"%s\", dataType = \"%s\")",
                info.getRemarks(), info.getPropertyType()));
        sb.append("\n");
    }

    /**
     * 为实体属性生成Orm框架（jpa/mybatis-plus）注解
     *
     * @param sb   StringBuilder对象
     * @param info 列属性
     */
    public void generateORMAnnotation(StringBuilder sb, ColumnInfo info) {
        if (ConfigUtil.getConfiguration().isMybatisPlusEnable()) {
            if (info.isPrimaryKey()) {
                if (ConfigUtil.getConfiguration().getIdStrategy() == null || ConfigUtil.getConfiguration().getIdStrategy() == IdStrategy.AUTO) {
                    sb.append(Constant.SPACE_4).append(String.format("@TableId(value = \"%s\", type = IdType.AUTO)\n", info.getColumnName()));
                } else if (ConfigUtil.getConfiguration().getIdStrategy() == IdStrategy.UUID) {
                    sb.append(Constant.SPACE_4).append(String.format("@TableId(value = \"%s\", type = IdType.ASSIGN_UUID)\n", info.getColumnName()));
                }
            } else {
                sb.append(Constant.SPACE_4).append(String.format("@TableField(value = \"%s\")\n", info.getColumnName()));
            }
        } else if (ConfigUtil.getConfiguration().isJpaEnable()) {
            if (info.isPrimaryKey()) {
                if (ConfigUtil.getConfiguration().getIdStrategy() == null || ConfigUtil.getConfiguration().getIdStrategy() == IdStrategy.AUTO) {
                    sb.append(Constant.SPACE_4).append("@Id\n");
                    sb.append(Constant.SPACE_4).append("@GeneratedValue(strategy = GenerationType.IDENTITY)\n");
                } else if (ConfigUtil.getConfiguration().getIdStrategy() == IdStrategy.UUID) {
                    sb.append(Constant.SPACE_4).append("@Id\n");
                    sb.append(Constant.SPACE_4).append("@GeneratedValue(generator = \"uuidGenerator\")\n");
                    sb.append(Constant.SPACE_4).append("@GenericGenerator(name = \"uuidGenerator\", strategy = \"uuid\")\n");
                }
            }
            sb.append(Constant.SPACE_4).append(String.format("@Column(name = \"%s\")\n", info.getColumnName()));
        }else if (ConfigUtil.getConfiguration().isTkMapperEnable()){
            if (info.isPrimaryKey()) {
                if (ConfigUtil.getConfiguration().getIdStrategy() == null || ConfigUtil.getConfiguration().getIdStrategy() == IdStrategy.AUTO) {
                    sb.append(Constant.SPACE_4).append("@Id\n");
                } else if (ConfigUtil.getConfiguration().getIdStrategy() == IdStrategy.UUID) {
                    sb.append(Constant.SPACE_4).append("@Id\n");
                    sb.append(Constant.SPACE_4).append("@GeneratedValue(generator = \"uuidGenerator\")\n");
                    sb.append(Constant.SPACE_4).append("@GenericGenerator(name = \"uuidGenerator\", strategy = \"uuid\")\n");
                }
            }
        }
    }

}
