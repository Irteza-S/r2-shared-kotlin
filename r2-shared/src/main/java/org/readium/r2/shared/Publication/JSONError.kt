package org.readium.r2.shared.Publication

sealed class JSONError : Exception() {
    object malformedJSON : JSONError()
    object licenseDocument : JSONError()
    object statusDocument : JSONError()
    object link : JSONError()
    object encryption : JSONError()
    object signature : JSONError()
    data class url(val rel: String) : JSONError()

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