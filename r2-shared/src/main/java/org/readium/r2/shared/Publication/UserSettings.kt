package org.readium.r2.shared.Publication

/// List of strings that can identify a user setting
enum class ReadiumCSSReference (val rawValue: String) {
    fontSize("fontSize"),
    fontFamily("fontFamily"),
    fontOverride("fontOverride"),
    appearance("appearance"),
    scroll("scroll"),
    publisherDefault("advancedSettings"),
    textAlignment("textAlign"),
    columnCount("colCount"),
    wordSpacing("wordSpacing"),
    letterSpacing("letterSpacing"),
    pageMargins("pageMargins"),
    lineHeight("lineHeight"),
    paraIndent("paraIndent"),
    hyphens("bodyHyphens"),
    ligatures("ligatures");

    companion object {
        operator fun invoke(rawValue: String) = ReadiumCSSReference.values().firstOrNull { it.rawValue == rawValue }
    }
}

/// List of strings that can identify the name of a CSS custom property
/// Also used for storing UserSettings in UserDefaults
enum class ReadiumCSSName (val rawValue: String) {
    fontSize("--USER__fontSize"),
    fontFamily("--USER__fontFamily"),
    fontOverride("--USER__fontOverride"),
    appearance("--USER__appearance"),
    scroll("--USER__scroll"),
    publisherDefault("--USER__advancedSettings"),
    textAlignment("--USER__textAlign"),
    columnCount("--USER__colCount"),
    wordSpacing("--USER__wordSpacing"),
    letterSpacing("--USER__letterSpacing"),
    pageMargins("--USER__pageMargins"),
    lineHeight("--USER__lineHeight"),
    paraIndent("--USER__paraIndent"),
    hyphens("--USER__bodyHyphens"),
    ligatures("--USER__ligatures");

    companion object {
        operator fun invoke(rawValue: String) = ReadiumCSSName.values().firstOrNull { it.rawValue == rawValue }
    }
}
