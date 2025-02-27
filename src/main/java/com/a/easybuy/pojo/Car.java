package com.a.easybuy.pojo;



import java.util.List;

public class Car {

  private long id;
  private long uid;
  private List<Good> list;

  public List<Good> getList() {
    return list;
  }

  public void setList(List<Good> list) {
    this.list = list;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

}
