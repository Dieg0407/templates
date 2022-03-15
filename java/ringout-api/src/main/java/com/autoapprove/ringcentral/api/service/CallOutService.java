package com.autoapprove.ringcentral.api.service;

import com.autoapprove.ringcentral.api.model.CallRequest;
import com.ringcentral.definitions.CallSession;

public interface CallOutService {

  CallSession call(CallRequest data);
}
