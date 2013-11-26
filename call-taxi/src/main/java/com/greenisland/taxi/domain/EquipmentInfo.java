package com.greenisland.taxi.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-8-15下午5:01:15
 */
@Entity
@Table(name = "TS_EQUIPMENT_INFO")
public class EquipmentInfo {
	private String id;
	private String equipmentId;
	private Integer requestCaptchaCount;
	private String uid;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID_")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "EQUIPMENT_ID_")
	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	@Column(name = "REQUEST_CAPTCHA_COUNT_")
	public Integer getRequestCaptchaCount() {
		return requestCaptchaCount;
	}

	public void setRequestCaptchaCount(Integer requestCaptchaCount) {
		this.requestCaptchaCount = requestCaptchaCount;
	}

	public EquipmentInfo() {
		super();
	}

	public EquipmentInfo(String id, String equipmentId, Integer requestCaptchaCount) {
		super();
		this.id = id;
		this.equipmentId = equipmentId;
		this.requestCaptchaCount = requestCaptchaCount;
	}
	@Column(name="USER_ID_")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	

}
