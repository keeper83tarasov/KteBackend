package com.tarasov.ktebackend.config;

import com.tarasov.ktebackend.controllers.ws.ClientWsImpl;
import com.tarasov.ktebackend.controllers.ws.ProductWsImpl;
import com.tarasov.ktebackend.controllers.ws.SaleWsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import javax.xml.ws.Endpoint;

@Configuration
@RequiredArgsConstructor
public class WsConfig {


    private final Bus bus;

    private final ClientWsImpl clientWsImpl;

    private final ProductWsImpl productWsImpl;

    private final SaleWsImpl saleWsImpl;

    @Bean
    public ServletRegistrationBean<Servlet> servletRegistrationBean(ApplicationContext context) {
        return new ServletRegistrationBean<Servlet>(new CXFServlet(), "/api1/*");
    }

    @Bean
    public Endpoint clientWs() {
        EndpointImpl endpoint = new EndpointImpl(bus, clientWsImpl);
        endpoint.publish("/clientWs");
        return endpoint;
    }

    @Bean
    public Endpoint productWs() {
        EndpointImpl endpoint = new EndpointImpl(bus, productWsImpl);
        endpoint.publish("/productWs");
        return endpoint;
    }

    @Bean
    public Endpoint saleWs() {
        EndpointImpl endpoint = new EndpointImpl(bus, saleWsImpl);
        endpoint.publish("/saleWs");
        return endpoint;
    }

}
