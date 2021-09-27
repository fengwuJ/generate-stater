package com.generator.generatestater.application;

import com.generator.generatestater.invoker.SingleInvoker;
import com.generator.generatestater.invoker.base.Invoker;

public class Main {

    public static void main(String[] args) {
        single();
    }

    public static void single() {
        Invoker invoker = new SingleInvoker.Builder()
                .setTableName("user_ex")
                .setClassName("UserEx")
                .build();
        invoker.execute();
    }

}
