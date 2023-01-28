package com.mrdmi.library.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
@ComponentScan("com.mrdmi.library")
@EnableWebMvc
@PropertySource("classpath:library-database.properties")
public class LibraryConfig implements WebMvcConfigurer{
	
	private final ApplicationContext applicationContext;
	
	private final Environment environment;
	
	@Autowired
	public LibraryConfig(ApplicationContext applicationContext, Environment environment) {
		this.applicationContext = applicationContext;
		this.environment = environment;
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		return templateEngine;
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		registry.viewResolver(thymeleafViewResolver);
	}
	
	@Bean
	public DataSource dataSource( ) {
		DriverManagerDataSource driverManagerDataSource = 
				new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
		driverManagerDataSource.setUrl(environment.getProperty("url"));
		driverManagerDataSource.setUsername(environment.getProperty("user_name"));
		driverManagerDataSource.setPassword(environment.getProperty("password"));
		return driverManagerDataSource;
	}
	
	@Bean
	public JdbcTemplate name() {
		return new JdbcTemplate(dataSource());
	}
}
