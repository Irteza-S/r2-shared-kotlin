package org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS

import org.json.JSONObject
import org.readium.r2.shared.Publication.Error
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

    /**
     * TODO: Not sure about what to throw
     */
    constructor(_json: Any?) : this("", 0.0) {
        if(_json != null) {

            var json = JSONObject(_json as String)
            this.currency = if(json.has("currency")) json.getString("currency") else throw Error.malformedJSON
            this.value = if(json.has("value")) parsePositiveDouble(json.get("value")) as Double else throw Error.malformedJSON
        }
    }

    fun toJSON() : JSONObject {
        val json = JSONObject()
        json.putOpt("currency", currency)
        json.putOpt("value", value)
        return json
    }

}

