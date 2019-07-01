/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.WebPublication.Metadata

import org.json.JSONArray
import org.json.JSONObject
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.parseArray
import java.io.Serializable

class Subject : Serializable {

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

    constructor(json: Any) {
        when(json) {
            is String -> {
                this.localizedName = json
            }
            is JSONObject -> {
                this.name = if(json.has("name")) json.getString("name") else throw JSONError.malformedJSON
                this.sortAs = json.getString("sortAs")
                this.scheme = json.getString("scheme")
                this.links = if(json.has("links")) parseArray<Link>(json.getJSONArray("links"), true).toMutableList() else mutableListOf()
            }
            else -> throw JSONError.malformedJSON
        }
    }

    fun parseSubjectJSONArray(json: JSONArray): List<Subject> {
        /**
         * Todo: to be checked, this is probably wrong
         */
        try {
            var list = mutableListOf<Subject>()
            for (i in 0..(json.length() - 1)) {
                val item = json.getJSONObject(i)
                var tmp = Subject(item)
                list.add(tmp)
            }
            return list
        }   catch (e: Exception) {
            throw JSONError.malformedJSON
        }
    }

}