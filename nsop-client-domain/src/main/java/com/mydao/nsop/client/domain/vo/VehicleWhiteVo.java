package com.mydao.nsop.client.domain.vo;

import java.util.Date;

/**
 * <p>
 * 车辆白名单表
 * </p>
 *
 * @author ZYW
 * @since 2018-05-07
 */
public class VehicleWhiteVo{

    /**
     * 主键id
     */
	private Long id;
    /**
     * 车辆id
     */
	private Long vehicleId;
    /**
     * 车牌号码
     */
	private String plateNo;
    /**
     * 车牌颜色
     */
	private String vehcolorId;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 更新时间
     */
	private Date updateTime;
    /**
     * 状态，1 未删除，0 禁用，-1 删除
     */
	private Integer status;
    /**
     * 客户id
     */
	private Long clientId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getVehcolorId() {
		return vehcolorId;
	}

	public void setVehcolorId(String vehcolorId) {
		this.vehcolorId = vehcolorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
