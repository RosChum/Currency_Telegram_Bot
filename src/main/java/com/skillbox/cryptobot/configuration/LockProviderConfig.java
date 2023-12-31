package com.skillbox.cryptobot.configuration;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LockProviderConfig {

    @Bean
    public LockProvider getLockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(dataSource);
    }
}
