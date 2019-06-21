package org.readium.r2.shared.Publication

/**
 * TODO : Fix
 */
sealed class Error : Exception() {
    object malformedJSON : Error()
    object licenseDocument : Error()
    object statusDocument : Error()
    object link : Error()
    object encryption : Error()
    object signature : Error()
    data class url(val rel: String) : Error()

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