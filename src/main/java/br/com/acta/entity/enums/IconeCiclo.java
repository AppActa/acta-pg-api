package br.com.acta.entity.enums;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum IconeCiclo {
    WALLET("https://res.cloudinary.com/kcypohk3/image/upload/v1790336465/wallet-icon.svg"),
    TARGET("https://res.cloudinary.com/kcypohk3/image/upload/v1790336454/target-icon.svg"),
    SHIELD("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/shield-icon.svg"),
    SETTINGS("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/settings-icon.svg"),
    PEOPLE("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/people-icon.svg"),
    HEADPHONE("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/headphone-icon.svg"),
    FOLDER("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/folder-icon.svg"),
    DATABASE("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/database-icon.svg"),
    CODE("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/code-icon.svg"),
    CLOCK("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/clock-icon.svg"),
    BOX("https://res.cloudinary.com/kcypohk3/image/upload/v1790336437/box-icon.svg");

    private final String url;

    public static boolean permite(String url) {
        return Arrays.stream(values()).anyMatch(icone -> icone.url.equals(url));
    }
}