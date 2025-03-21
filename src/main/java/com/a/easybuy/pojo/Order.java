package com.a.easybuy.pojo;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {

  private long id;
  private long uid;
  private String loginName;
  private String adress;
  private Date createDate;
  private BigDecimal total;
  private String orderCode;
  private int status;
  private List<OrderDetail> list;

  public List<OrderDetail> getList() {
    return list;
  }

  public void setList(List<OrderDetail> list) {
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


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", uid=" + uid +
            ", loginName='" + loginName + '\'' +
            ", adress='" + adress + '\'' +
            ", createDate=" + createDate +
            ", total=" + total +
            ", orderCode='" + orderCode + '\'' +
            ", status=" + status +
            '}';
  }
}
