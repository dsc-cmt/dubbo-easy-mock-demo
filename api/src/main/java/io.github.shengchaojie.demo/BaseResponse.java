package io.github.shengchaojie.demo;

import lombok.Data;

/**
 * @author shengchaojie
 * @date 2020-03-12
 **/
@Data
public class BaseResponse {

    private Boolean success;
    private String code;
    private String msg;
    private String subCode;
    private String subMsg;

}
