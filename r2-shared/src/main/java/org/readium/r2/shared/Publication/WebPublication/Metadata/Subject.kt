/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.WebPublication.Metadata

import org.json.JSONObject
import org.readium.r2.shared.JSONable
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.MultilanguageString
import org.readium.r2.shared.Publication.ParsingError
import org.readium.r2.shared.Publication.getStringArray
import org.readium.r2.shared.Publication.parseArray
import java.io.Serializable

class Subject : JSONable, Serializable {

    //From iOS
    var localizedName: String = ""
    var name: String = ""
        get() =  localizedName
    //  The WebPubManifest elements
    var sortAs: String? = null
    //  Epub 3.1 "scheme" (opf:authority)
    var scheme: String? = null
    //  Epub 3.1 "code" (opf:term)
    var code: String? = null
    /// Used to retrieve similar publications for the given subjects.
    var links: MutableList<Link> = mutableListOf()

    //TODO Throw exception
     constructor(json: Any) {
        when(json) {
            is String -> {
                this.localizedName = json
            }
            is JSONObject -> {
                this.name = if(json.has("name")) json.getString("name") else throw ParsingError.malformedJSON
                this.sortAs = json.getString("sortAs")
                this.scheme = json.getString("sortAs")
                this.links = if(json.has("links")) parseArray(json.getJSONArray("links"), true) else mutableListOf()
            }
        }
    }


    override fun toJSON(): JSONObject {
        val json = JSONObject()
        json.putOpt("name", name)
        json.putOpt("sortAs", sortAs)
        json.putOpt("scheme", scheme)
        json.putOpt("code", code)
        if (links.isNotEmpty())
            json.put("children", getStringArray(links))
        return json
    }

}