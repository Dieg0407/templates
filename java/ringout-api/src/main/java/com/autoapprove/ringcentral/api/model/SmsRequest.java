package com.autoapprove.ringcentral.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
  private String to;
  private String userPhoneNumber;
  private String text;
}
