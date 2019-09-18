package pers.zhixilang.yway.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.zhixilang.yway.core.context.RequestContext;
import pers.zhixilang.yway.core.runner.WayRunner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 17:12
 */
@WebServlet(name = "yway", urlPatterns = "/api/*")
public class WayServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(WayServlet.class);

    private WayRunner runner = new WayRunner();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // preFilter -> routeFilter -> postFilter
        LOGGER.info(request.getRemoteAddr());
        LOGGER.info(request.getRequestURL().toString());

        runner.init(request, response);
        try {
            runner.preRoute();
            runner.route();
            runner.postRoute();
        } catch (Throwable e) {
            LOGGER.error("servlet error, url = [{}], ", request.getRequestURL().toString(), e);
            RequestContext.getContext().getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } finally {
            RequestContext.getContext().unset();
        }

    }
}
