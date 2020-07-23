package com.moonyue.sleeve.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonyue.sleeve.core.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Map;

@Converter
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new HttpException();
        }
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        if(s==null){
            return null;
        }
        try {
            return mapper.readValue(s, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new HttpException();
        }
    }
}
