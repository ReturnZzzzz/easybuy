package com.a.easybuy.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.a.easybuy.pojo.City;
import com.a.easybuy.pojo.District;
import com.a.easybuy.pojo.Province;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReadJson {
    private static Map<String,List<City>> cityMap=null;
    private static Map<String,List<District>> districtMap=null;
    public static List<Province> getProvince() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(
                    new File("src/main/resources/chinaRegions/province.json"),
                    mapper.getTypeFactory().constructCollectionType(List.class,Province.class)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadCity() throws IOException {
        File file = new File("src/main/resources/chinaRegions/city.json");
        if(cityMap == null){
            ObjectMapper  objectMapper = new ObjectMapper();
            cityMap = objectMapper.readValue(file,new TypeReference<Map<String,List<City>>>(){});
        }
    }

    public static List<City> getCityListByProvinceId(String id){
        try {
            loadCity();
            return cityMap.getOrDefault(id, Collections.emptyList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadDistrict() throws IOException {
        File file = new File("src/main/resources/chinaRegions/district.json");
        if(districtMap == null){
            ObjectMapper  objectMapper = new ObjectMapper();
            districtMap = objectMapper.readValue(file,new TypeReference<Map<String,List<District>>>(){});
        }
    }

    public static List<District> getDistrictListByCityId(String id){
        try {
            loadDistrict();
            return districtMap.getOrDefault(id, Collections.emptyList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        String hebeiId = "120100000000";
        List<District> cities = getDistrictListByCityId(hebeiId);

        // 打印结果
        System.out.println("天津区县数量：" + cities.size());
        cities.forEach(city ->
                System.out.println("区县名称：" + city.getName() + "，行政代码：" + city.getId())
        );
    }
}
