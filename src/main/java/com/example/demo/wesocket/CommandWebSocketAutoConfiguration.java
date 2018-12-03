package com.example.demo.wesocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurationSupport;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import java.util.List;

/**
 * @author kid
 */
@Configuration
public class CommandWebSocketAutoConfiguration {

    @Bean
    public WebSocketMessager DefaultWebSocketMessager(){
        return new DefaultWebSocketMessager();
    }


    @Bean
    public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(10 * 1024 * 1024);
        container.setMaxBinaryMessageBufferSize(10 * 1024 * 1024);
        return container;
    }


    @Configuration
    public static class HandlerConfiguration extends WebSocketConfigurationSupport {
        private String[] allowedOrigins;

        public void setAllowedOrigins(String[] allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        @Autowired(required = false)
        private List<WebSocketMessager> webSocketMessagers;

        @Override
        protected void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            CommandWebSocketMessageDispatcher dispatcher = new CommandWebSocketMessageDispatcher();
            dispatcher.setWebSocketMessagers(webSocketMessagers);

            registry.addHandler(dispatcher, "/messageHandler")
                    .setAllowedOrigins("*")
                    .withSockJS()
                    .setSessionCookieNeeded(true);

            registry.addHandler(dispatcher, "/messageHandler");
            registry.addHandler(dispatcher, "/messageHandler")
                    .setAllowedOrigins("*");
        }
    }

}
