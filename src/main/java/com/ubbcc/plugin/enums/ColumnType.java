package com.ubbcc.plugin.enums;

import java.util.HashMap;

/**
 * Created by zilongye on 16/1/31.
 */
public class ColumnType {

    private static HashMap<String,String> type = new HashMap<String, String>();

    static{
        type.put("INT","Integer");
        type.put("BIGINT","Long");
        type.put("INTEGER","Integer");
        type.put("FLOAT","FLOAT");
        type.put("CHAR","String");
        type.put("VARCHAR","String");
        type.put("TEXT","String");
        type.put("DATETIME","Date");
        type.put("DATE","Date");
        type.put("TIMESTAMP","Date");
    }

    public static String getType(String type){
        return ColumnType.type.get(type);
    }
}
