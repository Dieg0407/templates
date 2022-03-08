package com.autoapprove.ringout.api.service;

import com.autoapprove.ringout.api.controller.SmsController;
import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.definitions.CreateSMSMessage;
import com.ringcentral.definitions.GetMessageInfoResponse;
import com.ringcentral.definitions.GetSMSMessageInfoResponse;
import com.ringcentral.definitions.ListMessagesParameters;
import com.ringcentral.definitions.MessageStoreCallerInfoRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class SmsService {
    final RestClient restClient;

    public SmsService(RestClient restClient) {
        this.restClient = restClient;
    }

    public GetSMSMessageInfoResponse send(SmsController.SmsMessage message) {
        try {
            final var sender = Stream.of(restClient.restapi()
                    .account()
                    .extension()
                    .phoneNumber()
                    .get().records).filter(r -> Arrays.asList(r.features).contains("SmsSender"))
                    .findFirst();

            if (sender.isEmpty()) {
                log.error("Sms sender not found");
                return null;
            }

            final var smsMessage = new CreateSMSMessage();
            smsMessage.from = new MessageStoreCallerInfoRequest().phoneNumber(sender.get().phoneNumber);
            smsMessage.to = new MessageStoreCallerInfoRequest[] {
                    new MessageStoreCallerInfoRequest().phoneNumber(message.getTo())
            };
            smsMessage.text = message.getContent();
            return restClient.restapi()
                    .account()
                    .extension()
                    .sms()
                    .post(smsMessage);

        } catch (Exception e) {
            log.error("Error", e);
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    public List<GetMessageInfoResponse> receiveSms() {
        try {
            final var parameters = new ListMessagesParameters();
            parameters.messageType = new String[] { "SMS" };
            parameters.conversationId = Long.parseLong("5004555331160293194") ;

            final var messages = restClient.restapi()
                .account()
                .extension()
                .messageStore()
                .list(parameters);

            return Arrays.asList(messages.records);
        } catch (RestException | IOException e) {
            log.error("Error fetching sms messages", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public GetMessageInfoResponse getSms(String smsId) {
        try {
            final var parameters = new ListMessagesParameters();
            parameters.messageType = new String[] { "SMS" };

            return restClient.restapi()
                .account()
                .extension()
                .messageStore(smsId)
                .get();
        } catch (RestException | IOException e) {
            log.error("Error fetching the sms message with id " + smsId, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public ConversationHolder getConversation(String smsId) {
        final var sms = getSms(smsId);
        try(final var res = restClient.get("/restapi/v1.0/account/~/extension/~/message-store/" + smsId + "/content/" + sms.attachments[0].id)){
            final var str = new String(res.bytes());

            return new ConversationHolder(str);
        } catch (RestException | IOException e) {
            log.error("Error fetching sms messages", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConversationHolder {
        private String data;   
    }
}
