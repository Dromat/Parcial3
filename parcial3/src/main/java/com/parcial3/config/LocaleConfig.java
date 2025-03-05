package com.parcial3.config;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocaleConfig {

    @Bean
    public LocaleContextResolver localeContextResolver() {
        return new CustomLocaleContextResolver();
    }

    public static class CustomLocaleContextResolver extends AcceptHeaderLocaleContextResolver {
        @Override
        public LocaleContext resolveLocaleContext(ServerWebExchange exchange) {
            String lang = exchange.getRequest().getQueryParams().getFirst("lang");
            if (lang != null && !lang.isEmpty()) {
                Locale locale = Locale.forLanguageTag(lang);
                return new SimpleLocaleContext(locale);
            }
            return super.resolveLocaleContext(exchange);
        }
    }
}