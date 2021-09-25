package com.generator.generatestater.application;

import com.generator.generatestater.invoker.Many2ManyInvoker;
import com.generator.generatestater.invoker.Many2OneInvoker;
import com.generator.generatestater.invoker.One2ManyInvoker;
import com.generator.generatestater.invoker.SingleInvoker;
import com.generator.generatestater.invoker.base.Invoker;

public class Main {

    public static void main(String[] args) {
        single();
    }

    public static void many2many() {
        Invoker invoker = new Many2ManyInvoker.Builder()
                .setTableName("user")
                .setClassName("User")
                .setParentTableName("role")
                .setParentClassName("Role")
                .setRelationTableName("user_role")
                .setForeignKey("user_id")
                .setParentForeignKey("role_id")
                .build();
        invoker.execute();
    }

    public static void many2one() {
        Invoker one2many = new Many2OneInvoker.Builder()
                .setTableName("user")
                .setClassName("User")
                .setParentTableName("office")
                .setParentClassName("Office")
                .setForeignKey("office_id")
                .build();
        one2many.execute();
    }

    public static void one2many() {
        Invoker invoker = new One2ManyInvoker.Builder()
                .setTableName("user")
                .setClassName("User")
                .setParentTableName("article")
                .setParentClassName("Article")
                .setParentForeignKey("user_id")
                .build();
        invoker.execute();
    }

    public static void single() {
        Invoker invoker = new SingleInvoker.Builder()
                .setTableName("user_ex")
                .setClassName("UserEx")
                .build();
        invoker.execute();
    }

}
