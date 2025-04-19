package com.example.weatherapp.utils;

import org.springframework.beans.BeanUtils;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class CustomBeanUtils {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullOrNonExistingPropertyNames(source));
    }

    private static String[] getNullOrNonExistingPropertyNames(Object source) {
        final PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(source.getClass());

        return Arrays.stream(descriptors)
                .map(PropertyDescriptor::getName)
                .filter(name -> {
                    if ("class".equals(name)) return true; // exclude "class" property
                    try {
                        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(source.getClass(), name);
                        if (descriptor != null && descriptor.getReadMethod() != null) {
                            Object value = descriptor.getReadMethod().invoke(source);
                            return value == null;
                        }
                    } catch (Exception ignored) {
                    }
                    return true; // exclude if any error occurs or property can't be read
                })
                .toArray(String[]::new);
    }


}
