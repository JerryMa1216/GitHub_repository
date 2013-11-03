package com.greenisland.taxi.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.greenisland.taxi.common.BaseHibernateDao;
import com.greenisland.taxi.common.constant.ApplicationState;
import com.greenisland.taxi.common.constant.ResponseState;
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

	/**
	 * 根据订单id查询订单信息
	 * 
	 * @param id
	 * @return
	 */
	public CallApplyInfo getCallApplyInfoById(String id) {
		return this.getHibernateTemplate().get(CallApplyInfo.class, id);
	}

	/**
	 * 查询复杂条件订单信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public CallApplyInfo getApplyInfoValidated(String id) {
		StringBuilder hql = new StringBuilder("from CallApplyInfo c where 1=1 ");
		// 订单未响应
		hql.append("and c.responseState ='" + ResponseState.NO_RESPONSE + "' ");
		// 订单未删除，申请状态为有效
		hql.append("and c.deleteFlag ='N' and c.state='" + ApplicationState.VALIDATION + "' ");
		hql.append("and c.id =?");
		List<CallApplyInfo> list = this.getHibernateTemplate().find(hql.toString(), id);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 根据用户id读取所有订单信息
	 * 
	 * @param uid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CallApplyInfo> queryApplyInfoByUid(String uid) {
		StringBuilder hql = new StringBuilder("from CallApplyInfo c where 1=1 ");
		hql.append(" and c.deleteFlag ='N' and c.state='" + ApplicationState.VALIDATION + "' ");
		hql.append("and c.userId=?");
		List<CallApplyInfo> list = this.getHibernateTemplate().find(hql.toString(), uid);
		return list;
	}

	public String saveCallApplyInfo(CallApplyInfo applyInfo) {
		return (String) this.getHibernateTemplate().save(applyInfo);
	}

	public void updateApplyInfo(CallApplyInfo applyInfo) {
		this.getHibernateTemplate().update(applyInfo);
	}
}
