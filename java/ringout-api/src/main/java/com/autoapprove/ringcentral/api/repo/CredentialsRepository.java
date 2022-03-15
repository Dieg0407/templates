package com.autoapprove.ringcentral.api.repo;

public interface CredentialsRepository {
  String getJwt(String extension);
}
