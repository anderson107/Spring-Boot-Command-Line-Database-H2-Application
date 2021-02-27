package com.alpha.dao;

import javax.sql.DataSource;

public interface DbConnection {

	public DataSource getConnection();
	
}
