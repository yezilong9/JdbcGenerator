package com.ubbcc.plugin;

/**
 * Created by zilongye on 2017/11/6.
 */

import com.ubbcc.plugin.enums.GeneratorStyle;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal JPA
 * @phase compile
 * @requiresProject false
 */
public class JpaGenerator extends JdbcGenerator{

  public JpaGenerator() {}

  public JpaGenerator(String url,String user,String pwd,String path,boolean isAll,String tables){
    this.dbUrl = url;
    this.dbUser = user;
    this.dbPwd = pwd;
    this.path = path;
    this.isAll = isAll;
    this.tables = tables;
  }

  @Override
  public String getStyle() {
    return GeneratorStyle.JPA.getStyle();
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    getLog().info("进行JPA代码自动生成！");
    try {
      generator();
      getLog().info("生成成功！");
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (TemplateException e) {
      e.printStackTrace();
    }
  }
}
