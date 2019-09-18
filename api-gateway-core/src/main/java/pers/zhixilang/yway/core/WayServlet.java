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

    private WayRunner runner;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        runner.init(request, response);
        RequestContext context = RequestContext.getContext();
        try {
            runner.preRoute();
            runner.route();
            runner.postRoute();
        } catch (Throwable e) {

            LOGGER.error("servlet error, origin url = [{}], service url = [{}]", request.getRequestURL().toString(),
                    context.getRequestEntity().getUrl(), e);
            context.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } finally {
            context.unset();
        }

    }

    @Override
    public void init() throws ServletException {
        runner = new WayRunner();
    }
}
