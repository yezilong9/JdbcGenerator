package com.ubbcc.plugin.enums;

/**
 * Created by zilongye on 2017/11/6.
 */
public enum GeneratorStyle {

  JPA("JPA"),MyBatis("MyBatis");

  private String style;

  GeneratorStyle(String style){
    this.style = style;
  }

  public String getStyle() {
    return style;
  }
}
