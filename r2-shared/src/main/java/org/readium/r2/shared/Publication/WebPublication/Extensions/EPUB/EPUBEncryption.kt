package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB

import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONError


/// Indicates that a resource is encrypted/obfuscated and provides relevant information for decryption.
data class EPUBEncryption(val _algorithm: String, val _compression: String? = null, val _originalLength: Int? = null, val _profile: String? = null, val _scheme: String? = null) {

    // Identifies the algorithm used to encrypt the resource.
    var algorithm: String // URI
    // Compression method used on the resource.
    var compression: String? = null
    // Original length of the resource in bytes before compression and/or encryption.
    var originalLength: Int? = null
    // Identifies the encryption profile used to encrypt the resource.
    var profile: String? = null // URI
    // Identifies the encryption scheme used to encrypt the resource.
    var scheme: String? = null // URI

    init {
        this.algorithm = _algorithm
        this.compression = _compression
        this.originalLength = _originalLength
        this.profile = _profile
        this.scheme = _scheme
    }

    constructor(json: JSONObject) : this("") {
        try {
            this.algorithm = if(json.has("algorithm")) json.getString("algorithm") else throw JSONError.malformedJSON
            this;compression = json.getString("compression")
            this.originalLength = json.getInt("original-length")
            this.profile = json.getString("profile")
            this.scheme = json.getString("scheme")
        }   catch (e: Exception) {
            throw JSONError.malformedJSON
        }
    }

}
