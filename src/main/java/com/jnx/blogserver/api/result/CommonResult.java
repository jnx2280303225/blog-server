package com.jnx.blogserver.api.result;

import com.jnx.blogserver.constant.ResultCodeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通用的返回对象
 *
 * @author 蒋楠鑫
 * @since 2021/12/10
 */
@Data
@ApiModel("通用的返回对象")
public class CommonResult<T> {

    @ApiModelProperty("返回码")
    private Integer code;

    @ApiModelProperty("返回消息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;

    private CommonResult() {

    }

    /**
     * 操作成功
     *
     * @param resultData 返回的数据
     * @param <T>        数据类型
     * @return 操作成功
     */
    public static <T> CommonResult<T> success(T resultData) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(ResultCodeConstant.SUCCESS_CODE);
        commonResult.setMessage("操作成功");
        commonResult.setData(resultData);
        return commonResult;
    }

    /**
     * 操作成功
     *
     * @param code
     * @param message
     * @param resultData
     * @param <T>
     * @return
     */
    public static <T> CommonResult<T> success(int code, String message, T resultData) {
        CommonResult<T> commonResult = new CommonResult<>();
        commonResult.setCode(code);
        commonResult.setMessage(message);
        commonResult.setData(resultData);
        return commonResult;
    }
}
