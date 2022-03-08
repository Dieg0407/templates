package com.autoapprove.ringout.api.controller;

import java.util.List;

import com.autoapprove.ringout.api.service.CallService;
import com.ringcentral.definitions.GetRingOutStatusResponse;
import com.ringcentral.definitions.UserCallLogRecord;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/call")
public class CallController {
    final CallService service;

    public CallController(CallService service) {
        this.service = service;
    }
    
    @PostMapping(path = "")
    public GetRingOutStatusResponse send(@RequestBody CallMessage message) {
        return service.call(message.getFrom(), message.getTo());
    }

    @GetMapping(path = "/{id}")
    public GetRingOutStatusResponse getCallStatus(@PathVariable("id") String id) {
        return service.getCallStatus(id);
    }

    @GetMapping(path = "")
    public List<UserCallLogRecord> getCallLogs() {
        return service.getCallLogs();
    }

    @DeleteMapping(path = "/{id}")
    public CallService.CancellCallRes cancellCallStatus(@PathVariable("id") String id) {
        return service.cancelCall(id);
    }
    /*
    */
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CallMessage {
        private String to;
        private String from;
    }
}
