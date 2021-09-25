package com.generator.generatestater.task.base;

import com.generator.generatestater.invoker.base.AbstractInvoker;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Serializable;

public abstract class AbstractTask implements Serializable {
    protected AbstractInvoker invoker;

    public AbstractTask() {
    }

    /**
     * 执行任务
     *
     * @throws IOException 文件读写异常
     * @throws TemplateException 模板异常
     */
    public abstract void run() throws IOException, TemplateException;

}
