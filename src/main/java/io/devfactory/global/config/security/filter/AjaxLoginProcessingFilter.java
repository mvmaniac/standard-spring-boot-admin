package io.devfactory.global.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.devfactory.account.dto.request.LoginRequestView;
import io.devfactory.global.config.security.token.AjaxAuthenticationToken;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper;

  public AjaxLoginProcessingFilter() {
    super(new AntPathRequestMatcher("/api/login"));
    objectMapper = new ObjectMapper();
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    if (!isAjax(request)) {
      throw new AuthenticationServiceException("Authentication is not supported...");
    }

    LoginRequestView requestView = objectMapper
        .readValue(request.getReader(), LoginRequestView.class);

    final String username = requestView.getUsername();
    final String password = requestView.getPassword();

    if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
      throw new UsernameNotFoundException("username or password is empty...");
    }

    return getAuthenticationManager().authenticate(new AjaxAuthenticationToken(username, password));
  }

  private boolean isAjax(HttpServletRequest request) {
    return "XMLHttpRequest".equals(request.getHeader("x-requested-with"));
  }

}
