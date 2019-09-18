package pers.zhixilang.yway.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import pers.zhixilang.yway.core.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 16:35
 */
public class RequestWrapperFilter extends AbsWayFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestWrapperFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public void run() {

        RequestContext context = RequestContext.getContext();

        HttpServletRequest request = context.getRequest();

        try {
            RequestEntity<byte[]> entity = createRequestEntity(request);
            context.setRequestEntity(entity);
        } catch (URISyntaxException e) {
            LOGGER.error("filter -> [{}] createRequestEntity error: ", this.getClass().getName(), e);
        } catch (IOException e) {
            LOGGER.error("filter -> [{}] createRequestBody error: ", this.getClass().getName(), e);
        } catch (Exception e) {
            LOGGER.error("filter -> [{}] unknow error: ", this.getClass().getName(), e);
        }

    }

    /**
     * 构造出RestTemplate能识别的RequestEntity
     * @param request HttpServletRequest
     * @return RequestEntity
     * @throws URISyntaxException
     * @throws IOException
     */
    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest request) throws URISyntaxException, IOException {
        HttpMethod method = HttpMethod.resolve(request.getMethod());

        MultiValueMap<String, String> headers = createRequestHeaders(request);

        byte[] body = createRequestBody(request);

        return new RequestEntity<>(body, headers, method, new URI(request.getRequestURI()));
    }

    /**
     * 封装请求体
     * @param request HttpServletRequest
     * @return 请求体
     * @throws IOException
     */
    private byte[] createRequestBody(HttpServletRequest request) throws IOException {
        return StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * 封装请求头
     * @param request HttpServletRequest
     * @return HttpHeaders
     */
    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (null == headerNames) {
            return httpHeaders;
        }
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            httpHeaders.add(headerName, request.getHeader(headerName));
        }
        return httpHeaders;
    }
}
