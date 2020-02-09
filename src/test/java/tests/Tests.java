package tests;

import com.alibaba.fastjson.JSON;
import io.github.shengchaojie.demo.Application;
import io.github.shengchaojie.demo.DemoService;
import io.github.shengchaojie.des.PrimitiveWrapper;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.integration.ClientAndServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    private ClientAndServer mockServer;

    @Before
    public void startMockServer() {
        mockServer = startClientAndServer(1080);

        mockServer
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/dubbo-easy-mock-demo/io.github.shengchaojie.demo.DemoService/returnString")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(JSON.toJSONString(new PrimitiveWrapper("7758")))
                );
    }

    @After
    public void stopMockServer() {
        mockServer.stop();
    }

    @Reference
    DemoService demoService;

    @Test
    public void testReturnString(){
        System.out.println(demoService.returnString());
    }

}
