package pers.zhixilang.yway.core.context;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 17:04
 */
public class RequestContext extends ConcurrentHashMap<String, Object> {

    protected static Class<? extends RequestContext> contextClass = RequestContext.class;

    protected static final ThreadLocal<? extends RequestContext> THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return contextClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    });

    public static RequestContext getContext() {
        // 得益于servlet容器使用的线程池技术
        return THREAD_LOCAL.get();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) get("request");
    }

    public void setRequest(HttpServletRequest request) {
        put("request", request);
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) get("response");
    }

    public void setResponse(HttpServletResponse response) {
        set("response", response);
    }


    public void setRequestEntity(RequestEntity requestEntity){
        set("requestEntity",requestEntity);
    }

    public RequestEntity getRequestEntity() {
        return (RequestEntity) get("requestEntity");
    }

    public void setResponseEntity(ResponseEntity responseEntity){
        set("responseEntity",responseEntity);
    }

    public ResponseEntity getResponseEntity() {
        return (ResponseEntity) get("responseEntity");
    }

    public void setServiceName(String serviceName) {
        set("serviceName", serviceName);
    }

    public String getServiceName() {
        return get("serviceName").toString();
    }

    public void setServiceUrl(String url) {
        set("serviceUrl", url);
    }

    public String getServiceUrl() {
        return get("serviceUrl").toString();
    }

    public void unset() {
        THREAD_LOCAL.remove();
    }

    private void set(String key, Object value) {
        if (value != null) {
            put(key, value);
        } else {
            remove(key);
        }
    }
}
