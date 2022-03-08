package com.autoapprove.ringout.api.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.definitions.GetRingOutStatusResponse;
import com.ringcentral.definitions.MakeRingOutCallerInfoRequestFrom;
import com.ringcentral.definitions.MakeRingOutCallerInfoRequestTo;
import com.ringcentral.definitions.MakeRingOutRequest;
import com.ringcentral.definitions.UserCallLogRecord;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallService {

    final RestClient client;

    public CallService(RestClient client) {
        this.client = client;
    }

    public GetRingOutStatusResponse call(String from, String to) {
        try {
            final var parameters = new MakeRingOutRequest();
            parameters.from(new MakeRingOutCallerInfoRequestFrom().phoneNumber(from));
            parameters.to(new MakeRingOutCallerInfoRequestTo().phoneNumber(to));
            parameters.playPrompt = true;

            return client.restapi().account().extension().ringOut().post(parameters);
        } catch (RestException | IOException e) {
            log.error("Error creating a call", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public List<UserCallLogRecord> getCallLogs() {
        try {
            return Arrays.asList(
                client.restapi()
                    .account()
                    .extension()
                    .callLog()
                    .list().records
            );

        } catch (RestException | IOException e) {
            log.error("Error getting call logs", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public GetRingOutStatusResponse getCallStatus(String id) {
        try {
            return client.restapi().account().extension().ringOut(id).get();
        } catch (RestException | IOException e) {
            log.error("Error getting call with id " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public CancellCallRes cancelCall(String id) {
        try {
            return new CancellCallRes(client.restapi().account().extension().ringOut(id).delete());
        } catch (RestException | IOException e) {
            log.error("Error cancelling a call with id " + id, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CancellCallRes {
        String message;
    }
}
