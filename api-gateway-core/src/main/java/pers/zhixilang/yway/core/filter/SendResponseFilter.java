package pers.zhixilang.yway.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import pers.zhixilang.yway.core.context.RequestContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 16:35
 */
public class SendResponseFilter extends AbsWayFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendResponseFilter.class);

    private static final String DEFAULT_CHAR_ENCODING = "UTF-8";

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public void run() {
        RequestContext context = RequestContext.getContext();

        HttpServletResponse response = context.getResponse();

        ResponseEntity responseEntity = context.getResponseEntity();

        try {
            addResponseHeaders(response, responseEntity);
            writeResponse(response, responseEntity);
        } catch (Exception e) {
            LOGGER.error("filter -> [{}] error: ", this.getClass().getName(), e);
        }


    }

    /**
     * 添加响应头
     * @param response HttpServletResponse
     * @param responseEntity ResponseEntity
     */
    private void addResponseHeaders(HttpServletResponse response, ResponseEntity responseEntity) {
        HttpHeaders headers = responseEntity.getHeaders();
        if (headers == null) {
            return;
        }
        Set<String> headerNames =  headers.keySet();
        if (headerNames.size() == 0) {
            return;
        }
        for (String headerName: headerNames) {
            if (headers.get(headerName) == null) {
                continue;
            }
            for (String val: headers.get(headerName)) {
                response.addHeader(headerName, val);
            }
        }
    }

    /**
     * response输出
     * @param response HttpServletResponse
     * @param responseEntity ResponseEntity
     * @throws IOException
     */
    private void writeResponse(HttpServletResponse response, ResponseEntity responseEntity) throws IOException{
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding(DEFAULT_CHAR_ENCODING);
        }
        if (!responseEntity.hasBody()) {
            return;
        }
        byte[] body = (byte[]) responseEntity.getBody();

        OutputStream os = response.getOutputStream();
        assert body != null;
        os.write(body);
        os.flush();
    }
}
