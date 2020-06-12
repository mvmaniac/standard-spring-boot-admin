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

  public List<Role> findRoleAll() {
    return roleRepository.findAll();
  }

  @Transactional
  public Role saveRole(Role role) {
    final Role savedRole = roleRepository.save(role);
    return findRoleById(savedRole.getId());
  }

  @Transactional
  public void updateRole(Role role) {
    final Role findRole = findRoleById(role.getId());
    findRole.update(role);
  }

  @Transactional
  public void deleteRoleById(Long roleId) {
    roleRepository.deleteById(roleId);
  }

}
