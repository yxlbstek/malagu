package vip.malagu;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RestController;

import com.bstek.uflo.console.UfloServlet;
import com.bstek.ureport.console.UReportServlet;
import com.bstek.urule.console.servlet.URuleServlet;

@SpringBootApplication
@RestController
@EnableScheduling
@ComponentScan({ "vip.malagu", "com.bstek.uflo.model" })
@ImportResource({"classpath:ureport-console-context.xml", "classpath:uflo-console-context.xml", "classpath:urule-console-context.xml"})
public class MalaguApplication {

	private DataSource dataSource;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MalaguApplication.class, args);
	}

	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public ServletRegistrationBean UReportServletRegistration() { 
        ServletRegistrationBean registration = new ServletRegistrationBean(new UReportServlet());  
        registration.addUrlMappings("/ureport/*");
        return registration;
    }
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public ServletRegistrationBean UfloServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new UfloServlet());  
        registration.addUrlMappings("/uflo/*");
        return registration;
    }
	
	@Bean
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ServletRegistrationBean UruleServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new URuleServlet());
		registration.addUrlMappings("/urule/*");
		return registration;
	}

	@Bean
	public LocalSessionFactoryBean localSessionFactoryBean() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		dataSource = DataSourceBuilder.create(MalaguApplication.class.getClassLoader())
				.driverClassName("com.mysql.jdbc.Driver").url("jdbc:mysql://localhost:3306/master?useUnicode=true&characterEncoding=utf-8&useSSL=false").username("root")
				.password("root").build();
		localSessionFactoryBean.setDataSource(dataSource);
		localSessionFactoryBean.setPackagesToScan("com.bstek.uflo.model*");
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.hbm2ddl.auto", "update");
		localSessionFactoryBean.setHibernateProperties(properties);
		return localSessionFactoryBean;
	}
	
//	@Bean
//	public LocalSessionFactoryBean localSessionFactoryBean() {
//		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
//		dataSource = DataSourceBuilder.create(MalaguApplication.class.getClassLoader())
//				.driverClassName("oracle.jdbc.driver.OracleDriver").url("jdbc:oracle:thin:@127.0.0.1:1521:orcl").username("master")
//				.password("123456").build();
//		localSessionFactoryBean.setDataSource(dataSource);
//		localSessionFactoryBean.setPackagesToScan("com.bstek.uflo.model*");
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
//		properties.put("hibernate.hbm2ddl.auto", "update");
//		localSessionFactoryBean.setHibernateProperties(properties);
//		return localSessionFactoryBean;
//	}
	
	@Bean
	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

}
