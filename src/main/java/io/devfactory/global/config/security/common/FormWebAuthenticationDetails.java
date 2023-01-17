package io.devfactory.global.config.security.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

  private final String secretKey;

  public FormWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
    secretKey = request.getParameter("secretKey");
  }

}
