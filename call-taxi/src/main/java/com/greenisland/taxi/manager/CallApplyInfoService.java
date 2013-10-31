package com.greenisland.taxi.manager;

import org.springframework.stereotype.Component;

import com.greenisland.taxi.common.BaseHibernateDao;
import com.greenisland.taxi.domain.CallApplyInfo;

/**
 * 叫车请求service
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-20下午3:33:08
 */
@Component("callApplyInfoService")
public class CallApplyInfoService extends BaseHibernateDao {

	public CallApplyInfo getCallApplyInfoById(String id) {
		return this.getHibernateTemplate().get(CallApplyInfo.class, id);
	}
}
