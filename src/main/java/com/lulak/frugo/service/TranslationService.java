package com.lulak.frugo.service;

import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    public String translateProduct(String key){
        return switch (key){
            case "offering-products.bananas" -> "Bananas";
            case "offering-products.carrots" -> "Carrots";
            case "offering-products.apples" -> "Apples";
            case "offering-products.parsley" -> "Parsley";
            default -> key;
        };
    }

    public String translateCategory(String category){
        return switch (category){
            case "fruits" -> "Fruits";
            case "vegetables" -> "Vegetables";
            case "herbs" -> "Herbs";
            default -> category;
        };
    }
}
