package pers.zhixilang.yway.core.entity;

import java.util.List;

/**
 * 路由定义
 * TODO 更新改自动注册
 *
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 18:47
 */
public class Route {

    private String path;

    private String url;

    private List<Filter> filters;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
}
