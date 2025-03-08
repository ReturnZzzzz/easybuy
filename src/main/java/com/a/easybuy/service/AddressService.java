package com.a.easybuy.service;

import com.a.easybuy.pojo.ResponseMessage;

public interface AddressService {
    ResponseMessage getAddressList();
    ResponseMessage delAddress(Integer id);
    ResponseMessage changeAddress(String province,String city,String district,String remark,String area,String name,String email,String phone);
    ResponseMessage getProvinceList();
    ResponseMessage getCityListByProvinceId(String provinceId);
    ResponseMessage getDistrictListByCityId(String cityId);
    ResponseMessage findAddressById(Integer id);

}
