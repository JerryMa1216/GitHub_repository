package com.greenisland.taxi.utils;

import com.bstek.bdf2.core.orm.hibernate.HibernateDao;

public class BaseHibernateDao extends HibernateDao{

	@Override
	protected String getModuleFixDataSourceName() {
		// TODO Auto-generated method stub
		return "oracle";
	}
	
}
