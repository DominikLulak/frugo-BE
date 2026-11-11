package com.lulak.frugo.service.referenceData.CRUD;

import com.lulak.frugo.dto.referenceData.CRUD.CountryCreateDto;
import com.lulak.frugo.model.Country;
import com.lulak.frugo.repository.referenceData.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CountryCrudService {

    private final CountryRepository countryRepository;

    public CountryCrudService(
            CountryRepository countryRepository
    ){
        this.countryRepository = countryRepository;
    }

    @Transactional
    public Country createCountry(
            CountryCreateDto dto
    ){
        Country country = new Country();

        country.setCode(dto.getCode());
        country.setName(dto.getName());

        country = countryRepository.save(country);
        return country;
    }

    @Transactional
    public Country updateCountry(
            Integer countryId,
            CountryCreateDto dto
    ){
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found " + countryId));

        country.setCode(dto.getCode());
        country.setName(dto.getName());

        return countryRepository.save(country);
    }
}
