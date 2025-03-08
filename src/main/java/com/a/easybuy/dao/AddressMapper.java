package com.a.easybuy.dao;

import com.a.easybuy.pojo.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    List<Address> getAddressList(Integer uid);
    int delAddress(@Param("id") Integer id, @Param("uid") Integer uid);
    int changeAddress(Address address);
    int addAddress(Address address);
    Address findAddressById(@Param("id") Integer id,@Param("uid") Integer uid);
}
