package com.autoapprove.ringout.api.controller;

import java.util.List;
import java.util.UUID;

import com.autoapprove.ringout.api.service.SmsService;
import com.ringcentral.definitions.GetMessageInfoResponse;
import com.ringcentral.definitions.GetSMSMessageInfoResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/sms")
public class SmsController {
    final SmsService service;
    final UUID id = UUID.fromString("666e83e8-ae58-4fa6-9b98-29f7486ec7ca");

    public SmsController(SmsService service) {
        this.service = service;
    }

    @PostMapping(path = "")
    public GetSMSMessageInfoResponse send(@RequestBody SmsMessage message) {
        return service.send(message);
    }

    @GetMapping(path = "")
    public List<GetMessageInfoResponse> listAllMessages() {
        return service.receiveSms();
    }

    @GetMapping(path = "/{id}")
    public GetMessageInfoResponse getMessage(@PathVariable("id") String id) {
        return service.getSms(id);
    }

    @GetMapping(path = "/{id}/conversation")
    public SmsService.ConversationHolder getConversation(@PathVariable("id") String id) {
        return service.getConversation(id);
    }

    /*
    */
    @PostMapping(path = "/hook", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> hook(@RequestBody(required = false) String body,
        @RequestHeader(name = "Validation-Token", required = false) String validation) {
            
        if (body == null)
            return ResponseEntity.ok()
                .header("Validation-Token", validation)
                .body(id.toString());
        else
            log.info(body);

        return ResponseEntity.ok()
            .build();
    }

    @Data
    public static class SmsMessage {
        String to;
        String content;
    }
}
