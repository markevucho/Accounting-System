package org.accounting.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories("org.accounting.repository")
@ComponentScan(basePackages={"org.accounting.controller.impl","org.accounting.service.impl"})
@PropertySource("classpath:jpa.properties")
public class SpringContextConfig {

    private static final String CREATE_SOURCE="javax.persistence.schema-generation.create-source";
    private static final String DATABASE_ACTION="javax.persistence.schema-generation.database.action";
    private static final String PACKAGES_TO_SCAN="org.accounting.model";
    private static final String JDBC_DRIVER="javax.persistence.jdbc.driver";
    private static final String JDBC_URL="javax.persistence.jdbc.url";
    private static final String JDBC_PASSWORD="javax.persistence.jdbc.password";
    private static final String JDBC_USER="javax.persistence.jdbc.user";

    @Autowired
    private Environment env;

    @Bean
    public EntityManagerFactory entityManagerFactory(){

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean=new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
        entityManagerFactoryBean.setJpaProperties(getJpaSchemaGenerationProps());
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());
        return transactionManager;
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter hibernateAdapter=new HibernateJpaVendorAdapter();
        hibernateAdapter.setDatabase(Database.MYSQL);
        hibernateAdapter.setGenerateDdl(false);
        hibernateAdapter.setShowSql(true);
        return hibernateAdapter;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(JDBC_DRIVER));
        dataSource.setUrl(env.getProperty(JDBC_URL));
        dataSource.setPassword(env.getProperty(JDBC_PASSWORD));
        dataSource.setUsername(env.getProperty(JDBC_USER));
        return dataSource;
    }

    private Properties getJpaSchemaGenerationProps(){
        Properties props=new Properties();
        props.setProperty(DATABASE_ACTION,env.getProperty(DATABASE_ACTION));
        props.setProperty(CREATE_SOURCE,env.getProperty(CREATE_SOURCE));
        return props;
    }

}
