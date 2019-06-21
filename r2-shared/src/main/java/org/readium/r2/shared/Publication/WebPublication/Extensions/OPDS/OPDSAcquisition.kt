package org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS

import org.json.JSONObject
import org.readium.r2.shared.Publication.ParsingError
import org.readium.r2.shared.Publication.getStringArray
import org.readium.r2.shared.Publication.parseArray
import org.readium.r2.shared.Publication.parsePositiveDouble


/// OPDS Acquisition Object
/// https://drafts.opds.io/schema/acquisition-object.schema.json
data class OPDSAcquisition (var _type: String, var _children: List<OPDSAcquisition>) {

    var type: String
    var children: List<OPDSAcquisition>

    init {
        this.type = _type
        this.children = _children
    }

    /**
     * TODO: Throw errors
     */
    constructor(_json: Any?) : this("", listOf()) {
        if(_json != null) {
            var json = JSONObject(_json as String)
            this.type = if(json.has("type")) json.getString("type") else throw ParsingError.malformedJSON
            this.children = if(json.has("child")) parseArray(json.getJSONArray("child")) else listOf()
        }
    }

    fun toJSON() : JSONObject {
        val json = JSONObject()
        json.putOpt("type", type)
        json.putOpt("child", getStringArray(children))
        return json
    }

}
