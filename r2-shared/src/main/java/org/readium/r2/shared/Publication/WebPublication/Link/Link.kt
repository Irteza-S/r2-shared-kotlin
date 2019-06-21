/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.WebPublication.Link

import org.json.JSONObject
import org.readium.r2.shared.*
import org.readium.r2.shared.Publication.*
import org.readium.r2.shared.Publication.MediaOverlays.MediaOverlays
import java.io.Serializable

/// Link Object for the Readium Web Publication Manifest.
/// https://readium.org/webpub-manifest/schema/link.schema.json
///
/// Note: This is not a struct because in certain situations Link has an Identity (eg. EPUB), and rely on reference semantics to manipulate the links.
class Link  : JSONable, Serializable  {

    /**FROM iOS **/
    /// URI or URI template of the linked resource.
    /// Note: a String because templates are lost with URL.
    var href: String  // URI
    /// MIME type of the linked resource.
    var type: String?
    /// Indicates that a URI template is used in href.
    var templated: Boolean
    /// Title of the linked resource.
    var title: String?
    /// Relation between the linked resource and its containing collection.
    var rels: MutableList<String>
    /// Properties associated to the linked resource.
    var properties: Properties
    /// Height of the linked resource in pixels.
    var height: Int?
    /// Width of the linked resource in pixels.
    var width: Int?
    /// Bitrate of the linked resource in kbps.
    var bitrate: Double?
    /// Length of the linked resource in seconds.
    var duration: Double?
    /// Resources that are children of the linked resource, in the context of a given collection role.
    var children: List<Link>


    //  The link destination
    /// MIME type of resource.
    var typeLink: String? = null
    //  The MediaOverlays associated to the resource of the Link
    var mediaOverlays: MediaOverlays? = null


    constructor(href: String, type: String? = null, templated: Boolean = false, title: String? = null, rels: MutableList<String> = mutableListOf(), rel: String? = null, properties: Properties = Properties(), height: Int? = null, width: Int? = null, bitrate: Double? = null, duration: Double? = null, children: List<Link> = listOf()) {
        this.href = href
        this.type = type
        this.templated = templated
        this.title = title
        this.rels = rels
        this.properties = properties
        this.height = height
        this.width = width
        this.bitrate = bitrate
        this.duration = duration
        this.children = children
        if (rel != null) {
            this.rels.add(rel)
        }
    }

    /**
     * TODO: Parsing Errors(var template if href is URI) + try catch on Properties + var Children & varRels Arrays Parsing?
     */
    constructor(_json: Any?, normalizeHref: (String) -> String = { it }) {
        try {
            var json = JSONObject(_json as String)
            this.href = if(json.has("href")) json.getString("href") else throw Error.malformedJSON
            this.type = if(json.has("type")) json.getString("type") else null
            this.templated = if(json.has("href")) json.getBoolean("templated") else false
            this.title = if(json.has("href")) json.getString("title") else null
            this.rels = if(json.has("href")) parseArray(json.getJSONArray("rel"), true) else mutableListOf()
            this.properties = if(json.has("href")) Properties(json.get("properties")) else Properties()
            this.height = if(json.has("height")) parsePositive(json.getInt("height")) as Int else null
            this.width = if(json.has("width")) parsePositive(json.getInt("width")) as Int else null
            this.bitrate = if(json.has("birate")) parsePositiveDouble(json.getDouble("bitrate")) as Double else null
            this.duration = if(json.has("width")) parsePositiveDouble(json.getDouble("width")) as Double else null
            this.children = if(json.has("children")) parseArray(json.getJSONArray("children")) else listOf()
        } catch (e: Exception) {
            throw Error.malformedJSON
        }
    }

    /**
     * TODO: ARRAYS TO STRING + Properties encodeIfNotEmpty()
     */
    override fun toJSON() : JSONObject {
        val json = JSONObject()
        json.putOpt("href", title)
        if(encodeIfNotNull(href) != null)
            json.putOpt("type", typeLink)
        if(encodeIfNotNull(templated) != null)
            json.putOpt("templated", templated)
        if(encodeIfNotNull(title) != null)
            json.putOpt("title", title)
        if(rels.isNotEmpty())
            json.putOpt("rel", getStringArray(rels))
        if(properties!= null)
            json.putOpt("rel", properties.toJSON())
        if (encodeIfNotNull(height) != null )
            json.putOpt("height", height)
        if (encodeIfNotNull(width) != null )
            json.putOpt("width", width)
        if (encodeIfNotNull(bitrate) != null )
            json.putOpt("bitrate", bitrate)
        if (encodeIfNotNull(duration) != null )
            json.putOpt("duration", duration)
        if (children.isNotEmpty())
            json.put("children", getStringArray(children))
        return json
    }

}


/**
 * TODO : this is not in iOS
 */

/*
  fun isEncrypted(): Boolean {
        return properties?.encryption != null
    }

enum class LinkError(var v: String) {
    InvalidLink("Invalid link"),
}


fun parseLink(linkDict: JSONObject, feedUrl: URL? = null): Link {
    val link = Link()
    if (linkDict.has("title")) {
        link.title = linkDict.getString("title")
    }
    if (linkDict.has("href")) {
        feedUrl?.let {
            link.href = getAbsolute(linkDict.getString("href")!!, feedUrl.toString())
        } ?: run {
            link.href = linkDict.getString("href")!!
        }
    }
    if (linkDict.has("type")) {
        link.typeLink = linkDict.getString("type")
    }
    if (linkDict.has("rel")) {
        if (linkDict.get("rel") is String) {
            link.rel?.add(linkDict.getString("rel"))
        } else if (linkDict.get("rel") is JSONArray) {
            val array = linkDict.getJSONArray("rel")
            for (i in 0..(array.length() - 1)) {
                val string = array.getString(i)
                link.rel?.add(string)
            }
        }
    }
    if (linkDict.has("height")) {
        link.height = linkDict.getInt("height")
    }
    if (linkDict.has("width")) {
        link.width = linkDict.getInt("width")
    }
    if (linkDict.has("bitrate")) {
        link.bitrate = linkDict.getInt("bitrate")
    }
    if (linkDict.has("duration")) {
        link.duration = linkDict.getDouble("duration")
    }
    if (linkDict.has("properties")) {
        val properties = Properties()
        val propertiesDict = linkDict.getJSONObject("properties")
        if (propertiesDict.has("numberOfItems")) {
            properties.numberOfItems = propertiesDict.getInt("numberOfItems")
        }
        if (propertiesDict.has("indirectAcquisition")) {
            val acquisitions = propertiesDict.getJSONArray("indirectAcquisition")
                    ?: throw Exception(LinkError.InvalidLink.name)
            for (i in 0..(acquisitions.length() - 1)) {
                val acquisition = acquisitions.getJSONObject(i)
                val indirectAcquisition = parseIndirectAcquisition(indirectAcquisitionDict = acquisition)
                properties.indirectAcquisition.add(indirectAcquisition)
            }
        }
        if (propertiesDict.has("price")) {
            val priceDict = propertiesDict.getJSONObject("price")
            val currency = priceDict["currency"] as? String
            val value = priceDict["value"] as? Double
            if (priceDict == null || currency == null || value == null) {
                throw Exception(LinkError.InvalidLink.name)
            }
            val price = Price(currency = currency, value = value)
            properties.price = price
        }
        link.properties = properties
    }
    if (linkDict.has("children")) {
        linkDict.get("children")?.let {
            val children = it as? JSONArray
                    ?: throw Exception(LinkError.InvalidLink.name)
            for (i in 0..(children.length() - 1)) {
                val childLinkDict = children.getJSONObject(i)
                val childLink = parseLink(childLinkDict)
                link.children?.add(childLink)
            }
        }
    }
    return link
}
*/