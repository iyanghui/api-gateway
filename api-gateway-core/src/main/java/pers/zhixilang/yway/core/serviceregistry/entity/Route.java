package pers.zhixilang.yway.core.serviceregistry.entity;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-10-03 11:05
 */
public class Route {
    private String id;

    private String name;

    private String prefix;

    private String route;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
