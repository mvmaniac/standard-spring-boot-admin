package io.devfactory.account.repository;

import io.devfactory.account.domain.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

  @EntityGraph(attributePaths = {"role"})
  @Override
  List<Resource> findAll(Sort sort);

  @EntityGraph(attributePaths = {"role"})
  @Override
  Optional<Resource> findById(Long aLong);

}
