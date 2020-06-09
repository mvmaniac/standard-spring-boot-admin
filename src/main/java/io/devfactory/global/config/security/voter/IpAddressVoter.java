package io.devfactory.global.config.security.voter;

import io.devfactory.global.config.security.service.ResourceMappingConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class IpAddressVoter implements AccessDecisionVoter<Object> {

  private final ResourceMappingConfigService resourceMappingConfigService;

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public int vote(Authentication authentication, Object object,
      Collection<ConfigAttribute> attributes) {

    final WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
    final String remoteAddress = details.getRemoteAddress();

    final List<String> accessIpAddress = resourceMappingConfigService.getAccessIpAddress();
    final boolean isNoneMatch = accessIpAddress.stream()
        .noneMatch(ipAddress -> ipAddress.equals(remoteAddress));

    if (isNoneMatch) {
      throw new AccessDeniedException("Invalid IpAddress...");
    }

    return ACCESS_ABSTAIN;
  }

}
