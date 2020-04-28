package com.vectory.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(
                    Objects.requireNonNull(PropertiesUtil.class.getClassLoader().getResourceAsStream("gmall.properties")),
                    StandardCharsets.UTF_8)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PropertiesUtil(){}

    public static String getProperty(String key){
        String value = properties.getProperty(key.trim());
        if(StringUtils.isBlank(value.trim())){
            return null;
        }
        return value;
    }

    public static String getProperty(String key, String defaultValue){
        String value = properties.getProperty(key);
        if(StringUtils.isBlank(value.trim())){
            return defaultValue;
        }
        return value;
    }
}
