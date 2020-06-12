package io.devfactory.account.service;

import io.devfactory.account.domain.Resource;
import io.devfactory.account.repository.ResourceRepository;
import io.devfactory.global.config.security.metadatasource.UrlFilterInvocationSecurityMedataSource;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ResourceService {

  private final ResourceRepository resourceRepository;

  // TODO: 인가처리 실시간 반영을 하는 더 좋은 방법...그나마 aop 적용?
  private final UrlFilterInvocationSecurityMedataSource urlFilterInvocationSecurityMedataSource;

  public Resource findResourceById(Long resourceId) {
    return resourceRepository.findResourceById(resourceId)
        .orElseThrow(EntityNotFoundException::new);
  }

  public List<Resource> findResourceAll() {
    return resourceRepository.findResourceAll();
  }

  @Transactional
  public Resource saveResource(Resource resource) {
    final Resource savedResource = resourceRepository.save(resource);

    urlFilterInvocationSecurityMedataSource.reload();
    return findResourceById(savedResource.getId());
  }

  @Transactional
  public void updateResource(Resource resource) {
    final Resource findResource = findResourceById(resource.getId());
    findResource.update(resource);

    urlFilterInvocationSecurityMedataSource.reload();
  }

  @Transactional
  public void deleteResourceById(Long resourceId) {
    resourceRepository.deleteById(resourceId);

    urlFilterInvocationSecurityMedataSource.reload();
  }

}
