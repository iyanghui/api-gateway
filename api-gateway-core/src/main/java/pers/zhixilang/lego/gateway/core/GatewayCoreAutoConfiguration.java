package pers.zhixilang.lego.gateway.core;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pers.zhixilang.lego.gateway.core.filter.ContextBuildFilter;
import pers.zhixilang.lego.gateway.core.filter.LimitFilter;
import pers.zhixilang.lego.gateway.core.filter.RequestWrapperFilter;
import pers.zhixilang.lego.gateway.core.filter.RouteFilter;
import pers.zhixilang.lego.gateway.core.filter.SendResponseFilter;
import pers.zhixilang.lego.gateway.core.runner.GatewayRunner;
import pers.zhixilang.lego.gateway.core.runner.GatewayServlet;

import java.time.Duration;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 15:29
 */
@Configuration
public class GatewayCoreAutoConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofMillis(60000L)).build();
    }

    @Bean
    public GatewayRunner gatewayRunner() {
        return new GatewayRunner();
    }

    @Bean
    public GatewayServlet gatewayServlet() {
        return new GatewayServlet();
    }

    @Bean
    public ContextBuildFilter contextBuildFilter() {
        return new ContextBuildFilter();
    }

    @Bean
    public RequestWrapperFilter requestWrapperFilter() {
        return new RequestWrapperFilter();
    }

    @Bean
    public LimitFilter limitFilter() {
        return new LimitFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

    @Bean
    public SendResponseFilter sendResponseFilter() {
        return new SendResponseFilter();
    }

}
