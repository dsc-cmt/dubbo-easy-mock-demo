package tests;

import com.alibaba.fastjson.JSON;
import io.github.shengchaojie.demo.Application;
import io.github.shengchaojie.demo.DemoService;
import io.github.shengchaojie.demo.DemoServiceImpl;
import io.github.shengchaojie.des.PrimitiveWrapper;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class Tests {

    @Reference
    DemoService demoService;

    private ClientAndServer mockServer;

    private Map<Method,Object> mockConfig = new HashMap<>();

    private DemoServiceImpl demoServiceImpl = new DemoServiceImpl();

    @Before
    public void startMockServer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

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
                                    .withPath("/dubbo-easy-mock-demo/io.github.shengchaojie.demo.DemoService/"+a.getKey().getName())
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
