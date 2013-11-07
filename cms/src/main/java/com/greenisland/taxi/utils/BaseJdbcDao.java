package com.greenisland.taxi.utils;

import com.bstek.bdf2.core.orm.jdbc.JdbcDao;

public class BaseJdbcDao extends JdbcDao{

	@Override
	protected String getModuleFixDataSourceName() {
		// TODO Auto-generated method stub
		return "oracle";
	}
	
}
