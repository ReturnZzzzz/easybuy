package com.a.easybuy.pojo;


public class Good {

  private long id;
  private long kid;
  private String gname;
  private double price;
  private String stock;
  private String imgPath;
  private String gstatus;
  private java.sql.Timestamp createTime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getKid() {
    return kid;
  }

  public void setKid(long kid) {
    this.kid = kid;
  }


  public String getGname() {
    return gname;
  }

  public void setGname(String gname) {
    this.gname = gname;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public String getStock() {
    return stock;
  }

  public void setStock(String stock) {
    this.stock = stock;
  }


  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }


  public String getGstatus() {
    return gstatus;
  }

  public void setGstatus(String gstatus) {
    this.gstatus = gstatus;
  }


  public java.sql.Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(java.sql.Timestamp createTime) {
    this.createTime = createTime;
  }

}
