package pers.zhixilang.yway.core.serviceregistry.core;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import pers.zhixilang.yway.core.config.RouteConfig;
import pers.zhixilang.yway.core.serviceregistry.*;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-02 15:42
 */
public class RouteBeanDefinitionParser implements BeanDefinitionParser {

    private Class beanClass;

    public RouteBeanDefinitionParser(Class beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        return parse(element, parserContext, beanClass);
    }

    private BeanDefinition parse(Element element, ParserContext context, Class beanClass) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);

        String id = element.getAttribute("id");
        if (RegistryBean.class.equals(beanClass)) {

        } else if (DiscoverBean.class.equals(beanClass)) {

        } else if (RouteConfig.class.equals(beanClass)) {

        }
        return beanDefinition;
    }
}
