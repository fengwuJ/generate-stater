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

public class InterfaceTask extends AbstractTask {

    public InterfaceTask(AbstractInvoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 构造Service接口填充数据
        Map<String, Object> interfaceData = new HashMap<>();
        interfaceData.put("Configuration", ConfigUtil.getConfiguration());
        interfaceData.put("ClassName", ConfigUtil.getConfiguration().getName().getEntity().replace(Constant.PLACEHOLDER,
                invoker.getClassName()));
        interfaceData.put("EntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName())));
        interfaceData.put("InterfaceClassName", ConfigUtil.getConfiguration().getName().getInterf()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()));
        interfaceData.put("ReqClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Req"));
        interfaceData.put("ResClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Res"));
        interfaceData.put("ReqEntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()))+"Req");
        interfaceData.put("ResEntityName", StringUtil.firstToLowerCase(ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Res")));
        interfaceData.put("SearchClassName", ConfigUtil.getConfiguration().getName().getEntity()
                .replace(Constant.PLACEHOLDER, invoker.getClassName()+"Search"));
        interfaceData.put("pkType", getPrimaryKeyType(invoker.getTableInfos()));

        String filePath = FileUtil.getSourcePath() + StringUtil.package2Path(ConfigUtil.getConfiguration().getPackageName())
                + StringUtil.package2Path(ConfigUtil.getConfiguration().getPath().getInterf());
        String fileName = ConfigUtil.getConfiguration().getName().getInterf().replace(Constant.PLACEHOLDER, invoker.getClassName()) + ".java";
        // 生成Service接口文件
        FileUtil.generateToJava(FreemarkerConfigUtil.TYPE_INTERFACE, interfaceData, filePath, fileName);
    }

    /**
     * 获取主键列对应的属性类型
     *
     * @param columnInfos
     * @return
     */
    private String getPrimaryKeyType(List<ColumnInfo> columnInfos) {
        if (!ConfigUtil.getConfiguration().isJpaEnable()) {
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
