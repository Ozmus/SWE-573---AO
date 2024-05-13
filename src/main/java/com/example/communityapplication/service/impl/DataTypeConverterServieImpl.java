package com.example.communityapplication.service.impl;

import com.example.communityapplication.model.datatype.Geolocation;
import com.example.communityapplication.service.DataTypeConverterService;

public class DataTypeConverterServieImpl implements DataTypeConverterService {
    @Override
    public Geolocation convertToGeolocation(String geolocation) {
        String[] split = geolocation.split(",");
        return new Geolocation(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
    }

    @Override
    public String convertToString(Geolocation geolocation) {
        StringBuilder result = new StringBuilder();
        result.append(geolocation.getLatitude());
        result.append(",");
        result.append(geolocation.getLongitude());
        return result.toString();
    }
}
