package com.study.interceptor.example;


import com.study.interceptor.annotation.Desensitization;
import com.study.interceptor.annotation.SensitiveData;
import com.study.interceptor.annotation.SensitiveField;
import com.study.interceptor.enum1.DesensitionType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author leo
 */
@Data
@SensitiveData
@Accessors(chain = true)
public class User {

    private String userName;

    /**
     * 身份证（aes 数据库加密）
     */
    @SensitiveField
    private String identityNo;

    /**
     * 真实姓名（aes 数据库加密）
     */
    @SensitiveField
    private String realName;

    /**
     * 手机号（aes 数据库加密）
     */
    @SensitiveField
    private String mobile;

    /**
     * 身份证号
     */
    @Desensitization(type = DesensitionType.ID_CARD)
    private String idcode;

/**
 * 此时在mapper中，指定paramType=User resultType=User 便可实现脱离业务层，基于mybatis拦截器的加解密操作
 */
}
