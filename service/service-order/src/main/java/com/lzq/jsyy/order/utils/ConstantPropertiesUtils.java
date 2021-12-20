package com.lzq.jsyy.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lzq
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    public static String APPID;
    public static String PARTNER;
    public static String PARTNERKEY;
    public static String CERT;
    public static String URL;
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.partner}")
    private String partner;
    @Value("${weixin.partnerkey}")
    private String partnerkey;
    @Value("${weixin.cert}")
    private String cert;
    @Value("${weixin.url}")
    private String url;

    @Override
    public void afterPropertiesSet() {
        APPID = appid;
        PARTNER = partner;
        PARTNERKEY = partnerkey;
        CERT = cert;
        URL = url;
    }
}
