package io.devfactory.account.service;

import static org.springframework.data.domain.Sort.Direction.ASC;

import io.devfactory.account.domain.Resource;
import io.devfactory.account.repository.ResourceRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ResourceService {

  private final ResourceRepository resourceRepository;

  public Resource findResourceById(Long resourceId) {
    return resourceRepository.findById(resourceId).orElseThrow(EntityNotFoundException::new);
  }

  public List<Resource> findResources() {
    return resourceRepository.findAll(Sort.by(ASC, "id"));
  }

  @Transactional
  public Resource saveResource(Resource resource) {
    final Resource savedResource = resourceRepository.save(resource);
    return findResourceById(savedResource.getId());
  }

  @Transactional
  public void modifyResource(Resource resource) {
    final Resource findResource = findResourceById(resource.getId());
    findResource.changeResource(resource);
  }

  @Transactional
  public void deleteResourceById(Long resourceId) {
    resourceRepository.deleteById(resourceId);
  }

}
