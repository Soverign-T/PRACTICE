package com.boco.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TfuAlarmTitle {

  private String titleId;
  private String vendorId;
  private String title;
  private java.util.Date insertTime;
  private String neType;
  private String vendorAlarmId;
  private String confirmed;
  private Long timeStamp;


  public String getTitleId() {
    return titleId;
  }

  public void setTitleId(String titleId) {
    this.titleId = titleId;
  }


  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public java.util.Date getInsertTime() {
    return insertTime;
  }

  public void setInsertTime(java.util.Date insertTime) {
    this.insertTime = insertTime;
  }


  public String getNeType() {
    return neType;
  }

  public void setNeType(String neType) {
    this.neType = neType;
  }


  public String getVendorAlarmId() {
    return vendorAlarmId;
  }

  public void setVendorAlarmId(String vendorAlarmId) {
    this.vendorAlarmId = vendorAlarmId;
  }


  public String getConfirmed() {
    return confirmed;
  }

  public void setConfirmed(String confirmed) {
    this.confirmed = confirmed;
  }



}
