package org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS

import org.json.JSONArray
import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.getStringArray
import org.readium.r2.shared.Publication.parseArray


/// OPDS Acquisition Object
/// https://drafts.opds.io/schema/acquisition-object.schema.json
data class OPDSAcquisition (var _type: String, var _children: List<OPDSAcquisition>) {

    var type: String
    var children: List<OPDSAcquisition>

    init {
        this.type = _type
        this.children = _children
    }

    constructor(json: JSONObject) : this("", listOf()) {
        try {
            this.type = if(json.has("type")) json.getString("type") else throw JSONError.malformedJSON
            this.children = parseArray<OPDSAcquisition>(json.getJSONArray("child"))
        }   catch (e: Exception) {
            throw JSONError.malformedJSON
        }
    }

    fun toJSON() : JSONObject {
        val json = JSONObject()
        json.putOpt("type", type)
        json.putOpt("child", getStringArray(children))
        return json
    }

}

/**
 * Parse OPDSAcquisition json array
 */
fun parseOPDSAcquisitionArray(json: JSONArray) : List<OPDSAcquisition> {
    try {
        var list = mutableListOf<OPDSAcquisition>()
        for (i in 0..(json.length() - 1)) {
            val item = json.getJSONObject(i)
            var tmp = OPDSAcquisition(item)
            list.add(tmp)
        }
        return list
    }   catch (e: java.lang.Exception) {
        throw JSONError.malformedJSON
    }
}
