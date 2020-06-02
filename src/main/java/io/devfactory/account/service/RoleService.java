package io.devfactory.account.service;

import io.devfactory.account.domain.Role;
import io.devfactory.account.repository.RoleRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoleService {

  private final RoleRepository roleRepository;

  public Role findRoleById(Long roleId) {
    return roleRepository.findById(roleId).orElseThrow(EntityNotFoundException::new);
  }

  public List<Role> findRoles() {
    return roleRepository.findAll();
  }

  @Transactional
  public Role save(Role role) {
    final Role savedRole = roleRepository.save(role);
    return findRoleById(savedRole.getId());
  }

  @Transactional
  public void deleteById(Long roleId) {
    roleRepository.deleteById(roleId);
  }

}
