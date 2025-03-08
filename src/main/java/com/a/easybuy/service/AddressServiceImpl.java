package com.a.easybuy.service;

import com.a.easybuy.dao.AddressMapper;
import com.a.easybuy.pojo.*;
import com.a.easybuy.util.ReadJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public ResponseMessage getAddressList() {
        logger.info("AddressServiceImpl  getAddressList start...");
        ResponseMessage rm = new ResponseMessage();
        List<Address> addressList = addressMapper.getAddressList(18);
        rm.setData(addressList);
        return rm;
    }

    @Override
    public ResponseMessage delAddress(Integer id) {
        logger.info("AddressServiceImpl  delAddress start...");
        ResponseMessage rm = new ResponseMessage();
        int delCount = addressMapper.delAddress(id,18);
        if(delCount>0){
            rm.setCode("200");
            rm.setMsg("删除成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("删除失败!");
        }
        return rm;
    }

    @Override
    public ResponseMessage changeAddress(String province,String city,String district,String remark,String area,String name,String email,String phone) {
        logger.info("AddressServiceImpl  changeAddress start...");
        ResponseMessage rm = new ResponseMessage();
        Address address = new Address();
        StringBuffer addressDetail = new StringBuffer();
        addressDetail.append(province);
        addressDetail.append(city);
        addressDetail.append(district);
        address.setRemark(remark);
        address.setArea(area);
        address.setEmail(email);
        address.setPhone(phone);
        address.setAddress(addressDetail.toString());
        int changeCount = addressMapper.changeAddress(address);
        if(changeCount>0){
            rm.setCode("200");
            rm.setMsg("修改成功!");
        }else{
            rm.setCode("201");
            rm.setMsg("修改失败!");
        }
        return rm;
    }

    @Override
    public ResponseMessage getProvinceList() {
        logger.info("AddressServiceImpl  getProvinceList start...");
        ResponseMessage rm = new ResponseMessage();
        List<Province> provinceList = ReadJson.getProvince();
        rm.setData(provinceList);
        return rm;
    }

    @Override
    public ResponseMessage getCityListByProvinceId(String provinceId) {
        logger.info("AddressServiceImpl  getCityListByProvinceId start...provinceId:"+provinceId);
        ResponseMessage rm = new ResponseMessage();
        List<City> cityList = ReadJson.getCityListByProvinceId(provinceId);
        rm.setData(cityList);
        return rm;
    }

    @Override
    public ResponseMessage getDistrictListByCityId(String cityId) {
        logger.info("AddressServiceImpl  getDistrictListByCityId start...");
        ResponseMessage rm = new ResponseMessage();
        List<District> districtList = ReadJson.getDistrictListByCityId(cityId);
        rm.setData(districtList);
        return rm;
    }

    @Override
    public ResponseMessage findAddressById(Integer id) {
        logger.info("AddressServiceImpl  getDistrictListByCityId start...id"+id);
        ResponseMessage rm = new ResponseMessage();
        Address address = addressMapper.findAddressById(id,18);
        rm.setData(address);
        return rm;
    }
}
