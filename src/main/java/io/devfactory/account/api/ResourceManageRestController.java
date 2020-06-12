package io.devfactory.account.api;

import io.devfactory.account.domain.Resource;
import io.devfactory.account.dto.request.ResourceRequestView;
import io.devfactory.account.dto.response.ResourceResponseView;
import io.devfactory.account.mapper.ResourceMapper;
import io.devfactory.account.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/admin/resources")
@RestController
public class ResourceManageRestController {

  private final ResourceService resourceService;

  @PostMapping
  public ResponseEntity<ResourceResponseView> createResource(@RequestBody ResourceRequestView view) {
    final Resource resource = ResourceMapper.INSTANCE.toResource(view);
    final Resource savedResource = resourceService.saveResource(resource);
    return ResponseEntity.ok(ResourceMapper.INSTANCE.toResourceResponseView(savedResource));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Boolean> modifyResource(@PathVariable("id") Long resourceId,
      @RequestBody ResourceRequestView view) {
    final Resource resource = ResourceMapper.INSTANCE.toResource(resourceId, view);
    resourceService.updateResource(resource);
    return ResponseEntity.ok(true);
  }

  @DeleteMapping
  public ResponseEntity<Boolean> removeResource(@RequestParam("id") Long resourceId) {
    resourceService.deleteResourceById(resourceId);
    return ResponseEntity.ok(true);
  }

}
