package pers.zhixilang.yway.core.serviceregistry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-03 11:11
 */
public class RegistryBean implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent>, BeanNameAware, ApplicationEventPublisherAware {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("====registryBean initial===");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("====registryBean initial===");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("====registryBean initial===");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        System.out.println("====registryBean initial===");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("====registryBean initial===");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("====registryBean initial===");
    }
}
