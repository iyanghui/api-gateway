package pers.zhixilang.lego.gateway.core.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.zhixilang.lego.gateway.core.exception.RouteNotFoundException;
import pers.zhixilang.lego.gateway.core.exception.ServiceUnableException;

import javax.annotation.Resource;
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
public class GatewayServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServlet.class);

    @Resource
    private GatewayRunner runner;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        runner.init(request, response);
        RequestContext context = RequestContext.getContext();
        try {
            runner.preRoute();
            runner.route();
            runner.postRoute();
        } catch (RouteNotFoundException e) {
            LOGGER.error("route not found: url = [{}]", request.getRequestURL(), e);
            context.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (ServiceUnableException e) {
            LOGGER.error("service unable: service name = [{}], service url = [{}]", context.getRoutePrefix(),
                    context.getRouteUrl(), e);
            context.getResponse().sendError(HttpServletResponse.SC_BAD_GATEWAY, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("servlet error, service name = [{}], service url = [{}]", context.getRoutePrefix(),
                    context.getRouteUrl(), e);
            context.getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            context.unset();
        }
    }
}
