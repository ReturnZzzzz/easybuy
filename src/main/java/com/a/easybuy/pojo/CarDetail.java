package com.a.easybuy.pojo;

public class CarDetail {
    private int id;
    private Good good;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @Override
    public String toString() {
        return "CarDetail{" +
                "id=" + id +
                ", good=" + good +
                ", count=" + count +
                '}';
    }
}
