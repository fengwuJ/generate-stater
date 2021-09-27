package com.generator.generatestater.task;

import com.generator.generatestater.entity.ColumnInfo;
import com.generator.generatestater.entity.Constant;
import com.generator.generatestater.invoker.base.AbstractInvoker;
import com.generator.generatestater.task.base.AbstractTask;
import com.generator.generatestater.utils.*;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceTask extends AbstractTask {

    public ServiceTask(AbstractInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 构造Service填充数据
        Map<String, Object> serviceData = new HashMap<>();
        serviceData.put("Configuration", ConfigUtil.getConfiguration());
        serviceData.put("ClassName", ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER, invoker.getClassName()));
        serviceData.put("EntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName())));
        serviceData.put("DaoClassName", ConfigUtil.getConfiguration().getName().getDao().replace(Constant.PLACEHOLDER, invoker.getClassName()));
        serviceData.put("DaoEntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getDao()
                .replace(Constant.PLACEHOLDER, invoker.getClassName())));
        serviceData.put("ReqEntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()))+"Req");
        serviceData.put("ResEntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Res")));
        serviceData.put("SearchClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Search"));
        serviceData.put("pkType", getPrimaryKeyType(invoker.getTableInfos()));
        serviceData.put("ReqClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Req"));
        serviceData.put("ResClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Res"));
        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
                + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getService());
        String fileName;
        /*
         * 根据用户是否配置了path节点下的interf属性来判断是否采用接口+实现类的方式
         */
        String serviceClassName = ConfigUtil.getConfiguration().getName().getService().replace(Constant.PLACEHOLDER, invoker.getClassName());
        if (StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getInterf())) {
            serviceData.put("ServiceClassName", serviceClassName);
            serviceData.put("Implements", "");
            serviceData.put("InterfaceImport", "");
            serviceData.put("Override", "");
            fileName = ConfigUtil.getConfiguration().getName().getService().replace(Constant.PLACEHOLDER, invoker.getClassName()) + ".java";
        } else {
            // Service接口实现类默认由Impl结尾
            serviceClassName = serviceClassName.contains("Impl") ? serviceClassName : serviceClassName + "Impl";
            serviceData.put("ServiceClassName", serviceClassName);
            serviceData.put("Implements", "implements " + ConfigUtil.getConfiguration().getName().getInterf()
                    .replace(Constant.PLACEHOLDER, invoker.getClassName()));
            serviceData.put("InterfaceImport", "import " + ConfigUtil.getConfiguration().getPackageName() + "."
                    + ConfigUtil.getConfiguration().getPath().getInterf() + "."
                    + ConfigUtil.getConfiguration().getName().getInterf().replace(Constant.PLACEHOLDER, invoker.getClassName()) + ";");
            serviceData.put("Override", "\n    @Override");
            fileName = serviceClassName + ".java";
        }
        // 生成Service文件
        FileUtil.generateToJava(FreemarkerConfigUtil.TYPE_SERVICE, serviceData, filePath, fileName);
    }

    /**
     * 获取主键列对应的属性类型
     *
     * @param columnInfos
     * @return
     */
    private String getPrimaryKeyType(List<ColumnInfo> columnInfos) {
        if (!ConfigUtil.getConfiguration().isTkMapperEnable()) {
            return "Serializable";
        }
        for (ColumnInfo info : columnInfos) {
            if (info.isPrimaryKey()) {
                return info.getPropertyType();
            }
        }
        return "Serializable";
    }
}
