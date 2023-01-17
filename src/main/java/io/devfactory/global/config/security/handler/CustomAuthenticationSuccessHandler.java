package io.devfactory.global.config.security.handler;

import java.io.IOException;
import java.util.Objects;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final RequestCache requestCache = new HttpSessionRequestCache();
  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    final SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (Objects.nonNull(savedRequest)) {
      final String redirectUrl = savedRequest.getRedirectUrl();
      redirectStrategy.sendRedirect(request, response, redirectUrl);
      return;
    }

    setDefaultTargetUrl("/");
    redirectStrategy.sendRedirect(request, response, getDefaultTargetUrl());
  }

}
