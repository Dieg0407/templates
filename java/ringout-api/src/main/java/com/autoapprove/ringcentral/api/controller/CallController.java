package com.autoapprove.ringcentral.api.controller;

import com.autoapprove.ringcentral.api.model.CallRequest;
import com.autoapprove.ringcentral.api.service.CallOutService;

import com.ringcentral.definitions.CallSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/call")
public class CallController {
    final CallOutService service;

    public CallController(CallOutService service) {
        this.service = service;
    }

    @CrossOrigin("https://1a37-38-25-17-223.ngrok.io/")
    @PostMapping(path = "")
    public CallSession call(
        @RequestBody CallRequest request,
        @RequestHeader(name = "x-ringcentral-token", required = false) String ringCentralToken) {

        if (ringCentralToken != null)
            return service.call(request, ringCentralToken);

        return service.call(request);
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
