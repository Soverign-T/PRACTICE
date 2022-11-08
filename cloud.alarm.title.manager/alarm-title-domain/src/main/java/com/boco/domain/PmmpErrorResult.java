package com.boco.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.sql.Timestamp;
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
  @DateTimeFormat(pattern = "yyyy-MM-dd HH24:mm:ss")
  private Timestamp todayTime;
  private Double todayInRate;
  private Double todayOutRate;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH24:mm:ss")
  private Timestamp yesterdayTime;
  private Double yesterdayInRate;
  private Double yesterdayOutRate;
  private Double inEfficiency;
  private Double outEfficiency;
  private Double inTongbi;
  private Double outTongbi;
  private String alarmReason;




}
