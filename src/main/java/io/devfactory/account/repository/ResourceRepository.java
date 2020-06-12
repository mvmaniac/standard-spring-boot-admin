package io.devfactory.account.repository;

import io.devfactory.account.domain.Resource;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

  @Query("select re from Resource re join fetch re.role ro order by re.id asc")
  List<Resource> findResourceAll();

  @Query("select re from Resource re join fetch re.role ro where re.type = :type order by re.orderNo desc")
  List<Resource> findResourcesByType(String type);

  @EntityGraph(attributePaths = {"role"})
  Optional<Resource> findResourceById(Long resourceId);

}
