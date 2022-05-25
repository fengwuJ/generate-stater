package com.generator.generatestater.application;

import com.generator.generatestater.invoker.SingleInvoker;
import com.generator.generatestater.invoker.base.Invoker;

import java.util.Calendar;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) {
        //single();

        Long sys = System.currentTimeMillis();
        Calendar otherDate = Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println();

    }

    public static void single() {
        Invoker invoker = new SingleInvoker.Builder()
                .setTableName("user_ex")
                .setClassName("UserEx")
                .build();
        invoker.execute();
    }

}
