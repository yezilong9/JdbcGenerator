package com.ubbcc.plugin.utils;

/**
 * Created by zilongye on 2017/12/8.
 */
public class NameUtil {

  public enum NameStyle{
    COLUMN,ENTITY
  }

  public static String conver(String src,NameStyle nameStyle) {
    if (src == null) {
      return null;
    }

    StringBuilder sb = new StringBuilder();
    boolean is = false;
    for (int i = 0; i < src.length(); i++) {
      char c = src.charAt(i);
      if (i == 0) {
        switch (nameStyle){
          case COLUMN:
            c = Character.toLowerCase(c);
            break;
          case ENTITY:
            c = Character.toUpperCase(c);
            break;
        }
      }

      if (c == '_') {
        is = true;
      } else {
        if (is) {
          sb.append(Character.toUpperCase(c));
          is = false;
        } else {
          sb.append(c);
        }
      }
    }
    String ret = sb.toString();
    return ret.startsWith("_") ? ret.substring(1) : ret;
  }

}
