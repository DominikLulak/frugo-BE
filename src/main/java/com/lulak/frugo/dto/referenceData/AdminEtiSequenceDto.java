package com.lulak.frugo.dto.referenceData;

public class AdminEtiSequenceDto {

    private final Integer id;
    private final String code;
    private final String description;
    private final Integer lastNumber;

    public AdminEtiSequenceDto(
            Integer id,
            String code,
            String description,
            Integer lastNumber
    ){
        this.id = id;
        this.code = code;
        this.description = description;
        this.lastNumber = lastNumber;
    }

    public Integer getId(){ return id; }
    public String getCode(){ return code; }
    public String getDescription(){ return description; }
    public Integer getLastNumber(){ return lastNumber; }
}
