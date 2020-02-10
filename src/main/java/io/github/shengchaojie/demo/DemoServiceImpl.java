package io.github.shengchaojie.demo;

/**
 * @author shengchaojie
 * @date 2020-02-10
 **/
public class DemoServiceImpl implements DemoService{
    @Override
    public String returnString() {
        return "123";
    }

    @Override
    public Integer returnInteger() {
        return 1;
    }

    @Override
    public Long returnLong() {
        return 2L;
    }

    @Override
    public Short returnShort() {
        return 3;
    }
}
