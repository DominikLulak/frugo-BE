package com.lulak.frugo.repository.referenceData;

import com.lulak.frugo.dto.referenceData.AdminCountryDto;
import com.lulak.frugo.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("""
        SELECT new com.lulak.frugo.dto.referenceData.AdminCountryDto(
            c.id,
            c.code,
            c.name
        )
        FROM Country c
        ORDER BY c.id
    """)
    List<AdminCountryDto> getAllCountries();
}
