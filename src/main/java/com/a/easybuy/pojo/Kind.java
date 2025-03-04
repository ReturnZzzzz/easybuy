package com.a.easybuy.pojo;


import java.util.List;

public class Kind {

  private Integer id;
  private String name;
  private long pid;
  private long type;
  private String iconClass;
  private List<Kind> children;
  private List<Good> products;

  public List<Kind> getChildren() {
    return children;
  }

  public void setChildren(List<Kind> children) {
    this.children = children;
  }

  public List<Good> getProducts() {
    return products;
  }

  public void setProducts(List<Good> products) {
    this.products = products;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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
