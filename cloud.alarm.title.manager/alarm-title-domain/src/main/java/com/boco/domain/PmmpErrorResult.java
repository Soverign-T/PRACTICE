package com.boco.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data 
@ToString
public class PmmpErrorResult {

  private String topoId;
  private String neId;
  private String neName;
  private String portId;
  private String portName;
  private String portType;
  private Double bandwidth;
  private Date todayTime;
  private Double todayInRate;
  private Double todayOutRate;
  private Date yesterdayTime;
  private Double yesterdayInRate;
  private Double yesterdayOutRate;
  private Double inEfficiency;
  private Double outEfficiency;
  private Double inTongbi;
  private Double outTongbi;
  private String alarmReason;


  public String getTopoId() {
    return topoId;
  }

  public void setTopoId(String topoId) {
    this.topoId = topoId;
  }


  public String getNeId() {
    return neId;
  }

  public void setNeId(String neId) {
    this.neId = neId;
  }


  public String getNeName() {
    return neName;
  }

  public void setNeName(String neName) {
    this.neName = neName;
  }


  public String getPortId() {
    return portId;
  }

  public void setPortId(String portId) {
    this.portId = portId;
  }


  public String getPortName() {
    return portName;
  }

  public void setPortName(String portName) {
    this.portName = portName;
  }


  public String getPortType() {
    return portType;
  }

  public void setPortType(String portType) {
    this.portType = portType;
  }


  public Double getBandwidth() {
    return bandwidth;
  }

  public void setBandwidth(Double bandwidth) {
    this.bandwidth = bandwidth;
  }


  public Date getTodayTime() {
    return todayTime;
  }

  public void setTodayTime(java.sql.Date todayTime) {
    this.todayTime = todayTime;
  }


  public Double getTodayInRate() {
    return todayInRate;
  }

  public void setTodayInRate(Double todayInRate) {
    this.todayInRate = todayInRate;
  }


  public Double getTodayOutRate() {
    return todayOutRate;
  }

  public void setTodayOutRate(Double todayOutRate) {
    this.todayOutRate = todayOutRate;
  }


  public Date getYesterdayTime() {
    return yesterdayTime;
  }

  public void setYesterdayTime(java.sql.Date yesterdayTime) {
    this.yesterdayTime = yesterdayTime;
  }


  public Double getYesterdayInRate() {
    return yesterdayInRate;
  }

  public void setYesterdayInRate(Double yesterdayInRate) {
    this.yesterdayInRate = yesterdayInRate;
  }


  public Double getYesterdayOutRate() {
    return yesterdayOutRate;
  }

  public void setYesterdayOutRate(Double yesterdayOutRate) {
    this.yesterdayOutRate = yesterdayOutRate;
  }


  public Double getInEfficiency() {
    return inEfficiency;
  }

  public void setInEfficiency(Double inEfficiency) {
    this.inEfficiency = inEfficiency;
  }


  public Double getOutEfficiency() {
    return outEfficiency;
  }

  public void setOutEfficiency(Double outEfficiency) {
    this.outEfficiency = outEfficiency;
  }


  public Double getInTongbi() {
    return inTongbi;
  }

  public void setInTongbi(Double inTongbi) {
    this.inTongbi = inTongbi;
  }


  public Double getOutTongbi() {
    return outTongbi;
  }

  public void setOutTongbi(Double outTongbi) {
    this.outTongbi = outTongbi;
  }


  public String getAlarmReason() {
    return alarmReason;
  }

  public void setAlarmReason(String alarmReason) {
    this.alarmReason = alarmReason;
  }

}
