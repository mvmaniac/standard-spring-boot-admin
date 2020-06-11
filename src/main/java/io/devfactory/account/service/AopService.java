package io.devfactory.account.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AopService {

  public void methodSecured() {
    log.debug("[dev] methodSecured...");
  }

  public void pointcutSecured() {
    log.debug("[dev] pointcutSecured...");
  }

  public void notSecured() {
    log.debug("[dev] notSecured...");
  }

}
