package io.devfactory;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SecurityController {

  @GetMapping("/")
  public String index(HttpSession session) {

    // ThreadLocal을 통한 SecurityContextHolder에서 가져오는 방법
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // HttpSession에서 가져오는 방법
    final SecurityContext context = (SecurityContext) session
        .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
    final Authentication sessionAuthentication = context.getAuthentication();

    log.debug("[dev] authentication == sessionAuthentication: {}",
        authentication == sessionAuthentication);
    return "index";
  }

  @GetMapping("/thread")
  public String thread() {
    // 자식 스레드 에서 메인 스레드 ThreadLocal 에 저장된 정보를 가져올 수 없음
    // 만야 공유가 가능하게 하려면 ThreadLocal Strategy 를 변경 해야 함
    new Thread(() -> {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      log.debug("[dev] child process authentication: {}", authentication);
    }).start();
    return "thread";
  }

  @GetMapping("/loginView")
  public String loginView() {
    return "loginView";
  }

  @GetMapping("/user")
  public String userView() {
    return "userView";
  }

  @GetMapping("/admin")
  public String adminView() {
    return "adminView";
  }

  @GetMapping("/denied")
  public String deniedView() {
    return "deniedView";
  }

}
