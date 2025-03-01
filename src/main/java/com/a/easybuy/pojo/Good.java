package com.a.easybuy.pojo;


public class Good {

  private long id;
  private String gname;
  private String description;
  private double price;
  private long stock;
  private String imgPath;
  private long gstatus;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getGname() {
    return gname;
  }

  public void setGname(String gname) {
    this.gname = gname;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public long getStock() {
    return stock;
  }

  public void setStock(long stock) {
    this.stock = stock;
  }


  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }


  public long getGstatus() {
    return gstatus;
  }

  public void setGstatus(long gstatus) {
    this.gstatus = gstatus;
  }

}
