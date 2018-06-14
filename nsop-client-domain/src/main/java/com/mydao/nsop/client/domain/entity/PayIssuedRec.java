package com.mydao.nsop.client.domain.entity;

import java.util.Date;

public class PayIssuedRec {
    private String recid;

    private String plateno;

    private String platecolor;

    private Date entrytime;

    private String netno;

    private String siteno;

    private String laneno;

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

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno == null ? null : plateno.trim();
    }

    public String getPlatecolor() {
        return platecolor;
    }

    public void setPlatecolor(String platecolor) {
        this.platecolor = platecolor == null ? null : platecolor.trim();
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

    public String getVehclass() {
        return vehclass;
    }

    public void setVehclass(String vehclass) {
        this.vehclass = vehclass == null ? null : vehclass.trim();
    }
}