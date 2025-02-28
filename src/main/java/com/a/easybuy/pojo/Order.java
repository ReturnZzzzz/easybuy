package com.a.easybuy.pojo;


public class Order {

  private long id;
  private long uid;
  private String loginName;
  private String adress;
  private java.sql.Timestamp createDate;
  private double total;
  private String orderCode;


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


  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }


  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }


  public java.sql.Timestamp getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.sql.Timestamp createDate) {
    this.createDate = createDate;
  }


  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }


  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

}
