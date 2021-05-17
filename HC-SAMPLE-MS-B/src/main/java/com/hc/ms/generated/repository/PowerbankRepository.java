package com.hc.ms.generated.repository;

import com.hc.ms.generated.domain.Powerbank;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Powerbank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PowerbankRepository extends JpaRepository<Powerbank, Long>, JpaSpecificationExecutor<Powerbank> {
}
