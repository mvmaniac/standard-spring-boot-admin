package io.devfactory.account.controller.admin;

import io.devfactory.account.domain.Resource;
import io.devfactory.account.domain.Role;
import io.devfactory.account.dto.response.ResourceResponseView;
import io.devfactory.account.dto.response.ResourcesResponseView;
import io.devfactory.account.dto.response.RoleResponseView;
import io.devfactory.account.mapper.ResourceMapper;
import io.devfactory.account.mapper.RoleMapper;
import io.devfactory.account.service.ResourceService;
import java.util.List;
import io.devfactory.account.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin/resources")
@Controller
public class ResourceManageController {

  private final ResourceService resourceService;
  private final RoleService roleService;

  @GetMapping
  public String viewResources(Model model) {
    final List<Resource> resources = resourceService.findResources();
    final List<ResourceResponseView> resourceResponseViews = ResourceMapper.INSTANCE
        .toResourceResponseViewList(resources);

    model.addAttribute("result", ResourcesResponseView.of(resourceResponseViews));
    return "views/admin/resource/list";
  }

  @GetMapping("/form")
  public String viewResourceForm() {
    return "views/admin/resource/form";
  }

  @GetMapping("/form/{id}")
  public String viewResourceForm(@PathVariable("id") Long resourceId, Model model) {
    final Resource resource = resourceService.findResourceById(resourceId);
    final ResourceResponseView resourceResponseView = ResourceMapper.INSTANCE
        .toResourceResponseView(resource);

    final List<Role> roles = roleService.findRoles();
    final List<RoleResponseView> rolesResponseView = RoleMapper.INSTANCE
        .toRoleResponseViewList(roles);

    model.addAttribute("resource", resourceResponseView);
    model.addAttribute("roles", rolesResponseView);
    return "views/admin/resource/form";
  }

}
