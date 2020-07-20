package com.diamond.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TransactionProvider;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.ThreadLocalTransactionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.hikari.connection-timeout}") String connectionTimeout,
            @Value("${spring.datasource.hikari.maximum-pool-size}") int maxPoolSize
    ) {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setConnectionTimeout(Long.valueOf(connectionTimeout));
        hikariConfig.setAutoCommit(true);
        hikariConfig.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        hikariConfig.setPoolName("diamond-db");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public TransactionProvider jooqTransactionProvider(DataSource dataSource){
        return new ThreadLocalTransactionProvider(new DataSourceConnectionProvider(dataSource));
    }

    @Bean
    public DSLContext dslContext(DataSource dataSource, TransactionProvider transactionProvider){
        final DefaultConfiguration config =  new DefaultConfiguration();
        config.setDataSource(dataSource);
        config.setSQLDialect(SQLDialect.POSTGRES);
        config.setTransactionProvider(transactionProvider);
        return new DefaultDSLContext(config);
    }
}
