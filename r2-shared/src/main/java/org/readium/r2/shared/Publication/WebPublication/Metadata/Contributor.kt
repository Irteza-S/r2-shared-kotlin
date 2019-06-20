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
import org.readium.r2.shared.Publication.*
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import java.io.Serializable

class Contributor : JSONable, Serializable {


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



    constructor(name: String, identifier: String? = null, sortAs: String? = null, roles: MutableList<String> = mutableListOf(), role: String? = null, position: Double? = null, links: List<Link> = listOf()) {
        this.localizedName = name
        this.identifier = identifier
        this.sortAs = sortAs
        this.roles = roles
        this.position = position
        this.links = links
        val role = role
        if (role != null) {
            this.roles.add(role)
        }
    }



    /** TODO Throw errors **/
    constructor(_json: Any, normalizeHref: (String) -> String = { it }) {
        when (_json) {
            is String -> {
                this.localizedName = _json
            }
            is JSONObject -> {
                var json = JSONObject((_json as String).toString())
                this.name = if(json.has("name")) json.getString("name") else throw ParsingError.malformedJSON
                this.localizedName = if(json.has("localizedName")) json.getString("localizedName") else null
                this.identifier = if(json.has("href")) json.getString("templated") else null
                this.sortAs = if(json.has("sortAs")) json.getString("sortAs") else null
                this.roles = if(json.has("roles")) parseArray(json.getJSONArray("roles"), true) else mutableListOf()
                this.position = if(json.has("position")) parsePositiveDouble(json.getDouble("position")) else null
                this.links = if(json.has("links")) parseArray(json.getJSONArray("links"), true) else listOf()
            }
            else -> {
                throw ParsingError.malformedJSON
            }
        }
    }

    override fun toJSON(): JSONObject {
        val obj = JSONObject()
        obj.put("name", name)
        if (roles.isNotEmpty()) {
            obj.put("roles", getStringArray(roles as MutableList<Any>))
        }
        obj.put("sortAs", sortAs)
        return obj
    }

}

/**
 * TODO : extension Array
 */

//Removed from iOS
/*
fun parseContributors(contributors: Any): List<Contributor> {
    val result: MutableList<Contributor> = mutableListOf()
    when (contributors) {
        is String -> {
            val c = Contributor()
            c.localizedName = contributors
            result.add(c)
        }
        is Array<*> -> {
            for(i in 0 until contributors.size - 1) {
                val c = Contributor()
                c.localizedName = contributors[i] as String
                result.add(c)
            }
        }
        is JSONObject -> {
            val c = parseContributor(contributors)
            result.add(c)
        }
        is JSONArray -> for (i in 0..(contributors.length() - 1)) {
            when (contributors.get(i)) {
                is String -> {
                    val c = Contributor()
                    c.localizedName = contributors.getString(i)
                    result.add(c)
                }
                is JSONObject -> {
                    val obj = contributors.getJSONObject(i)
                    val c = parseContributor(obj)
                    result.add(c)
                }
            }
        }
    }
    return result
}*/

//Deleted from iOS
/*
fun parseContributor(cDict: JSONObject): Contributor {
    val c = Contributor()

    if (cDict.has("name")) {
        if (cDict.get("name") is String) {
            c.localizedName = cDict.getString("name")
        } else if (cDict.get("name") is JSONObject) {
            val array = cDict.getJSONObject("name")
            c.localizedName = array as
        }

    }
    if (cDict.has("identifier")) {
        c.identifier = cDict.getString("identifier")
    }
    if (cDict.has("sort_as")) {
        c.sortAs = cDict.getString("sort_as")
    }
    if (cDict.has("role")) {
        c.roles.add(cDict.getString("role"))
    }
    if (cDict.has("links")) {
        cDict.get("links")?.let {
        val links = it as? JSONArray
                ?: JSONArray()
        for (i in 0..(links.length() - 1)) {
            val linkDict = links.getJSONObject(i)
            val link = parseLink(linkDict)
            c.links.add(link)
        }
    }
    }
    return c
}
*/