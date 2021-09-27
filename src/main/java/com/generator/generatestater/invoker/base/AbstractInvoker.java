package com.generator.generatestater.invoker.base;

import com.generator.generatestater.db.ConnectionUtil;
import com.generator.generatestater.entity.ColumnInfo;
import com.generator.generatestater.task.base.AbstractTask;
import com.generator.generatestater.utils.TaskQueue;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractInvoker implements Invoker {
    /**
     * 主表名
     */
    protected String tableName;
    /**
     * 主类名
     */
    protected String className;
    /**
     * 主表元数据
     */
    protected List<ColumnInfo> tableInfos;
    /**
     * 数据库连接工具
     */
    protected ConnectionUtil connectionUtil = new ConnectionUtil();
    /**
     * 任务队列
     */
    protected TaskQueue taskQueue = new TaskQueue();
    /**
     * 线程池
     */
    private ExecutorService executorPool = Executors.newFixedThreadPool(6);

    /**
     * 获取表元数据，模板方法，由子类实现
     *
     * @throws Exception 获取元数据失败则抛出异常
     */
    protected abstract void queryMetaData() throws Exception;

    /**
     * 初始化代码生成任务，模板方法，由子类实现
     */
    protected abstract void initTasks();

    /**
     * 开始生成代码
     */
    @Override
    public void execute() {
        try {
            queryMetaData();
            initTasks();
            while (!taskQueue.isEmpty()) {
                AbstractTask task = taskQueue.poll();
                executorPool.execute(() -> {
                    try {
                        task.run();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (TemplateException e) {
                        e.printStackTrace();
                    }
                });
            }
            executorPool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClassName() {
        return className;
    }

    public List<ColumnInfo> getTableInfos() {
        return tableInfos;
    }

    public void setTableInfos(List<ColumnInfo> tableInfos) {
        this.tableInfos = tableInfos;
    }
}
