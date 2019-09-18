package pers.zhixilang.yway.service.bill.service;

import org.springframework.stereotype.Service;
import pers.zhixilang.yway.service.bill.entity.BillEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 11:34
 */
@Service
public class BillService {

    public List<BillEntity> getBills() {
        List<BillEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BillEntity entity = new BillEntity();
            entity.setId((long) i);
            entity.setUserId((long) i);
            entity.setDate(20190918120000L);
            entity.setAmount(new BigDecimal(i));
            list.add(entity);
        }
        return list;
    }
}
