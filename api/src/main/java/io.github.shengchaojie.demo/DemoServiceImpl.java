package io.github.shengchaojie.demo;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<String> returnListString() {
        return Lists.newArrayList("1","2","3");
    }

    @Override
    public Map<String, String> returnMapStringString() {
        Map<String,String> map = new HashMap<>();
        map.put("1","1");
        return map;
    }

    @Override
    public QueryCurNeedRepayResponse testObject1() {
        QueryCurNeedRepayResponse queryCurNeedRepayResponse = new QueryCurNeedRepayResponse();
        queryCurNeedRepayResponse.setCode("10000");
        queryCurNeedRepayResponse.setInterest(1L);
        queryCurNeedRepayResponse.setMsg("成功");
        queryCurNeedRepayResponse.setPrincipal(0L);
        queryCurNeedRepayResponse.setTotalAmount(0L);
        return queryCurNeedRepayResponse;
    }
}
