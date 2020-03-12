package io.github.shengchaojie.demo;

import java.util.List;
import java.util.Map;

/**
 * @author shengchaojie
 * @date 2020-02-09
 **/
public interface DemoService {

    String returnString();

    Integer returnInteger();

    Long returnLong();

    Short returnShort();

    List<String> returnListString();

    Map<String,String> returnMapStringString();

}
