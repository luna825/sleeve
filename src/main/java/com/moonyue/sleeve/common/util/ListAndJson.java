package com.moonyue.sleeve.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonyue.sleeve.core.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class ListAndJson implements AttributeConverter<List<Object>, String> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Object> objects) {
        try {
            return objectMapper.writeValueAsString(objects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new HttpException();
        }
    }

    @Override
    public List<Object> convertToEntityAttribute(String s) {
        if (s == null){
            return null;
        }
        try {
            return objectMapper.readValue(s, List.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new HttpException();
        }
    }
}
