package io.devfactory.global.common.runner;

import io.devfactory.global.config.security.metadatasource.UrlFilterInvocationSecurityMedataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitializeRunner implements ApplicationRunner {

  private final UrlFilterInvocationSecurityMedataSource urlFilterInvocationSecurityMedataSource;

  @Override
  public void run(ApplicationArguments args) {
    // TODO: ddl-auto 를 create 해서 할 경우 SecurityMetadataSource 에서 데이터 싱크가 안맞는 문제가 있어 임시방편 해결법...
    urlFilterInvocationSecurityMedataSource.reload();
  }

}
