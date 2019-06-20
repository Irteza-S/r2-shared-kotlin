package org.readium.r2.shared.Publication

/**
 * TODO : Fix
 */
sealed class ParsingError : Exception() {
    object malformedJSON : ParsingError()
    object licenseDocument : ParsingError()
    object statusDocument : ParsingError()
    object link : ParsingError()
    object encryption : ParsingError()
    object signature : ParsingError()
    data class url(val rel: String) : ParsingError()

    val errorDescription: String?
        get() {
            return when (this) {
                is malformedJSON -> "The JSON is malformed and can't be parsed."
                is licenseDocument -> "The JSON is not representing a valid License Document."
                is statusDocument -> "The JSON is not representing a valid Status Document."
                is link -> "Invalid Link."
                is encryption -> "Invalid Encryption."
                is signature -> "Invalid License Document Signature."
                is url -> "Invalid URL for link with rel $rel."
            }
        }
}