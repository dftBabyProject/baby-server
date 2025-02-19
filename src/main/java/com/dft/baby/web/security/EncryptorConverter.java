package com.dft.baby.web.security;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class EncryptorConverter implements AttributeConverter<String, String> {

    private final CryptoUtil cryptoUtil;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return cryptoUtil.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return cryptoUtil.decrypt(dbData);
    }
}
