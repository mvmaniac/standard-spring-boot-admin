package io.devfactory.global.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.devfactory.account.domain.Account;
import io.devfactory.account.mapper.AccountMapper;
import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    final Account account = (Account) authentication.getPrincipal();

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    objectMapper
        .writeValue(response.getWriter(), AccountMapper.INSTANCE.toLoginResponseView(account));
  }

}
