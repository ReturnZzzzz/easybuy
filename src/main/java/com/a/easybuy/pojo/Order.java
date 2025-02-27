package com.a.easybuy.pojo;


public class Order {

  private long id;
  private String code;
  private long status;
  private String total;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }


  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

}
