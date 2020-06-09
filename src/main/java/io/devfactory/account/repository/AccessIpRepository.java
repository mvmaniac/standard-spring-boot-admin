package io.devfactory.account.repository;

import io.devfactory.account.domain.AccessIp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccessIpRepository extends JpaRepository<AccessIp, Long> {

  Optional<AccessIp> findByIpAddress(String ipAddress);

}
