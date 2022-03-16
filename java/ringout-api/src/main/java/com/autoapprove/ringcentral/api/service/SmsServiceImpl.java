package com.autoapprove.ringcentral.api.service;

import com.autoapprove.ringcentral.api.model.SmsRequest;
import com.autoapprove.ringcentral.api.service.factory.RestClientFactory;
import com.ringcentral.RestClient;
import com.ringcentral.RestException;
import com.ringcentral.definitions.CreateSMSMessage;
import com.ringcentral.definitions.GetSMSMessageInfoResponse;
import com.ringcentral.definitions.GetServiceInfoResponse;
import com.ringcentral.definitions.MessageStoreCallerInfoRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static java.util.Arrays.asList;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
  private static final String SMS_IDENTIFIER = "SmsSender";

  private final RestClientFactory factory;

  public SmsServiceImpl(RestClientFactory factory) {
    this.factory = factory;
  }

  @Override
  public GetSMSMessageInfoResponse sendSms(SmsRequest data) {
    final var client = factory.createClient(data.getUserPhoneNumber());

    validateSenderNumber(client, data.getUserPhoneNumber());

    final var request = new CreateSMSMessage()
        .from(new MessageStoreCallerInfoRequest().phoneNumber(data.getUserPhoneNumber()))
        .to(new MessageStoreCallerInfoRequest[] {
            new MessageStoreCallerInfoRequest().phoneNumber(data.getTo())
        })
        .text(data.getText());

    try {
      final GetSMSMessageInfoResponse response = client.restapi()
          .account()
          .extension()
          .sms()
          .post(request);

      client.revoke();

      return response;
    } catch (RestException | IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to send sms message", e);
    }
  }

  public void validateSenderNumber(RestClient client, String phoneNumber) {
    try {
      final var phones = client.restapi()
          .account()
          .extension()
          .phoneNumber()
          .get()
          .records;

      for(final var phone : phones) {
        if (!phoneNumber.equals(phone.phoneNumber))
          continue;

        if (asList(phone.features).contains(SMS_IDENTIFIER))
          return;

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The provided phone doesn't have the permission to send SMS");
      }

      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The extension doesn't contain the phone number: " + phoneNumber);
    } catch (RestException | IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to fetch phoneNumbers for userPhoneNumber: " + phoneNumber, e);
    }
  }
}
