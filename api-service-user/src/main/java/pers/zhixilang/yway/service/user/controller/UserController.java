package pers.zhixilang.yway.service.user.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.zhixilang.yway.service.user.service.UserService;

import javax.annotation.Resource;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 11:10
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String getUsers() {
        return JSON.toJSONString(userService.getUsers());
    }
}
