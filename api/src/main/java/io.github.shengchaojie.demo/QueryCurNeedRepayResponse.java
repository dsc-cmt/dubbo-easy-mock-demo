package io.github.shengchaojie.demo;

import lombok.Data;

/**
 * @author shengchaojie
 * @date 2020-03-12
 **/
@Data
public class QueryCurNeedRepayResponse extends BaseResponse{

    private Long totalAmount;
    private Long principal;
    private Long interest;
    private Long ovdAmount;

}
