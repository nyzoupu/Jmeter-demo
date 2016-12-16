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

    //设置可用参数及的默认值；
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("appId", "");
        params.addArgument("privateRSAkey", "");
        params.addArgument("requestUrl", "");
        params.addArgument("method", "");
        params.addArgument("jsonData", "");
        return params;
    }

  //初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，类似于LoadRunner中的init方法
    public void setupTest(JavaSamplerContext arg0) {
         results = new SampleResult();      
    }
    
  //测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法
    public SampleResult runTest(JavaSamplerContext arg0) {
        appId = arg0.getParameter("appId");
        privateRSAkey = arg0.getParameter("privateRSAkey");
        requestUrl = arg0.getParameter("requestUrl");
        method = arg0.getParameter("method");
        jsonData = arg0.getParameter("jsonData");
        
         results.sampleStart();     //定义一个事务，表示这是事务的起始点，类似于LoadRunner的lr.start_transaction
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
             //会显示在结果树的响应数据里  
             System.out.println(jmeterTest);//会输出在Jmeter启动的命令窗口中
         }catch(Throwable e){
             results.setSuccessful(false);
             e.printStackTrace();
         }finally{            
             results.sampleEnd();     //定义一个事务，表示这是事务的结束点，类似于LoadRunner的lr.end_transaction        
         }
       return results;
    }
    
    //结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，类似于LoadRunner中的end方法
    public void teardownTest(JavaSamplerContext arg0) {
    }    
}
