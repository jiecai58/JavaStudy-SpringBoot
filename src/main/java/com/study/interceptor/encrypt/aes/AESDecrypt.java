package com.study.interceptor.encrypt.aes;

import com.study.interceptor.encrypt.DecryptUtil;
import com.study.interceptor.annotation.SensitiveField;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.lang.reflect.Field;

/**
 * @author leo
 */
@Service
public class AESDecrypt implements DecryptUtil {

    /**
     * 解密
     *
     * @param result resultType的实例
     * @return T
     * @throws IllegalAccessException 字段不可访问异常
     */
    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            //取出所有被EncryptDecryptField注解的字段
            SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(result);
                //只支持String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对注解的字段进行逐一解密
                    field.set(result, AESUtil.decrypt(value));
                }
            }
        }
        return result;
    }
}
