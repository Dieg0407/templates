package com.autoapprove.ringcentral.api.service;

import com.autoapprove.ringcentral.api.model.SmsRequest;
import com.ringcentral.definitions.GetSMSMessageInfoResponse;

public interface SmsService {
  GetSMSMessageInfoResponse sendSms(SmsRequest data);
}
