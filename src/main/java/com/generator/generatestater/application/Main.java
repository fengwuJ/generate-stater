package com.generator.generatestater.application;

import com.generator.generatestater.invoker.SingleInvoker;
import com.generator.generatestater.invoker.base.Invoker;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String,String> tableEntityMap = initMapping();
        for (String key : tableEntityMap.keySet()) {
            single(key,tableEntityMap.get(key));
        }
    }

    private static Map<String, String> initMapping() {
        Map<String,String> tableEntityMap = new HashMap<>();
        tableEntityMap.put("table_position","TablePosition");
        tableEntityMap.put("table_user_info","TableUserInfo");
        tableEntityMap.put("table_verification_code_info","TableVerificationCodeInfo");
        tableEntityMap.put("table_year_limit","TableYearLimit");
        return tableEntityMap;
    }

    public static void single(String tableName,String entityName) {
        Invoker invoker = new SingleInvoker.Builder()
                .setTableName(tableName)
                .setClassName(entityName)
                .build();
        invoker.execute();
    }

}
