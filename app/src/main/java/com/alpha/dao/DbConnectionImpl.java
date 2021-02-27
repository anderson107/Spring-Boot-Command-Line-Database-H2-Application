package com.alpha.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

@Component
public class DbConnectionImpl implements DbConnection {

	private static Logger logger = LoggerFactory.getLogger(DbConnectionImpl.class);

	public DbConnectionImpl( ) {

	}

	@Override
	public DataSource getConnection() {

		try {
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			builder.setType(EmbeddedDatabaseType.H2);
			builder.addScript("classpath:db/schema.sql");
			builder.addScript("classpath:db/dummyData.sql");
			return builder.build();

		} catch (Exception e) {
			logger.error("Problem connecting with DB", e);
			return null;
		}

	}

}
