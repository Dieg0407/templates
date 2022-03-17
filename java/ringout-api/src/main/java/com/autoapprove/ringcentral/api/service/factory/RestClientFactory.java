package com.autoapprove.ringcentral.api.service.factory;

import com.ringcentral.RestClient;

public interface RestClientFactory {
  RestClient createClient(String extension);
  RestClient createClient(String extension, String authToken);
}
