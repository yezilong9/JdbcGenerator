package com.ubbcc.plugin;

import com.ubbcc.plugin.enums.ColumnType;
import com.ubbcc.plugin.utils.NameUtil;
import com.ubbcc.plugin.utils.NameUtil.NameStyle;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.*;
import org.codehaus.plexus.util.StringUtils;

public abstract class JdbcGenerator extends AbstractMojo {

  public abstract String getStyle();

  /**
   * @parameter expression="${dbUrl}"
   * @required
   */
  protected String dbUrl;
  /**
   * @parameter expression="${dbUser}"
   * @required
   */
  protected String dbUser;
  /**
   * @parameter expression="${dbPwd}"
   * @required
   */
  protected String dbPwd;
  /**
   * @parameter expression="${isAll}" defaultValue="false"
   */
  protected boolean isAll;

  /**
   * @parameter expression="${tables}"
   */
  protected String tables;
  /**
   * @parameter expression="${path}"
   */
  protected String path;

  public String getDbUrl() {
    return dbUrl;
  }

  public String getDbUser() {
    return dbUser;
  }

  public String getDbPwd() {
    return dbPwd;
  }

  public boolean isAll() {
    return isAll;
  }

  public List<String> getTables() {
    List<String> tableList = new ArrayList<String>();
    if (StringUtils.isNotBlank(tables)) {
      tableList.addAll(Arrays.asList(tables.split(",")));
    }
    return tableList;
  }

  @Override
  public abstract void execute() throws MojoExecutionException, MojoFailureException;


  public void generator()
      throws SQLException, IOException, ClassNotFoundException, TemplateException {
    Connection connection = getConnection();
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet tableRet = metaData.getTables(null, null, null, new String[]{"TABLE"});
    List<String> tableList = getTables();
    boolean hasBase = new File(System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", File.separator)
            + File.separator + "base").exists();
    while (tableRet.next()) {
      String tableName = tableRet.getString("TABLE_NAME");
      if (!isAll && !tableList.contains(tableName)) {
        continue;
      }
      HashMap<String, Object> hashMap = new HashMap<String, Object>();

      ResultSet colRet = metaData.getColumns(null, null, tableName, null);
      List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
      while (colRet.next()) {
        String columnName = colRet.getString("COLUMN_NAME");
        String annotation = "@Column";
        if (columnName.equals("id") || columnName.equals("ctime") || columnName.equals("utime")) {
          continue;
        }
        String columnType = colRet.getString("TYPE_NAME");

        HashMap<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("annotation", annotation);
        columnMap.put("columnType", ColumnType.getType(columnType));
        columnMap.put("columnName", NameUtil.conver(columnName, NameStyle.COLUMN));
        columnMap.put("methodName", NameUtil.conver(columnName, NameStyle.ENTITY));
        list.add(columnMap);
      }
      hashMap.put("parameters", list);
      hashMap.put("tableName", tableName);
      String entityName = NameUtil.conver(tableName, NameStyle.ENTITY);
      hashMap.put("entityName", entityName);
      hashMap.put("package", path);

      if (!hasBase) {
        String[] fileNames = {"BaseEntity","BaseRepository","BaseService","BaseServiceImpl"};
        for(String fileName : fileNames){
          mkdirBase(hashMap,fileName);
        }
        hasBase = true;
      }

      mkdirEntity(hashMap, entityName);
      mkdirService(hashMap, entityName);
      mkdirServiceImpl(hashMap, entityName);
      mkdirDAO(hashMap, entityName);
    }
  }


  private void mkdirBase(HashMap<String, Object> hashMap, String fileName)
      throws IOException, TemplateException {
    String content = IOUtils.toString(JdbcGenerator.class.getResourceAsStream("/JPA/" + fileName + ".ftl"),"UTF-8");
    String javaFile = generateTemplate(content, hashMap);
    String codePath =
        System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", File.separator)
            + File.separator + "base";
    File file = new File(codePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtils.writeStringToFile(new File(file.getPath() + File.separator + fileName + ".java"),
        javaFile);
  }

  private void mkdirEntity(HashMap<String, Object> hashMap, String entityName)
      throws IOException, TemplateException {
    String content = IOUtils.toString(JdbcGenerator.class.getResourceAsStream("/JPA/entity.ftl"),"UTF-8");
    String javaFile = generateTemplate(content, hashMap);
    String codePath =
        System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", File.separator)
            + File.separator + "entity";
    File file = new File(codePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtils.writeStringToFile(new File(file.getPath() + File.separator + entityName + ".java"),
        javaFile);
  }

  private void mkdirService(HashMap<String, Object> hashMap, String entityName)
      throws IOException, TemplateException {
    String content = IOUtils.toString(JdbcGenerator.class.getResourceAsStream("/JPA/service.ftl"),"UTF-8");
    String javaFile = generateTemplate(content, hashMap);
    String codePath =
        System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", File.separator)
            + File.separator + "service";
    File file = new File(codePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtils.writeStringToFile(
        new File(file.getPath() + File.separator + entityName + "Service" + ".java"),
        javaFile);
  }


  private void mkdirDAO(HashMap<String, Object> hashMap, String entityName)
      throws IOException, TemplateException {
    String content = IOUtils.toString(JdbcGenerator.class.getResourceAsStream("/JPA/repository.ftl"),"UTF-8");
    String javaFile = generateTemplate(content, hashMap);
    String codePath =
        System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", File.separator)
            + File.separator + "repository";
    File file = new File(codePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtils.writeStringToFile(
        new File(file.getPath() + File.separator + entityName + "Repository" + ".java"),
        javaFile);
  }

  private void mkdirServiceImpl(HashMap<String, Object> hashMap, String entityName)
      throws IOException, TemplateException {
    String content = IOUtils.toString(JdbcGenerator.class.getResourceAsStream("/JPA/serviceImpl.ftl"),"UTF-8");
    String javaFile = generateTemplate(content, hashMap);
    String codePath =
        System.getProperty("user.dir") + "/src/main/java/" + path.replace(".", File.separator)
            + File.separator + "service/impl";
    File file = new File(codePath);
    if (!file.exists()) {
      file.mkdirs();
    }
    FileUtils.writeStringToFile(
        new File(file.getPath() + File.separator + entityName + "ServiceImpl" + ".java"),
        javaFile);
  }


  private Connection getConnection()
      throws IOException, SQLException, ClassNotFoundException {
    Connection con;
    String name = "com.mysql.jdbc.Driver";
    Class.forName(name);//指定连接类型
    con = DriverManager
        .getConnection(dbUrl, dbUser, dbPwd);//获取连接
    return con;
  }


  private String generateTemplate(String content,
      HashMap<String, Object> map) throws IOException, TemplateException {
    Configuration cfg = new Configuration();
    StringTemplateLoader sTmpLoader = new StringTemplateLoader();
    String id = UUID.randomUUID().toString();
    sTmpLoader.putTemplate(id, content);
    cfg.setTemplateLoader(sTmpLoader);
    cfg.setDefaultEncoding("UTF-8");
    Template template = cfg.getTemplate(id);
    StringWriter writer = new StringWriter();
    template.process(map, writer);
    return writer.toString();
  }
}
