package io.devfactory.global.config.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    String errorMessage;

    if (exception instanceof BadCredentialsException) {
      errorMessage = "Invalid Username or Password...";
    } else if (exception instanceof InsufficientAuthenticationException) {
      errorMessage = "Invalid Secret Key...";
    } else {
      errorMessage = exception.getMessage();
    }

    // 여러가지 방법이 있는데 일단은 foward로 처리 함
    // forward 이기 떄문에 상단 URL 로 변하지 않지만 controller 에서 post로 받아야 함
    request.setAttribute("errorMessage", errorMessage);
    request.getRequestDispatcher("/sign-in/form?error").forward(request, response);
  }

}
