package com.a.easybuy.pojo;


import java.math.BigDecimal;
import java.util.Date;

public class Good {

  private long id;
  private String gname;
  private String description;
  private BigDecimal price;
  private long stock;
  private String imgPath;
  private long gstatus;
  private Integer kid;
  private Date createdate;

  public Date getCreatedate() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getKid() {
    return kid;
  }

  public void setKid(Integer kid) {
    this.kid = kid;
  }

  @Override
  public String toString() {
    return "Good{" +
            "id=" + id +
            ", gname='" + gname + '\'' +
            ", description='" + description + '\'' +
            ", price=" + price +
            ", stock=" + stock +
            ", imgPath='" + imgPath + '\'' +
            ", gstatus=" + gstatus +
            ", kid=" + kid +
            '}';
  }
}
