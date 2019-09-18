package pers.zhixilang.yway.core.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.zhixilang.yway.core.entity.Route;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 15:15
 */
public class RouteParser {

    private static final String ROUTE_URL = "url";

    private static final String ROUTE_PATH = "path";

    /**
     * 读取指定route配置
     * @param is 配置文件路径
     * @return routes
     */
    public static List<Route> readConfig(InputStream is) throws IllegalArgumentException {
        SAXReader reader = new SAXReader();
        Document dom;
        try {
            dom = reader.read(is);
        } catch (DocumentException e) {
            throw new IllegalArgumentException("parse xml error", e);
        }
        Element element = dom.getRootElement();
        List<Element> eleBeanList =  element.elements("route");
        if (eleBeanList == null || eleBeanList.isEmpty()) {
            return null;
        }

        List<Route> routes = new LinkedList<>();
        for (Element bean: eleBeanList) {
            Route route = new Route();
            for (Iterator i = bean.elementIterator(); i.hasNext();) {
                Element node = (Element) i.next();
                String name = node.getName();
                switch (name) {
                    case ROUTE_PATH:
                        route.setPath(node.getText());
                        break;
                    case ROUTE_URL:
                        route.setUrl(node.getText());
                        break;
                        default:
                            break;
                }
            }
            routes.add(route);
        }
        return routes;
    }
}
