package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB

import org.json.JSONObject
import org.readium.r2.shared.Publication.Error
import org.readium.r2.shared.Publication.encodeIfNotNull


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

    constructor(_json: Any?) : this("") {
        if(_json != null) {
            var json = JSONObject(_json as String)
            this.algorithm = if(json.has("algorithm")) json.getString("algorithm") else throw Error.malformedJSON
            this.compression = if(json.has("compression")) json.getString("compression") else null
            this.originalLength = if(json.has("original-length")) json.getInt("original-length") else null
            this.profile = if(json.has("profile")) json.getString("profile") else null
            this.scheme = if(json.has("scheme")) json.getString("scheme") else null
        }
    }

    fun toJSON() : JSONObject {
        val json = JSONObject()
        json.putOpt("algorithm", algorithm)
        if(encodeIfNotNull(compression) != null)
            json.putOpt("compression", compression)
        if(encodeIfNotNull(originalLength) != null)
            json.putOpt("original-length", originalLength)
        if(encodeIfNotNull(profile) != null)
            json.putOpt("profile", profile)
        if(encodeIfNotNull(scheme) != null)
            json.putOpt("scheme", scheme)
        return json
    }

}
