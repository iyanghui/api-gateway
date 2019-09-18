package pers.zhixilang.yway.service.bill.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pers.zhixilang.yway.service.bill.service.BillService;

import javax.annotation.Resource;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 11:33
 */
@RestController
@RequestMapping(value = "/bill")
public class BillController {

    @Resource
    private BillService billService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String getBills() {
        return JSON.toJSONString(billService.getBills());
    }
}
