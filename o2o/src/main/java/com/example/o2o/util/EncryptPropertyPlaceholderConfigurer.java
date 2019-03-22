package com.example.o2o.util;


import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    // all the String need to decryptStirng
    private String[] encryptPropNames = {"jdbc.username","jdbc.password"};

    /**
     * return the property value of the corresponding property value
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
         if(isEncryptProp(propertyName)){
            String decryptValue = DESUtil.getDecryptString(propertyValue);
            return decryptValue;
        }
        return propertyValue;
    }

    /**
     * verify if that property has encrypted
     * @param propertyName
     * @return
     */
    private boolean isEncryptProp(String propertyName) {
        for(String encryptPropName:encryptPropNames){
            if(propertyName.equals(encryptPropName)) return true;
        }
        return false;
    }
}
