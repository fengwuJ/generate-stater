package com.generator.generatestater.task;

import com.generator.generatestater.entity.ColumnInfo;
import com.generator.generatestater.entity.Configuration;
import com.generator.generatestater.entity.Constant;
import com.generator.generatestater.invoker.base.AbstractInvoker;
import com.generator.generatestater.task.base.AbstractTask;
import com.generator.generatestater.utils.*;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapperTask extends AbstractTask {

    public MapperTask(AbstractInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 构造Mapper填充数据
        int generateXmlType = FreemarkerConfigUtil.TYPE_EMPTYMAPPER;
        Map<String, Object> mapperData = new HashMap<>();
        Configuration configuration = ConfigUtil.getConfiguration();
        mapperData.put("Configuration", ConfigUtil.getConfiguration());
        mapperData.put("ClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()));
        mapperData.put("EntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName())));
        mapperData.put("DaoClassName", ConfigUtil.getConfiguration().getName().getDao()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()));
        mapperData.put("ResultMap", resultMap());
        if (!StringUtil.isEmpty(invoker.getRelationalTableName())) {
            // 多对多
            mapperData.put("Association", "");
            mapperData.put("Collection", collection());
        } else if (!StringUtil.isEmpty(invoker.getParentForeignKey())) {
            // 多对一
            mapperData.put("Association", "");
            mapperData.put("Collection", collection());
        } else if (!StringUtil.isEmpty(invoker.getForeignKey())) {
            // 一对多
            mapperData.put("Association", association());
            mapperData.put("Collection", "");
        } else {
            // 单表
            mapperData.put("Association", "");
            mapperData.put("Collection", "");
        }
        if (!ConfigUtil.getConfiguration().isGenerateEmptyMapper()){
            generateXmlType = FreemarkerConfigUtil.TYPE_MAPPER;
            mapperData.put("PrimaryKey", getPrimaryKeyColumnInfo(invoker.getTableInfos()).getColumnName());
            mapperData.put("WhereId", "#{" + getPrimaryKeyColumnInfo(invoker.getTableInfos()).getPropertyName() + "}");
            mapperData.put("PrimaryColumn", getPrimaryKeyColumnInfo(invoker.getTableInfos()));
            mapperData.put("InsertProperties", insertProperties());
            mapperData.put("ColumnMap", columnMap());
            mapperData.put("InsertBatchValues", insertBatchValues());
            mapperData.put("InsertValues", insertValues());
            mapperData.put("UpdateProperties", updateProperties());
            mapperData.put("Joins", joins());
        }

        String filePath;
        if (ConfigUtil.getConfiguration().isMapperUnderSource()) {
            // mapper-under-source = true，表示将Mapper映射文件放在源文件目录下
            filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
                    + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getMapper());
        } else {
            // 默认情况下，将Mapper映射文件放在resources下
            filePath = FileUtil.getResourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getMapper());
        }
        String fileName = ConfigUtil.getConfiguration().getName().getMapper().replace(Constant.PLACEHOLDER, invoker.getClassName()) + ".xml";
        // 生成Mapper文件
        FileUtil.generateToJava(generateXmlType, mapperData, filePath, fileName);
    }

    /**
     * 获取主键列
     *
     * @param list
     * @return
     */
    private ColumnInfo getPrimaryKeyColumnInfo(List<ColumnInfo> list) {
        for (ColumnInfo columnInfo : list) {
            if (columnInfo.isPrimaryKey()) {
                return columnInfo;
            }
        }
        return null;
    }

    /**
     * 生成columnMap
     *
     * @return ColumnMap代码段
     */
    public String columnMap() {
        StringBuilder sb = new StringBuilder();
        invoker.getTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            if (info.getColumnName().equals(invoker.getForeignKey())) {
                return;
            }
            sb.append(index == 0 ? "" : Constant.SPACE_8);
            sb.append(String.format("`%s`.`%s`,\n", invoker.getTableName(), info.getColumnName()));
        }));
        if (invoker.getParentTableInfos() != null) {
            invoker.getParentTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
                sb.append(Constant.SPACE_8);
                if (!StringUtil.isEmpty(invoker.getRelationalTableName()) || !StringUtil.isEmpty(invoker.getParentForeignKey())) {
                    sb.append(String.format("`%s`.`%s` AS `%ss.%s`,\n", invoker.getParentTableName(), info.getColumnName(),
                            StringUtil.firstToLowerCase(invoker.getParentClassName()), info.getColumnName()));
                } else {
                    sb.append(String.format("`%s`.`%s` AS `%s.%s`,\n", invoker.getParentTableName(), info.getColumnName(),
                            StringUtil.firstToLowerCase(invoker.getParentClassName()), info.getColumnName()));
                }
            }));
        }
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    /**
     * 生成resultMap
     *
     * @return ResultMap代码段
     */
    public String resultMap() {
        StringBuilder sb = new StringBuilder();
        invoker.getTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            if (info.getColumnName().equals(invoker.getForeignKey())) {
                return;
            }
            sb.append(index == 0 ? "" : Constant.SPACE_8);
            if (info.isPrimaryKey()) {
                sb.append(String.format("<id column=\"%s\" property=\"%s\" />\n", info.getColumnName(), info.getPropertyName()));
            } else {
                sb.append(String.format("<result column=\"%s\" property=\"%s\" />\n", info.getColumnName(), info.getPropertyName()));
            }
        }));
        return sb.toString();
    }

    /**
     * 生成association
     *
     * @return association代码段
     */
    public String association() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<association property=\"%s\" javaType=\"%s.%s\">\n", StringUtil.firstToLowerCase(invoker.getParentClassName()),
                ConfigUtil.getConfiguration().getPackageName() + "." + ConfigUtil.getConfiguration().getPath().getEntity(),
                invoker.getParentClassName()));
        invoker.getParentTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            if (info.isPrimaryKey()) {
                sb.append(Constant.SPACE_12).append(String.format("<id column=\"%s.%s\" property=\"%s\" />\n",
                        StringUtil.firstToLowerCase(invoker.getParentClassName()), info.getColumnName(), info.getPropertyName()));
            } else {
                sb.append(Constant.SPACE_12).append(String.format("<result column=\"%s.%s\" property=\"%s\" />\n",
                        StringUtil.firstToLowerCase(invoker.getParentClassName()), info.getColumnName(), info.getPropertyName()));
            }
        }));
        sb.append(Constant.SPACE_8).append("</association>");
        return sb.toString();
    }

    /**
     * 生成collection
     *
     * @return collection代码段
     */
    public String collection() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<collection property=\"%ss\" ofType=\"%s.%s\" >\n ", StringUtil.firstToLowerCase(invoker.getParentClassName()),
                ConfigUtil.getConfiguration().getPackageName() + "." + ConfigUtil.getConfiguration().getPath().getEntity(),
                invoker.getParentClassName()));
        invoker.getParentTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            if (info.isPrimaryKey()) {
                sb.append(Constant.SPACE_12).append(String.format("<id column=\"%ss.%s\" property=\"%s\" />\n",
                        StringUtil.firstToLowerCase(invoker.getParentClassName()), info.getColumnName(), info.getPropertyName()));
            } else {
                sb.append(Constant.SPACE_12).append(String.format("<result column=\"%ss.%s\" property=\"%s\" />\n",
                        StringUtil.firstToLowerCase(invoker.getParentClassName()), info.getColumnName(), info.getPropertyName()));
            }
        }));
        sb.append(Constant.SPACE_8).append("</collection>");
        return sb.toString();
    }

    /**
     * 生成insertProperties
     *
     * @return insertProperties代码段
     */
    public String insertProperties() {
        StringBuilder sb = new StringBuilder();
        invoker.getTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            sb.append(index == 0 ? "" : Constant.SPACE_12);
            sb.append(String.format("`%s`,\n", info.getColumnName()));
        }));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    /**
     * 生成insertValues
     *
     * @return insertValues代码段
     */
    public String insertValues() {
        StringBuilder sb = new StringBuilder();
        invoker.getTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            sb.append(index == 0 ? "" : Constant.SPACE_12);
            if (StringUtil.isEmpty(invoker.getRelationalTableName()) && !StringUtil.isEmpty(invoker.getForeignKey())) {
                if (info.getColumnName().equals(invoker.getForeignKey())) {
                    sb.append(String.format("#{%s.%s},\n", StringUtil.firstToLowerCase(invoker.getParentClassName()),
                            getPrimaryKeyColumnInfo(invoker.getParentTableInfos()).getPropertyName()));
                } else {
                    sb.append(String.format("#{%s},\n", info.getPropertyName()));
                }
            } else {
                sb.append(String.format("#{%s},\n", info.getPropertyName()));
            }
        }));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    /**
     * 生成insertBatchValues
     *
     * @return insertBatchValues代码段
     */
    public String insertBatchValues() {
        StringBuilder sb = new StringBuilder();
        invoker.getTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            sb.append(index == 0 ? "" : Constant.SPACE_12);
            if (StringUtil.isEmpty(invoker.getRelationalTableName()) && !StringUtil.isEmpty(invoker.getForeignKey())) {
                if (info.getColumnName().equals(invoker.getForeignKey())) {
                    sb.append(String.format("#{%s.%s.%s},\n", StringUtil.firstToLowerCase(invoker.getClassName()),
                            StringUtil.firstToLowerCase(invoker.getParentClassName()),
                            getPrimaryKeyColumnInfo(invoker.getParentTableInfos()).getPropertyName()));
                } else {
                    sb.append(String.format("#{%s.%s},\n", StringUtil.firstToLowerCase(invoker.getClassName()), info.getPropertyName()));
                }
            } else {
                sb.append(String.format("#{%s.%s},\n", StringUtil.firstToLowerCase(invoker.getClassName()), info.getPropertyName()));
            }
        }));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    /**
     * 生成updateProperties
     *
     * @return updateProperties代码段
     */
    public String updateProperties() {
        StringBuilder sb = new StringBuilder();
        invoker.getTableInfos().forEach(ForEachUtil.withIndex((info, index) -> {
            sb.append(index == 0 ? "" : Constant.SPACE_8);
            if (StringUtil.isEmpty(invoker.getRelationalTableName()) && !StringUtil.isEmpty(invoker.getForeignKey())) {
                if (info.getColumnName().equals(invoker.getForeignKey())) {
                    sb.append(String.format("`%s` = #{%s.%s},\n", info.getColumnName(),
                            StringUtil.firstToLowerCase(invoker.getParentClassName()),
                            getPrimaryKeyColumnInfo(invoker.getParentTableInfos()).getPropertyName()));
                } else {
                    sb.append(String.format("`%s` = #{%s},\n", info.getColumnName(), info.getPropertyName()));
                }
            } else {
                sb.append(String.format("`%s` = #{%s},\n", info.getColumnName(), info.getPropertyName()));
            }
        }));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }

    /**
     * 生成joins代码段
     *
     * @return joins代码段
     */
    public String joins() {
        StringBuilder sb = new StringBuilder();
        if (!StringUtil.isEmpty(invoker.getRelationalTableName())) {
            // 多对多
            sb.append(String.format("LEFT JOIN `%s` ON `%s`.`%s` = `%s`.`%s`", invoker.getRelationalTableName(),
                    invoker.getRelationalTableName(), invoker.getForeignKey(), invoker.getTableName(),
                    getPrimaryKeyColumnInfo(invoker.getTableInfos()).getColumnName()));
            sb.append("\n").append(Constant.SPACE_8);
            sb.append(String.format("LEFT JOIN `%s` ON `%s`.`%s` = `%s`.`%s`", invoker.getParentTableName(),
                    invoker.getParentTableName(), getPrimaryKeyColumnInfo(invoker.getParentTableInfos()).getColumnName(),
                    invoker.getRelationalTableName(), invoker.getParentForeignKey()));
        } else if (!StringUtil.isEmpty(invoker.getParentForeignKey())) {
            // 一对多
            sb.append(String.format("LEFT JOIN `%s` ON `%s`.`%s` = `%s`.`%s`", invoker.getParentTableName(),
                    invoker.getParentTableName(), invoker.getParentForeignKey(), invoker.getTableName(),
                    getPrimaryKeyColumnInfo(invoker.getTableInfos()).getColumnName()));
        } else if (!StringUtil.isEmpty(invoker.getForeignKey())) {
            // 多对一
            sb.append(String.format("LEFT JOIN `%s` ON `%s`.%s = `%s`.`%s`", invoker.getParentTableName(),
                    invoker.getParentTableName(), getPrimaryKeyColumnInfo(invoker.getParentTableInfos()).getColumnName(),
                    invoker.getTableName(), invoker.getForeignKey()));
        }
        return sb.toString();
    }

}
