package io.devfactory.global.config.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 인가처리를 AbstractSecurityInterceptor 에게 맡겨도 되는데
// 미리 고정된 정보를 가지고 인가처리를 안타도 되도록 확인 해서 null로 리턴
// 해당 필터를 하지 않아도 AbstractSecurityInterceptor 를 ConfigAttribute 값이 없으면 통과됨
public class PermitAllFilter extends FilterSecurityInterceptor {

  private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";

  @SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
  private boolean observeOncePerRequest = true;

  private final List<AntPathRequestMatcher> permitAllRequestMatchers;

  public PermitAllFilter(String... permitAllResources) {
    permitAllRequestMatchers = Arrays.stream(permitAllResources)
        .map(AntPathRequestMatcher::new)
        .toList()
    ;
  }

  @Override
  protected InterceptorStatusToken beforeInvocation(Object object) {
    HttpServletRequest request = ((FilterInvocation) object).getRequest();

    boolean isPermitAll = permitAllRequestMatchers.stream()
        .anyMatch(matcher -> matcher.matches(request));

    if (isPermitAll) {
      return null;
    }

    return super.beforeInvocation(object);
  }

  @Override
  public void invoke(FilterInvocation fi) throws IOException, ServletException {
    if ((fi.getRequest() != null)
        && (fi.getRequest().getAttribute(FILTER_APPLIED) != null)
        && observeOncePerRequest) {
      // filter already applied to this request and user wants us to observe
      // once-per-request handling, so don't re-do security checking
      fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
    } else {
      // first time this request being called, so perform security checking
      if (fi.getRequest() != null && observeOncePerRequest) {
        fi.getRequest().setAttribute(FILTER_APPLIED, Boolean.TRUE);
      }

      InterceptorStatusToken token = beforeInvocation(fi);

      try {
        fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
      } finally {
        super.finallyInvocation(token);
      }

      super.afterInvocation(token, null);
    }
  }
}
