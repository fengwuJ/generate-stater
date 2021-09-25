package com.generator.generatestater.invoker;

import com.generator.generatestater.invoker.base.AbstractBuilder;
import com.generator.generatestater.invoker.base.AbstractInvoker;
import com.generator.generatestater.utils.StringUtil;

public class SingleInvoker extends AbstractInvoker {

    private SingleInvoker() {

    }

    @Override
    protected void queryMetaData() throws Exception {
        tableInfos = connectionUtil.getMetaData(tableName);
    }

    @Override
    protected void initTasks() {
        taskQueue.initSingleTasks(this);
    }

    public static class Builder extends AbstractBuilder {

        public Builder() {
            invoker = new SingleInvoker();
        }

        public Builder setTableName(String tableName) {
            invoker.setTableName(tableName);
            return this;
        }

        public Builder setClassName(String className) {
            invoker.setClassName(className);
            return this;
        }

        @Override
        public void checkBeforeBuild() throws Exception {
            if (StringUtil.isEmpty(invoker.getTableName())) {
                throw new Exception("Table name can't be null.");
            }
            if (StringUtil.isEmpty(invoker.getClassName())) {
                invoker.setClassName(StringUtil.tableName2ClassName(invoker.getTableName()));
            }
        }
    }

}
