package com.droar.hs.command.infrastructure.repository;

import com.droar.hs.command.infrastructure.repository.entities.HotelSearchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelSearchPostgresDataRepository extends JpaRepository<HotelSearchJpaEntity, String> {

}