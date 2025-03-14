package com.a.easybuy.controller;

import com.a.easybuy.pojo.ResponseMessage;
import com.a.easybuy.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("address")
public class AddressController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AddressService addressService;

    @RequestMapping("getAddressList")
    @ResponseBody
    public ResponseMessage getAddressList(){
        logger.info("AddressController getAddressList start...");
        return  addressService.getAddressList();
    }

    @RequestMapping("delAddress")
    @ResponseBody
    public ResponseMessage delAddress(String id){
        logger.info("AddressController delAddress start... id:"+id);
        return  addressService.delAddress(Integer.parseInt(id));
    }

    @RequestMapping("getProvinceList")
    @ResponseBody
    public ResponseMessage getProvinceList(){
        logger.info("AddressController getProvinceList start...");
        return addressService.getProvinceList();
    }

    @RequestMapping("getCityList")
    @ResponseBody
    public ResponseMessage getCityList(String provinceId){
        logger.info("AddressController getCityList start...provinceId："+provinceId);
        return  addressService.getCityListByProvinceId(provinceId);
    }

    @RequestMapping("getDistrictList")
    @ResponseBody
    public ResponseMessage getDistrictList(String cityId){
        logger.info("AddressController getCityList start...cityId："+cityId);
        return addressService.getDistrictListByCityId(cityId);
    }

    @RequestMapping("changeAddress")
    @ResponseBody
    public ResponseMessage changeAddress(String province,String city,String district,String remark,String area,String name,String email,String phone){
        logger.info("AddressController changeAddress start...province："+province+"city"+city+"district"+district+
                "remark"+remark+"area"+area+"name"+name+"email"+email+"phone"+phone);
        return addressService.changeAddress(province,city,district,remark,area,name,email,phone);
    }

    @RequestMapping("findAddressById")
    @ResponseBody
    public  ResponseMessage findAddressById(String id){
        logger.info("AddressController getCityList start...id："+id);
        return addressService.findAddressById(Integer.parseInt(id));
    }
}
