package pers.zhixilang.yway.core.config;

import org.springframework.context.annotation.Configuration;
import pers.zhixilang.yway.core.entity.Route;
import pers.zhixilang.yway.core.util.RouteParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 15:14
 */
@Configuration
public class RouteConfig {

    private static List<Route> routes;

    private static final String ROUTE_PREFIX = "/api/";

    public void init() {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("route.xml");
            routes = RouteParser.readConfig(is);
        } catch (Exception e) {
            throw new RuntimeException("路由配置文件读取错误: ", e);
        }
        if (routes == null || routes.size() == 0) {
            throw new RuntimeException("没有路由");
        }

    }

    public String getServiceName(String uri) {
        for (Route route: routes) {
            if (uri.startsWith(route.getPath())) {
                return route.getPath();
            }
        }
        return "";
    }

    public String getServiceUrl(String uri) {
        List<String> serviceList = new ArrayList<>();

        for (Route route: routes) {
            if (uri.startsWith(route.getPath())) {
                serviceList.add(route.getUrl() + uri.replace(ROUTE_PREFIX, ""));
            }
        }
        // simple load balance
        return serviceList.get(new Random().nextInt(serviceList.size()));
    }
}
