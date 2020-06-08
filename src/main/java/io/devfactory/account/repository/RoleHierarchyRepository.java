package io.devfactory.account.repository;


import io.devfactory.account.domain.RoleHierarchy;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {

  @EntityGraph(attributePaths = {"childRole", "parentRole"})
  @Query("select rh from RoleHierarchy rh order by rh.parentRole.id nulls first")
  List<RoleHierarchy> findRoleHierarchies();

}
