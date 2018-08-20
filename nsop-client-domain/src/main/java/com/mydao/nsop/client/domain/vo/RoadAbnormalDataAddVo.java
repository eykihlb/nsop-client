package com.mydao.nsop.client.domain.vo;

import java.io.Serializable;
import java.util.Date;

public class RoadAbnormalDataAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 路网号+站号+车道编码_通行时间（UTC）
     */
    private String recCode;
    /**
     * 计费车牌号
     */
    private String farePlateno;
    /**
     * 计费车牌颜色
     */
    private String farePlatecolor;
    /**
     * 驶入时间
     */
    private Date entryTime;
    /**
     * 路网号
     */
    private String entno;
    /**
     * 收费站号
     */
    private String siteno;
    /**
     * 车道号
     */
    private String laneno;
    /**
     * 识别车牌号
     */
    private String lprPlateno;
    /**
     * 识别车牌颜色
     */
    private String lprPlatecolor;
    /**
     * 摄像机编号
     */
    private String camerano;
    /**
     * 上传标识 0-未上传 1-已上传
     */
    private Integer upflag;
    /**
     * 交易序号
     */
    private Integer transno;
    /**
     * 收费车型 01-客一 02-客二 03-客三 04-客四 05-货一 06-货二 07-货三 08-货四 09-货五
     */
    private String vehclass;
    /**
     * 驶出时间
     */
    private Date exitTime;
    /**
     * 驶入记录编码
     */
    private String entryRecid;
    /**
     * 金额
     */
    private String fareTotal;
    /**
     * 通行类型 1-驶入 2-驶出
     */
    private Integer type;

    public String getRecCode() {
        return recCode;
    }

    public void setRecCode(String recCode) {
        this.recCode = recCode;
    }

    public String getFarePlateno() {
        return farePlateno;
    }

    public void setFarePlateno(String farePlateno) {
        this.farePlateno = farePlateno;
    }

    public String getFarePlatecolor() {
        return farePlatecolor;
    }

    public void setFarePlatecolor(String farePlatecolor) {
        this.farePlatecolor = farePlatecolor;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getEntno() {
        return entno;
    }

    public void setEntno(String entno) {
        this.entno = entno;
    }

    public String getSiteno() {
        return siteno;
    }

    public void setSiteno(String siteno) {
        this.siteno = siteno;
    }

    public String getLaneno() {
        return laneno;
    }

    public void setLaneno(String laneno) {
        this.laneno = laneno;
    }

    public String getLprPlateno() {
        return lprPlateno;
    }

    public void setLprPlateno(String lprPlateno) {
        this.lprPlateno = lprPlateno;
    }

    public String getLprPlatecolor() {
        return lprPlatecolor;
    }

    public void setLprPlatecolor(String lprPlatecolor) {
        this.lprPlatecolor = lprPlatecolor;
    }

    public String getCamerano() {
        return camerano;
    }

    public void setCamerano(String camerano) {
        this.camerano = camerano;
    }

    public Integer getUpflag() {
        return upflag;
    }

    public void setUpflag(Integer upflag) {
        this.upflag = upflag;
    }

    public Integer getTransno() {
        return transno;
    }

    public void setTransno(Integer transno) {
        this.transno = transno;
    }

    public String getVehclass() {
        return vehclass;
    }

    public void setVehclass(String vehclass) {
        this.vehclass = vehclass;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public String getEntryRecid() {
        return entryRecid;
    }

    public void setEntryRecid(String entryRecid) {
        this.entryRecid = entryRecid;
    }

    public String getFareTotal() {
        return fareTotal;
    }

    public void setFareTotal(String fareTotal) {
        this.fareTotal = fareTotal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
