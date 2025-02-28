package com.a.easybuy.pojo;


public class Kind {

  private long id;
  private String name;
  private long pid;
  private long type;
  private String iconClass;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getPid() {
    return pid;
  }

  public void setPid(long pid) {
    this.pid = pid;
  }


  public long getType() {
    return type;
  }

  public void setType(long type) {
    this.type = type;
  }


  public String getIconClass() {
    return iconClass;
  }

  public void setIconClass(String iconClass) {
    this.iconClass = iconClass;
  }

}
