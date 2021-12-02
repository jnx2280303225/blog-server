package com.jnx.blogserver.api.user;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author 蒋楠鑫
 * @since 2021/12/2
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户信息api接口", tags = "用户信息")
public class UserController {


}
