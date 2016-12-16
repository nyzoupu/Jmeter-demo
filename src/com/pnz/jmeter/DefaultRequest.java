package com.pnz.jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.shangshu.bigdatagw.sdk.CommonPushResult;

public class DefaultRequest extends AbstractJavaSamplerClient {

    private SampleResult results;
    private String       appId;
    private  String               privateRSAkey;
    private String               requestUrl;
    private String               method;
    private String               jsonData;

    //���ÿ��ò�������Ĭ��ֵ��
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("appId", "");
        params.addArgument("privateRSAkey", "");
        params.addArgument("requestUrl", "");
        params.addArgument("method", "");
        params.addArgument("jsonData", "");
        return params;
    }

  //��ʼ��������ʵ������ʱÿ���߳̽�ִ��һ�Σ��ڲ��Է�������ǰִ�У�������LoadRunner�е�init����
    public void setupTest(JavaSamplerContext arg0) {
         results = new SampleResult();      
    }
    
  //����ִ�е�ѭ���壬�����߳�����ѭ�������Ĳ�ͬ��ִ�ж�Σ�������LoadRunner�е�Action����
    public SampleResult runTest(JavaSamplerContext arg0) {
        appId = arg0.getParameter("appId");
        privateRSAkey = arg0.getParameter("privateRSAkey");
        requestUrl = arg0.getParameter("requestUrl");
        method = arg0.getParameter("method");
        jsonData = arg0.getParameter("jsonData");
        
         results.sampleStart();     //����һ�����񣬱�ʾ�����������ʼ�㣬������LoadRunner��lr.start_transaction
         try{
             Request test = new Request();
             CommonPushResult jmeterTest = test.jmeterTest(appId, privateRSAkey, requestUrl, method, jsonData);
          
             if (jmeterTest.isSuccess()) {
                results.setSuccessful(true);
                results.setResponseData(jmeterTest.getBizData(), "utf-8");
                results.setDataType(SampleResult.TEXT);
             }else {
                 results.setSuccessful(false);
                 results.setResponseData(jmeterTest.getErrorCode() + "," + jmeterTest.getErrorMessage(), "utf-8");
            }
             //����ʾ�ڽ��������Ӧ������  
             System.out.println(jmeterTest);//�������Jmeter�������������
         }catch(Throwable e){
             results.setSuccessful(false);
             e.printStackTrace();
         }finally{            
             results.sampleEnd();     //����һ�����񣬱�ʾ��������Ľ����㣬������LoadRunner��lr.end_transaction        
         }
       return results;
    }
    
    //����������ʵ������ʱÿ���߳̽�ִ��һ�Σ��ڲ��Է������н�����ִ�У�������LoadRunner�е�end����
    public void teardownTest(JavaSamplerContext arg0) {
    }    
}
