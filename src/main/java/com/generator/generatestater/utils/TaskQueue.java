package com.generator.generatestater.utils;

import com.generator.generatestater.entity.Mode;
import com.generator.generatestater.invoker.base.AbstractInvoker;
import com.generator.generatestater.task.*;
import com.generator.generatestater.task.base.AbstractTask;

import java.util.LinkedList;

public class TaskQueue {

    /**
     * 任务队列
     */
    private LinkedList<AbstractTask> taskQueue = new LinkedList<>();

    /**
     * 初始化共性任务，包括Controller、ServiceImpl、Service、Dao、Mapper任务
     *
     * @param invoker 执行器
     */
    private void initCommonTasks(AbstractInvoker invoker) {
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getController())) {
            taskQueue.add(new ControllerTask(invoker));
        }
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getService())) {
            taskQueue.add(new ServiceTask(invoker));
        }
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getInterf())) {
            taskQueue.add(new InterfaceTask(invoker));
        }
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getDao())) {
            taskQueue.add(new DaoTask(invoker));
        }
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getMapper())) {
            taskQueue.add(new MapperTask(invoker));
        }
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getReq())
                || !StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getRes())
                || !StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getSearch())) {
            taskQueue.add(new DtoTask(invoker));
        }
    }

    /**
     * 初始化单表生成任务，包括Entity、Mapper任务
     *
     * @param invoker 执行器
     */
    public void initSingleTasks(AbstractInvoker invoker) {
        initCommonTasks(invoker);
        if (!StringUtil.isEmpty(ConfigUtil.getConfiguration().getPath().getEntity())) {
            taskQueue.add(new EntityTask(Mode.ENTITY_MAIN, invoker));
        }
    }

    /**
     * 任务队列是否为空
     *
     * @return 是否为空
     */
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    /**
     * 取出一个任务
     *
     * @return 任务
     */
    public AbstractTask poll() {
        return taskQueue.poll();
    }

}
