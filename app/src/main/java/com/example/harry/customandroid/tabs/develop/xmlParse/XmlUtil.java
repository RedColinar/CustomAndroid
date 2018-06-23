package com.example.harry.customandroid.tabs.develop.xmlParse;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

// SimpleXml地址 http://simple.sourceforge.net/home.php
public class XmlUtil {
    public static <T> T getEntity(String result, Class<T> clazz) {

//        if (clazz.equals(String.class)) {
//            // 去掉 <?xml version="1.0" encoding="utf-8"?>
//            int startResult = result.indexOf(">") + 1;
//            int endResult = result.indexOf("</");
//            return (T) result.substring(startResult, endResult);
//        }

        T t = null;

        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        try {
            t = serializer.read(clazz, result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
}
