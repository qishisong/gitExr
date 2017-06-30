package com.blomni.o2o.order.conf;

import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.github.pagehelper.PageHelper;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
//mybaits dao 搜索路径
@MapperScan(basePackages="com.blomni.o2o.order.mapper")
public class MybatisDataSource {
	@Autowired
	private DataSourceProperties dataSourceProperties;
	private static String MYBATIS_CONFIG = "mybatis-config.xml";  
	//mybaits mapper xml搜索路径
	private final static String mapperLocations="classpath:blomni/o2o/service/dao/*.xml"; 

	private org.apache.tomcat.jdbc.pool.DataSource pool;

	@RefreshScope
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {		
		DataSourceProperties config = dataSourceProperties;		
		this.pool = new org.apache.tomcat.jdbc.pool.DataSource();		
		this.pool.setDriverClassName(config.getDriverClassName());
		this.pool.setUrl(config.getUrl());
		if (config.getUsername() != null) {
			this.pool.setUsername(config.getUsername());
		}
		if (config.getPassword() != null) {
			this.pool.setPassword(config.getPassword());
		}
		this.pool.setInitialSize(config.getInitialSize());
		this.pool.setMaxActive(config.getMaxActive());
		this.pool.setMaxIdle(config.getMaxIdle());
		this.pool.setMinIdle(config.getMinIdle());
		this.pool.setTestOnBorrow(config.isTestOnBorrow());
		this.pool.setTestOnReturn(config.isTestOnReturn());
		this.pool.setValidationQuery(config.getValidationQuery());
		return this.pool;
	}

	@PreDestroy
	public void close() {
		if (this.pool != null) {
			this.pool.close();
		}
	}

	@Bean
	@RefreshScope
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
		
		// 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        // 添加插件
        sqlSessionFactoryBean.setPlugins(new Interceptor[] {
                pageHelper
        });
		
		 return sqlSessionFactoryBean.getObject();
	}
	
	@Bean
	//@RefreshScope
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
