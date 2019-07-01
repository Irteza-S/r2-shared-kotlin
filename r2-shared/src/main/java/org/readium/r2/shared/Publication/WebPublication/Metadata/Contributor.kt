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
import org.readium.r2.shared.JSONable
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS.OPDSAcquisition
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.getStringArray
import org.readium.r2.shared.Publication.parseArray
import org.readium.r2.shared.Publication.parsePositiveDouble
import java.io.Serializable

class Contributor : Serializable {

    var name: String? = null
        get() = name
    /// An unambiguous reference to this contributor
    var localizedName: String? = null
    /// An unambiguous reference to this contributor.
    var identifier: String? = null
    /// The string used to sort the name of the contributor.
    var sortAs: String? = null
    /// The role of the contributor in the publication making.
    var roles: MutableList<String> = mutableListOf()
    /// The position of the publication in this collection/series, when the contributor represents a collection.
    var position: Double? = null
    /// Used to retrieve similar publications for the given contributor.
    var links: List<Link> = listOf()

    constructor(_json: Any, normalizeHref: (String) -> String = { it }) {
        when (_json) {
            is String -> {
                this.localizedName = _json
            }
            is JSONObject -> {
                var json = JSONObject(_json as String)
                this.name = if(json.has("name")) json.getString("name") else throw JSONError.malformedJSON
                this.localizedName = json.getString("localizedName")
                this.identifier = json.getString("identifier")
                this.sortAs = json.getString("sortAs")
                this.roles = parseArray<String>(json.getJSONArray("roles"), true).toMutableList()
                this.position = parsePositiveDouble(json.getDouble("position"))
                this.links = parseArray<Link>(json.getJSONArray("links"), true)
            }
            else -> {
                throw JSONError.malformedJSON
            }
        }
    }

}

fun parseContributorJSONArray(json: JSONArray): List<Contributor> {
    /**
     * Todo: to be checked, this is probably wrong
     */
    try {
        var list = mutableListOf<Contributor>()
        for (i in 0..(json.length() - 1)) {
            val item = json.getJSONObject(i)
            var tmp = Contributor(item)
            list.add(tmp)
        }
        return list
    }   catch (e: Exception) {
        throw JSONError.malformedJSON
    }
}


/**
 * TODO: not in Swift anymore
 */
//fun parseContributors(contributors: Any): List<Contributor> {
//    val result: MutableList<Contributor> = mutableListOf()
//    when (contributors) {
//        is String -> {
//            val c = Contributor()
//            c.multilanguageName.singleString = contributors
//            result.add(c)
//        }
//        is Array<*> -> {
//            for(i in 0 until contributors.size - 1) {
//                val c = Contributor()
//                c.multilanguageName.singleString = contributors[i] as String
//                result.add(c)
//            }
//        }
//        is JSONObject -> {
//            val c = parseContributor(contributors)
//            result.add(c)
//        }
//        is JSONArray -> for (i in 0 until contributors.length()) {
//            when (contributors.get(i)) {
//                is String -> {
//                    val c = Contributor()
//                    c.multilanguageName.singleString = contributors.getString(i)
//                    result.add(c)
//                }
//                is JSONObject -> {
//                    val obj = contributors.getJSONObject(i)
//                    val c = parseContributor(obj)
//                    result.add(c)
//                }
//            }
//        }
//    }
//    return result
//}
//
//fun parseContributor(cDict: JSONObject): Contributor {
//    val c = Contributor()
//
//    if (cDict.has("name")) {
//        if (cDict.get("name") is String) {
//            c.multilanguageName.singleString = cDict.getString("name")
//        } else if (cDict.get("name") is JSONObject) {
//            val array = cDict.getJSONObject("name")
//            c.multilanguageName.multiString = array as MutableMap<String, String>
//        }
//
//    }
//    if (cDict.has("identifier")) {
//        c.identifier = cDict.getString("identifier")
//    }
//    if (cDict.has("sort_as")) {
//        c.sortAs = cDict.getString("sort_as")
//    }
//    if (cDict.has("role")) {
//        c.roles.add(cDict.getString("role"))
//    }
//    if (cDict.has("links")) {
//        cDict.get("links")?.let {
//        val links = it as? JSONArray
//                ?: JSONArray()
//        for (i in 0 until links.length()) {
//            val linkDict = links.getJSONObject(i)
//            val link = parseLink(linkDict)
//            c.links.add(link)
//        }
//    }
//    }
//    return c
//}
