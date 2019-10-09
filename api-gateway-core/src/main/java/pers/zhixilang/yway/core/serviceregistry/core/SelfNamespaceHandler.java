package pers.zhixilang.yway.core.serviceregistry.core;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import pers.zhixilang.yway.core.serviceregistry.*;

/**
 * 自定义schema解析器
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-02 11:46
 */
public class SelfNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        System.out.println("====handler");
        registerBeanDefinitionParser("upstream", new RouteBeanDefinitionParser(ServiceConfig.class));

        registerBeanDefinitionParser("downstream", new RouteBeanDefinitionParser(ServiceConfig.class));

        registerBeanDefinitionParser("registry", new RouteBeanDefinitionParser(RegistryBean.class));

        registerBeanDefinitionParser("discover", new RouteBeanDefinitionParser(DiscoverBean.class));
    }
}
