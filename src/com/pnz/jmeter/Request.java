/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.pnz.jmeter;

import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

import com.alibaba.fastjson.JSONObject;
import com.shangshu.bigdatagw.sdk.CommonPushResult;
import com.shangshu.bigdatagw.sdk.DefaultGatewayClient;
import com.shangshu.bigdatagw.sdk.DefaultRequest;
import com.shangshu.bigdatagw.sdk.GatewayClient;

public class Request {

    public CommonPushResult jmeterTest(String appId, String privateRSAkey, String requestUrl,
                                       String method, String jsonData) throws IOException,
                                                                      Exception {
        GatewayClient client = new DefaultGatewayClient(appId, requestUrl, privateRSAkey);
        DefaultRequest request = new DefaultRequest();
        request.setMethod(method);
        request.setRequestParams(JSONObject.parseObject(jsonData, Map.class));
        CommonPushResult result = client.execute(request);
        return result;
    }
    
    public static void main(String[] args) {
        String string="{\"name\":\"zp\"}";
        JSONObject.parseObject(string, Map.class);
    }

}
