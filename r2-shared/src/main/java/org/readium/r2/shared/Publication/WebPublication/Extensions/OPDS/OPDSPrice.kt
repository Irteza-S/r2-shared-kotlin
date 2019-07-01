package org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS

import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.parsePositiveDouble


/// The price of a publication in an OPDS link.
/// https://drafts.opds.io/schema/properties.schema.json
data class OPDSPrice(var _currency: String, var _value: Double) {

    var currency: String // eg. EUR

    var value: Double // Should only be used for display purposes, because of precision issues inherent with Double and the JSON parsing.

    init {
        this.currency = _currency
        this.value = _value
    }

    constructor(json: JSONObject) : this("", 0.0) {
        try {
            currency = json.getString("currency")
            value = if(json.has("value")) parsePositiveDouble(json.get("value")) as Double else throw JSONError.malformedJSON
        }   catch (e : Exception) {
            throw JSONError.malformedJSON
        }
    }

    fun toJSON() : JSONObject {
        val json = JSONObject()
        json.putOpt("currency", currency)
        json.putOpt("value", value)
        return json
    }

}

