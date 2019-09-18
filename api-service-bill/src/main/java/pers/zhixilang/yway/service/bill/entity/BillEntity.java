package pers.zhixilang.yway.service.bill.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 11:34
 */
@Data
@NoArgsConstructor
public class BillEntity {
    private Long id;

    private Long userId;

    private Long date;

    private BigDecimal amount;
}
