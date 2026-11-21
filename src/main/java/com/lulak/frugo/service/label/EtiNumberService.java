package com.lulak.frugo.service.label;

import com.lulak.frugo.model.EtiSequence;
import com.lulak.frugo.repository.referenceData.EtiSequenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EtiNumberService {

    private static final String WAREHOUSE_ITEM_SEQUENCE = "WAREHOUSE_ITEM";
    private static final String PALLET_SEQUENCE = "PALLET";

    private static final String ETI_PREFIX = "ETI";
    private static final int MIN_NUMBER_LENGTH = 4;

    private final EtiSequenceRepository etiSequenceRepository;

    public EtiNumberService(EtiSequenceRepository etiSequenceRepository){
        this.etiSequenceRepository = etiSequenceRepository;
    }

    @Transactional
    public String generateWarehouseItemEti(){
        return generateEti(WAREHOUSE_ITEM_SEQUENCE);
    }

    @Transactional
    public String generatePalletEti(){
        return generateEti(PALLET_SEQUENCE);
    }

    private String generateEti(String sequenceCode){
        EtiSequence sequence = etiSequenceRepository
                .findByCodeForUpdate(sequenceCode)
                .orElseThrow(() -> new IllegalStateException(
                        "ETI sequence not found " + sequenceCode
                ));

        if(sequence.getLastNumber() == Integer.MAX_VALUE){
            throw new IllegalStateException(
                    "ETI sequence has reached the maximum value " + sequenceCode
            );
        }

        int nextNumber = sequence.getLastNumber() + 1;

        sequence.setLastNumber(nextNumber);
        etiSequenceRepository.save(sequence);

        return formatEti(nextNumber);
    }

    private String formatEti(int number){
        return ETI_PREFIX + String.format(
                "%0" + MIN_NUMBER_LENGTH + "d",
                number
        );
    }
}
