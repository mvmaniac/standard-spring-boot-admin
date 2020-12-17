package io.devfactory.global.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException {

    String errorMessage;

    if (exception instanceof BadCredentialsException) {
      errorMessage = "Invalid Username or Password...";
    } else {
      errorMessage = exception.getMessage();
    }

    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    objectMapper.writeValue(response.getWriter(), errorMessage);
  }

}
