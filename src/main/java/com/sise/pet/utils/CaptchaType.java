package com.sise.pet.utils;

public enum CaptchaType {
   UPDATE_PASSWORD(1,"captcha:updatePassword:"),
   REGISTER(2,"captcha:register:");

     Integer code;
     String value;

    CaptchaType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValue(Integer code) {
        CaptchaType[] values = values();
        for (CaptchaType captchaType : values) {
            if (captchaType.code.equals(code)) {
                return captchaType.value;
            }
        }
        return null;
    }


}
