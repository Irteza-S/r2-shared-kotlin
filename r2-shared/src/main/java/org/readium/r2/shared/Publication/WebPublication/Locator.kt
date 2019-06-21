/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Mostapha Idoubihi, Paul Stoica
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.WebPublication

import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.encodeIfNotNull
import org.readium.r2.shared.Publication.getStringArray


/**
 * TODO ADD CONSTRUCTORS + var Description
 */
data class Locator (val _href: String, val _type: String, val _title: String? = null, val _locations: Locations? = null, val _text: LocatorText? = null){

    /// The URI of the resource that the Locator Object points to.
    var href: String = ""  // URI
    /// The media type of the resource that the Locator Object points to.
    var type: String = ""
    /// The title of the chapter or section which is more relevant in the context of this locator.
    var title: String?
    /// One or more alternative expressions of the location.
    var locations: Locations?
    /// Textual context of the locator.
    var text: LocatorText?

     init {
         this.href = _href
         this.type = _type
         this.title = _title
         this.locations = _locations
         this.text = _text
    }


    //TODO Throw exception
    constructor(json: Any) : this("",""){
        if(json is JSONObject) {
            this.href = if(json.has("href")) json.getString("href") else throw org.readium.r2.shared.Publication.Error.malformedJSON
            this.type = if(json.has("type")) json.getString("type") else throw org.readium.r2.shared.Publication.Error.malformedJSON
            this.title = json.getString("title")
            this.locations = if(json.has("locations")) Locations(json.get("locations")) else null
            this.text = if(json.has("text")) LocatorText(json.get("locations")) else null
        }
    }

    //TODO : complete
    constructor(link: Link) : this("","") {

    }
}


data class LocatorText(var _after: String? = null, var _before: String? = null, var _highlight: String? = null) {
    var after: String?
    var before: String?
    var highlight: String?

    init {
        this.after = _after
        this.before = _before
        this.highlight = _highlight
    }

    constructor(json: Any) : this() {
        if (json is JSONObject) {
            this.after = json.getString("after")
            this.before = json.getString("before")
            this.highlight = json.getString("highlight")
        } else throw JSONError.parsing(this)
    }

    fun toJSON() : JSONObject {
        val json = JSONObject()
        if(encodeIfNotNull(after) != null)
            json.putOpt("after", after)
        if(encodeIfNotNull(before) != null)
            json.putOpt("before", before)
        if(encodeIfNotNull(highlight) != null)
            json.putOpt("highlight", highlight)
        return json
    }
}


data class Locations(var _fragment: String? = null, var _progression: Double? = null, var _position: Int? = null) {
    /// Contains one or more fragment in the resource referenced by the Locator Object.
    var fragment: String? = null // 1 = fragment identifier (toc, page lists, landmarks)
    /// Progression in the resource expressed as a percentage.
    var progression: Double? = null // 2 = bookmarks
    /// An index in the publication.
    var position: Int? = null // 3 = goto page

    init {
        this.fragment = _fragment
        this.progression = _progression
        this.position = _position
    }


    constructor(json: Any) : this() {
        if(json is JSONObject) {
            this.fragment = json.getString("fragment")
            this.progression = json.getDouble("progression")
            this.position = json.getInt("position")
        } else throw JSONError.parsing(this)
    }

}


/*
/**
 * Locator model - https://github.com/readium/architecture/tree/master/locators
 *
 * @val href: String -  The href of the resource the locator points at.
 * @val type: String - The media type of the resource that the Locator Object points to.
 * @val title: String - The title of the chapter or section which is more relevant in the context of this locator.
 * @val location: Location - One or more alternative expressions of the location.
 * @val text: LocatorText? - Textual context of the locator.
 */

open class Locator(val href: String,
                   val type: String,
                   val title: String? = null,
                   val locations: Locations? = null,
                   val text: LocatorText?) : Serializable

class LocatorText(var after: String? = null,
                  var before: String? = null,
                  var highlight: String? = null)
    : JSONable, Serializable {

    companion object {
        fun fromJSON(json: JSONObject): LocatorText {

            val location = LocatorText()
            if (json.has("before")) {
                location.before = json.getString("before")
            }
            if (json.has("highlight")) {
                location.highlight = json.getString("highlight")
            }
            if (json.has("after")) {
                location.after = json.getString("after")
            }

            return location
        }
    }

    override fun toJSON(): JSONObject {
        val json = JSONObject()

        before?.let {
            json.putOpt("before", before)
        }
        highlight?.let {
            json.putOpt("highlight", highlight)
        }
        after?.let {
            json.putOpt("after", after)
        }

        return json
    }

    override fun toString(): String {
        var jsonString = """{"""
        before.let { jsonString += """ "before": "$before" ,""" }
        highlight.let { jsonString += """ "before": "$highlight" ,""" }
        after.let { jsonString += """ "after": "$after" ,""" }
        jsonString += """}"""
        return jsonString
    }
}

/**
 * Location : Class that contain the different variables needed to localize a particular position
 *
 * @var fragment: Long? - Contains one or more fragment in the resource referenced by the Locator Object.
 * @var progression: Double - Progression in the resource expressed as a percentage.
 * @var position: Long - An index in the publication.
 *
 */
class Locations(var fragment: String? = null,        // 1 = fragment identifier (toc, page lists, landmarks)
                var progression: Double? = null,     // 2 = bookmarks
                var position: Long? = null           // 3 = goto page
) : JSONable, Serializable {

    companion object {
        fun fromJSON(json: JSONObject): Locations {

            val location = Locations()
            if (json.has("fragment")) {
                location.fragment = json.getString("fragment")
            }
            if (json.has("progression")) {
                location.progression = json.getDouble("progression")
            }
            if (json.has("position")) {
                location.position = json.getLong("position")
            }

            return location
        }

        fun isEmpty(locations: Locations):Boolean {
            if (locations.fragment == null && locations.position == null && locations.progression == null) {
                return true
            }
            return false
        }

    }

    override fun toJSON(): JSONObject {
        val json = JSONObject()

        fragment?.let {
            json.putOpt("fragment", fragment)
        }
        progression?.let {
            json.putOpt("progression", progression)
        }
        position?.let {
            json.putOpt("position", position)
        }

        return json
    }

    override fun toString(): String {
        var jsonString = """{"""
        fragment.let { jsonString += """ "fragment": "$fragment" ,""" }
        progression.let { jsonString += """ "progression": "$progression" ,""" }
        position.let { jsonString += """ "position": "$position" """ }
        jsonString += """}"""
        return jsonString
    }
}*/