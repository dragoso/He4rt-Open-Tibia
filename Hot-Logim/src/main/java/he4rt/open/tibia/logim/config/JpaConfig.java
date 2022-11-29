package he4rt.open.tibia.logim.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JpaConfig {

    @Bean
    @Primary
    public DataSource DataSource(Properties configProperties) {

        return DataSourceBuilder
                .create()
                .url(configProperties.getProperty("sqlUrl", "jdbc:mysql://localhost/hot?useTimezone=true&serverTimezone=GMT-3"))
                .username(configProperties.getProperty("sqlUser", "root"))
                .password(configProperties.getProperty("sqlPass", "root"))
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties configProperties) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("he4rt");

        Properties jpaProperties = new Properties();

        jpaProperties.put("hibernate.hbm2ddl.auto", configProperties.getProperty("sqlAutoGenerateType", "none"));
        jpaProperties.put("hibernate.show_sql", configProperties.getProperty("sqlViewQuerys", "false"));
        jpaProperties.put("hibernate.format_sql", configProperties.getProperty("sqlFormatQuerys", "false"));

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
