package com.mydao.nsop.client.domain.entity;

import java.util.Date;

public class PayEntryRec {
    private String recid;

    private String farePlateno;

    private String farePlatecolor;

    private Date entrytime;

    private String netno;

    private String siteno;

    private String laneno;

    private String lprPlateno;

    private String lprPlatecolor;

    private String camerano;

    private Short upflag;

    private Integer transno;

    private String vehclass;

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid == null ? null : recid.trim();
    }

    public String getFarePlateno() {
        return farePlateno;
    }

    public void setFarePlateno(String farePlateno) {
        this.farePlateno = farePlateno == null ? null : farePlateno.trim();
    }

    public String getFarePlatecolor() {
        return farePlatecolor;
    }

    public void setFarePlatecolor(String farePlatecolor) {
        this.farePlatecolor = farePlatecolor == null ? null : farePlatecolor.trim();
    }

    public Date getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
    }

    public String getNetno() {
        return netno;
    }

    public void setNetno(String netno) {
        this.netno = netno == null ? null : netno.trim();
    }

    public String getSiteno() {
        return siteno;
    }

    public void setSiteno(String siteno) {
        this.siteno = siteno == null ? null : siteno.trim();
    }

    public String getLaneno() {
        return laneno;
    }

    public void setLaneno(String laneno) {
        this.laneno = laneno == null ? null : laneno.trim();
    }

    public String getLprPlateno() {
        return lprPlateno;
    }

    public void setLprPlateno(String lprPlateno) {
        this.lprPlateno = lprPlateno == null ? null : lprPlateno.trim();
    }

    public String getLprPlatecolor() {
        return lprPlatecolor;
    }

    public void setLprPlatecolor(String lprPlatecolor) {
        this.lprPlatecolor = lprPlatecolor == null ? null : lprPlatecolor.trim();
    }

    public String getCamerano() {
        return camerano;
    }

    public void setCamerano(String camerano) {
        this.camerano = camerano == null ? null : camerano.trim();
    }

    public Short getUpflag() {
        return upflag;
    }

    public void setUpflag(Short upflag) {
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
        this.vehclass = vehclass == null ? null : vehclass.trim();
    }
}