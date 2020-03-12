package tests;

import com.alibaba.fastjson.JSON;
import io.github.shengchaojie.demo.DemoService;
import io.github.shengchaojie.demo.DemoServiceImpl;
import io.github.shengchaojie.des.PrimitiveWrapper;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author shengchaojie
 * @date 2020-02-09
 **/
public class Tests {

    private DemoService demoService;

    private ClientAndServer mockServer;

    private Map<Method,Object> mockConfig = new HashMap<>();

    private DemoServiceImpl demoServiceImpl = new DemoServiceImpl();

    @Before
    public void startMockServer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test-consumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");

        ReferenceConfig<DemoService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(DemoService.class);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setApplication(applicationConfig);
        demoService = referenceConfig.get();

        Method[] methods = DemoService.class.getDeclaredMethods();

        for(Method method : methods){
            if(ReflectUtils.isPrimitive(method.getReturnType())){
                mockConfig.put(method,new PrimitiveWrapper(method.invoke(demoServiceImpl)));
            }else{
                mockConfig.put(method,method.invoke(demoServiceImpl));
            }
        }

        mockServer = startClientAndServer(1080);

        mockConfig.entrySet().stream().forEach(a->{
            mockServer
                    .when(
                            request()
                                    .withMethod("GET")
                                    .withPath("/dubbo-easy-mock-demo/"+ DemoService.class.getName() +"/"+a.getKey().getName())
                    )
                    .respond(
                            response()
                                    .withStatusCode(200)
                                    .withBody(JSON.toJSONString(a.getValue()))
                    );
        });

    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void autoMethodTest() throws InvocationTargetException, IllegalAccessException {
        Method[] methods = DemoService.class.getDeclaredMethods();

        for(Method method : methods){
            Assert.assertEquals(method.invoke(demoService),method.invoke(demoServiceImpl));
        }

    }

}
