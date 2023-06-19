package com.droar.hs.command.infrastructure.repository;

import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.domain.repository.HotelSearchDomainRepository;
import com.droar.hs.command.infrastructure.repository.mapper.HotelSearchJpaMapper;
import org.springframework.stereotype.Repository;

@Repository
public class HotelSearchRepository implements HotelSearchDomainRepository {

    private final HotelSearchPostgresDataRepository dataRepository;
    private final HotelSearchJpaMapper mapper;

    public HotelSearchRepository(final HotelSearchPostgresDataRepository dataRepository,
                                 final HotelSearchJpaMapper hotelSearchJpaEntity) {
        this.dataRepository = dataRepository;
        this.mapper = hotelSearchJpaEntity;
    }

    @Override
    public void save(final HotelSearch hotelSearch) {
        this.mapper.toJpaEntity(hotelSearch).ifPresent(this.dataRepository::save);
    }
}