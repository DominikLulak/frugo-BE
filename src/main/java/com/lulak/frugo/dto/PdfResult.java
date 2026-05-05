package com.lulak.frugo.dto;

public class PdfResult {

    private final String fileName;
    private final byte[] content;

    public PdfResult(String fileName, byte[] content){
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName(){
        return fileName;
    }

    public byte[] getContent() {
        return content;
    }
}
