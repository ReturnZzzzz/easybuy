package com.a.easybuy.service;

import com.a.easybuy.dao.BillMapper;
import com.a.easybuy.pojo.Bill;
import com.a.easybuy.pojo.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BillMapper billMapper;

    @Override
    public ResponseMessage addBill(Bill bill) {
        logger.info("addBill bill"+bill);
        ResponseMessage responseMessage = new ResponseMessage();
        int count = billMapper.add(bill);
        if (count > 0) {
            responseMessage.setCode("200");
        }else {
            responseMessage.setCode("201");
        }
        return responseMessage;
    }
}
