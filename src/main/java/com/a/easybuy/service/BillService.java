package com.a.easybuy.service;

import com.a.easybuy.pojo.Bill;
import com.a.easybuy.pojo.ResponseMessage;

public interface BillService {
    public ResponseMessage addBill(Bill bill);
}
