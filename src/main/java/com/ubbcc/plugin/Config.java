package com.ubbcc.plugin;

import java.util.Properties;

/**
 * Created by zilongye on 16/1/18.
 */
public class Config {

    //数据库
    private String jdbcUrl;
    private String username;
    private String password;
    private boolean isAllTable;
    private String tableNames;
    private String modulePath;
    private String baseModulePath;

    public Config(Properties properties) {
        this.jdbcUrl = properties.getProperty("jdbc.url");
        this.username = properties.getProperty("jdbc.username");
        this.password = properties.getProperty("jdbc.password");
        this.isAllTable = Boolean.parseBoolean(properties.getProperty("isalltable"));
        this.tableNames = properties.getProperty("tablenames");
        this.modulePath = properties.getProperty("module.path");
        this.baseModulePath = properties.getProperty("base.module.path");
    }

    public String getBaseModulePath() {
        return baseModulePath;
    }

    public void setBaseModulePath(String baseModulePath) {
        this.baseModulePath = baseModulePath;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllTable() {
        return isAllTable;
    }

    public void setAllTable(boolean isAllTable) {
        this.isAllTable = isAllTable;
    }

    public String getTableNames() {
        return tableNames;
    }

    public void setTableNames(String tableNames) {
        this.tableNames = tableNames;
    }

    public String getModulePath() {
        return modulePath;
    }

    public void setModulePath(String modulePath) {
        this.modulePath = modulePath;
    }
}
