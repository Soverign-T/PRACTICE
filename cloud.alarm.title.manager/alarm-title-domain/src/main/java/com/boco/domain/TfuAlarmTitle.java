package com.boco.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TfuAlarmTitle {

  private Integer titleId;
  private Integer vendorId;
  private String title;
//  @DateTimeFormat("")
//  private Date insertTime;
  private Integer neType;
  private Integer vendorAlarmId;
  private Integer confirmed;
//  private Date timeStamp;




}
