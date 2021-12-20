package com.lzq.jsyy.msm.service.impl;


import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.lzq.jsyy.msm.service.MsmService;
import com.lzq.jsyy.msm.utils.ConstantPropertiesUtils;
import com.lzq.jsyy.vo.msm.MsmVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @author lzq
 */
@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信服务
     *
     * @param phone
     * @param sixBitRandom
     * @return
     */
    @Override
    public boolean send(String phone, String sixBitRandom) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        String serverIp = "app.cloopen.com";
        String serverPort = "8883";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);

        sdk.setAccount(ConstantPropertiesUtils.ACCOUNT_SID, ConstantPropertiesUtils.ACCOUNT_TOKEN);
        sdk.setAppId(ConstantPropertiesUtils.APP_ID);
        sdk.setBodyType(BodyType.Type_JSON);

        String templateId = "1";
        String[] datas = {sixBitRandom, "2"};

        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, datas);
        return "000000".equals(result.get("statusCode"));
    }

    /**
     * mq使用的，订单发送短信服务
     *
     * @param msmVo
     * @return
     */
    @Override
    public boolean send(MsmVo msmVo) {
        if (StringUtils.isEmpty(msmVo.getPhone())) {
            return false;
        }

        String serverIp = "app.cloopen.com";
        String serverPort = "8883";
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);

        sdk.setAccount(ConstantPropertiesUtils.ACCOUNT_SID, ConstantPropertiesUtils.ACCOUNT_TOKEN);
        sdk.setAppId(ConstantPropertiesUtils.APP_ID);
        sdk.setBodyType(BodyType.Type_JSON);

        String templateId = "1";
        String[] datas = {"000000", "2"};

        HashMap<String, Object> result = sdk.sendTemplateSMS(msmVo.getPhone(), templateId, datas);
        return "000000".equals(result.get("statusCode"));
    }

}
