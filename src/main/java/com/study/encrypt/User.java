package com.study.encrypt;

import lombok.Data;
import lombok.experimental.Accessors;

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


}
