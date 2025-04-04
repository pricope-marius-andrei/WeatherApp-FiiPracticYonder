package com.example.weatherapp.utils;

import org.springframework.beans.BeanUtils;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class CustomBeanUtils {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        final PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(source.getClass());
        return Arrays.stream(descriptors)
                .map(PropertyDescriptor::getName)
                .filter(name -> {
                    try {
                        return BeanUtils.getPropertyDescriptor(source.getClass(), name)
                                .getReadMethod().invoke(source) == null;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toArray(String[]::new);
    }


}
