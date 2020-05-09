package vip.malagu.config;

import org.malagu.multitenant.domain.DataSourceInfo;
import org.malagu.multitenant.domain.Organization;
import org.malagu.multitenant.listener.DataSourceCreateListener;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(1000)
public class OracleDataSourceCreateListener implements DataSourceCreateListener {

	@Override
	public void onCreate(Organization organization, DataSourceInfo dataSourceInfo,
						 DataSourceBuilder<?> dataSourceBuilder) {

		if ("oracle.jdbc.driver.OracleDriver".equals(dataSourceInfo.getDriverClassName())) {
			dataSourceBuilder.url(dataSourceInfo.getUrl());
			dataSourceBuilder.username(organization.getId());
		}
	}

}
