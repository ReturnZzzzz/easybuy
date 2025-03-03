package com.a.easybuy.pojo;


import java.math.BigDecimal;
import java.util.Date;

public class Bill {

  private long id;
  private String phone;
  private BigDecimal price;
  private Date createtime;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public Date getCreatetime() {
    return createtime;
  }

  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }

}
