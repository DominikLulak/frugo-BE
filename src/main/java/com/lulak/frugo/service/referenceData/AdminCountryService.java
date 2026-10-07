package com.lulak.frugo.service.referenceData;

import com.lulak.frugo.dto.referenceData.AdminCountryDto;
import com.lulak.frugo.repository.referenceData.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCountryService {

    private final CountryRepository countryRepository;

    public AdminCountryService(
            CountryRepository countryRepository
    ){
        this.countryRepository = countryRepository;
    }

    public List<AdminCountryDto> getAllCountries(){ return countryRepository.getAllCountries(); }
}
