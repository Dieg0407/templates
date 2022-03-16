package com.autoapprove.ringcentral.api.controller;

import com.autoapprove.ringcentral.api.model.SmsRequest;
import com.autoapprove.ringcentral.api.service.SmsService;
import com.ringcentral.definitions.GetSMSMessageInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/sms")
public class SmsController {
    final SmsService service;

    public SmsController(SmsService service) {
        this.service = service;
    }

    @PostMapping(path = "")
    public GetSMSMessageInfoResponse sendSms(@RequestBody SmsRequest request) {
        return service.sendSms(request);
    }

    @PostMapping(path = "/hook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hook(@RequestBody(required = false) String body,
        @RequestHeader(name = "Validation-Token", required = false) String validation) {
            
        if (body == null)
            return ResponseEntity.ok()
                .header("Validation-Token", validation)
                .build();
        else
            log.info(body);

        return ResponseEntity.ok()
            .build();
    }
}
