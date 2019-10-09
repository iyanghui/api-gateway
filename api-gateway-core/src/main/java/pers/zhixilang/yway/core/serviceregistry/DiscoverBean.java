package pers.zhixilang.yway.core.serviceregistry;

import org.springframework.beans.factory.InitializingBean;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-03 11:11
 */
public class DiscoverBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("====discoverBean initial===");
    }
}
