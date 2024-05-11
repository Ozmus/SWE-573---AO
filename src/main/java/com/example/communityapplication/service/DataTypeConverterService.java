package com.example.communityapplication.service;

import com.example.communityapplication.model.datatype.Geolocation;

public interface DataTypeConverterService {
    Geolocation convertToGeolocation(String geolocation);
    String convertToString(Geolocation geolocation);
}