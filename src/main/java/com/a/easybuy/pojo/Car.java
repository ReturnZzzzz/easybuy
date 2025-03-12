package com.a.easybuy.pojo;


import java.util.List;

public class Car {

  private long id;
  private long uid;
  private List<CarDetail> list;

  public List<CarDetail> getList() {
    return list;
  }

  public void setList(List<CarDetail> list) {
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
