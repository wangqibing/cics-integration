//package com.ccic.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
///
/**
 * 工作流配置文件
 */
//@Configuration
//@EnableTransactionManagement//开启事物管理
//@EnableJpaRepositories(//自定义数据管理的配置
//        entityManagerFactoryRef = "activitiEntityManagerFactory", //指定EntityManager的创建工厂Bean
//        transactionManagerRef = "activitiTransactionManager",//指定事物管理的Bean
//        basePackages = {"com.fintech.insurance.micro.workflow.persist.entity"})//指定管理的实体位置
//public class ActivitiConfiguration extends AbstractProcessEngineAutoConfiguration {
//
//    @Bean("activitiDataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.activiti.datasource")
//    public DataSourceProperties activitiDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean("activitiDataSource")
//    public HikariDataSource activitiDataSource() {
//        DataSourceProperties activitiDataSourceProperties = activitiDataSourceProperties();
//        return (HikariDataSource)activitiDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Primary
//    @Bean("insuranceActivitiConfig")
//    public SpringProcessEngineConfiguration insuranceActivitiConfig(PlatformTransactionManager transactionManager,
//                                                                    SpringAsyncExecutor springAsyncExecutor) throws IOException {
//        SpringProcessEngineConfiguration springProcessEngineConfiguration = baseSpringProcessEngineConfiguration(
//                activitiDataSource(),
//                transactionManager,
//                springAsyncExecutor);
//
//        return springProcessEngineConfiguration;
//    }
//
//}
