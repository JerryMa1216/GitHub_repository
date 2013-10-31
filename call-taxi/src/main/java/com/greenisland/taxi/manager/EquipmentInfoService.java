package com.greenisland.taxi.manager;

import java.util.List;

import org.springframework.stereotype.Component;

import com.greenisland.taxi.common.BaseHibernateDao;
import com.greenisland.taxi.domain.EquipmentInfo;

/**
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-10-20下午3:29:11
 */
@Component("equipmentInfoService")
public class EquipmentInfoService extends BaseHibernateDao {
	@SuppressWarnings("unchecked")
	public EquipmentInfo getEquipmentById(String id) {
		List<EquipmentInfo> list = this.getHibernateTemplate().find("from EquipmentInfo e where e.id =?", id);
		return list.size() > 0 && list != null ? list.get(0) : null;
	}

	public void update(EquipmentInfo equipmentInfo) {
		this.getHibernateTemplate().update(equipmentInfo);
	}
}