package com.greenisland.taxi.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jerry
 * @E-mail jerry.ma@bstek.com
 * @version 2013-7-30上午10:45:21
 */
@Entity
@Table(name = "ts_call_apply_info")
public class CallApplyInfo implements Serializable {
	private static final long serialVersionUID = -7391073506344915844L;
	private String id;
	private String startLocation;
	private String endLocation;
	private Date callTime;
	private String callType;
	private String state;
	private String responseState;
	private String tradeState;
	private String isGetOn;
	private String isComment;
	private String userId;
	private String taxiId;
	private String canncelReason;// 取消原因
	private Date appointmentTime;// 预约时间
	private Integer callScope;// 叫车范围
	private Integer monitorCount = 0;// 监控次数
	private String deleteFlag;// 删除标记
	private String mechineType;// 设备类型 1.ios 2.android
	private Integer callLength;// 叫车距离
	private Date createDate;
	private Date updateDate;

	private UserInfo userInfo;
	private TaxiInfo taxiInfo;
	private CommentInfo commentInfo;

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

	@Column(name = "START_LOCATION_")
	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	@Column(name = "END_LOCATION_")
	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	@Column(name = "CALL_TIME_")
	public Date getCallTime() {
		return callTime;
	}

	public void setCallTime(Date callTime) {
		this.callTime = callTime;
	}

	@Column(name = "CALL_TYPE_")
	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	@Column(name = "STATE_")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "RESPONSE_STATE_")
	public String getResponseState() {
		return responseState;
	}

	public void setResponseState(String responseState) {
		this.responseState = responseState;
	}

	@Column(name = "TRADE_STATE_")
	public String getTradeState() {
		return tradeState;
	}

	public void setTradeState(String tradeState) {
		this.tradeState = tradeState;
	}

	@Column(name = "IS_GET_ON_")
	public String getIsGetOn() {
		return isGetOn;
	}

	public void setIsGetOn(String isGetOn) {
		this.isGetOn = isGetOn;
	}

	@Column(name = "IS_COMMENT_")
	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	@Column(name = "USER_ID_")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "TAXI_ID_")
	public String getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(String taxiId) {
		this.taxiId = taxiId;
	}

	@Column(name = "CANCEL_REASON_")
	public String getCanncelReason() {
		return canncelReason;
	}

	public void setCanncelReason(String canncelReason) {
		this.canncelReason = canncelReason;
	}

	@Column(name = "APPOINTMENT_START_TIME_")
	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	@Column(name = "CALL_SCOPE_")
	public Integer getCallScope() {
		return callScope;
	}

	public void setCallScope(Integer callScope) {
		this.callScope = callScope;
	}

	@Column(name = "MONITOR_COUNT_")
	public Integer getMonitorCount() {
		return monitorCount;
	}

	public void setMonitorCount(Integer monitorCount) {
		this.monitorCount = monitorCount;
	}

	@Column(name = "DELETE_FLAG_")
	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Column(name = "MECHINE_TYPE_")
	public String getMechineType() {
		return mechineType;
	}

	public void setMechineType(String mechineType) {
		this.mechineType = mechineType;
	}

	@Column(name = "CREATE_DATE_")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_DATE_")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "CALL_DISTANCE_")
	public Integer getCallLength() {
		return callLength;
	}

	public void setCallLength(Integer callLength) {
		this.callLength = callLength;
	}

	public CallApplyInfo(String id, String startLocation, String endLocation, Date callTime, String callType, String state, String responseState, String tradeState, String isGetOn, String isComment,
			String userId, String taxiId, String canncelReason, Date appointmentTime, Integer callScope, Integer monitorCount, String deleteFlag, String mechineType, Date createDate, Date updateDate) {
		super();
		this.id = id;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
		this.callTime = callTime;
		this.callType = callType;
		this.state = state;
		this.responseState = responseState;
		this.tradeState = tradeState;
		this.isGetOn = isGetOn;
		this.isComment = isComment;
		this.userId = userId;
		this.taxiId = taxiId;
		this.canncelReason = canncelReason;
		this.appointmentTime = appointmentTime;
		this.callScope = callScope;
		this.monitorCount = monitorCount;
		this.deleteFlag = deleteFlag;
		this.mechineType = mechineType;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public CallApplyInfo() {
		super();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID_", insertable = false, updatable = false)
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAXI_ID_", insertable = false, updatable = false)
	public TaxiInfo getTaxiInfo() {
		return taxiInfo;
	}

	public void setTaxiInfo(TaxiInfo taxiInfo) {
		this.taxiInfo = taxiInfo;
	}

	@Transient
	public CommentInfo getCommentInfo() {
		return commentInfo;
	}

	public void setCommentInfo(CommentInfo commentInfo) {
		this.commentInfo = commentInfo;
	}

}
