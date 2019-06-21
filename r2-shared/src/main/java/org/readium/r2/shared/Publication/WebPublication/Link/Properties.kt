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
import org.readium.r2.shared.Encryption
import org.readium.r2.shared.JSONable
import org.readium.r2.shared.OPDS.IndirectAcquisition
import org.readium.r2.shared.OPDS.Price
import org.readium.r2.shared.Publication.*
import java.io.Serializable


/// Link Properties
/// https://readium.org/webpub-manifest/schema/properties.schema.json
class Properties : JSONable, Serializable {


    //Update from swift
    var page : Page? = null
    var orientation: Orientation? = null
    /// Additional properties for extensions.
    var otherProperties: MutableList<Pair<String, Any?>>
        get() {
            return otherPropertiesJSON.json
        }
        set(newValue) {
            otherPropertiesJSON.json = newValue
        }

    // Trick to keep the struct equatable despite [String: Any]
    private var otherPropertiesJSON: JSONDictionary = JSONDictionary()

    /**
     * TODO: Subscript + Mutating functions
     */
    constructor(orientation: Orientation? = null, page: Page? = null, otherProperties: List<Pair<String, Any?>> = listOf()) {
        this.orientation = orientation
        this.page = page
        this.otherPropertiesJSON = JSONDictionary(otherProperties) ?: JSONDictionary()
    }

    /**
     * TODO : Throw error on JSONDictionnary()
     */
    constructor(json: Any?) {
        if (json != null) {
            var json = JSONDictionary(json) ?: throw JSONError.parsing(this)
            this.orientation = parseRaw(json.pop("orientation"))
            this.page = parseRaw(json.pop("page"))
            this.otherPropertiesJSON = json
        }
    }

    /// Suggested orientation for the device when displaying the linked resource.
    enum class Orientation (val rawValue: String) {
        auto("auto"), landscape("landscape"), portrait("portrait");

        companion object {
            operator fun invoke(rawValue: String) = Orientation.values().firstOrNull { it.rawValue == rawValue }
        }
    }

    /// Indicates how the linked resource should be displayed in a reading environment that displays synthetic spreads.
    enum class Page (val rawValue: String) {
        left("left"), right("right"), center("center");

        companion object {
            operator fun invoke(rawValue: String) = Page.values().firstOrNull { it.rawValue == rawValue }
        }
    }

    //TODO : make JSON
    override fun toJSON(): JSONObject {
        val json = JSONObject()
        if(encodeIfNotNull(orientation) != null)
            json.putOpt("orientation", orientation)
        if(encodeIfNotNull(page) != null)
            json.putOpt("page", page)
        return json
    }




    //Not in iOS anymore
    /*
    /// Suggested orientation for the device when displaying the linked resource.
    var orientation: String? = null
    /// Indicates how the linked resource should be displayed in a reading
    /// environment that displays synthetic spreads.
    var page: String? = null
    /// Identifies content contained in the linked resource, that cannot be
    /// strictly identified using a media type.
    var contains: MutableList<String> = mutableListOf()
    /// Location of a media-overlay for the resource referenced in the Link Object.
    private var mediaOverlay: String? = null
    /// Indicates that a resource is encrypted/obfuscated and provides relevant
    /// information for decryption.
    var encryption: Encryption? = null
    /// Hint about the nature of the layout for the linked resources.
    var layout: String? = null
    /// Suggested method for handling overflow while displaying the linked resource.
    var overflow: String? = null
    /// Indicates the condition to be met for the linked resource to be rendered
    /// within a synthetic spread.
    var spread: String? = null
    ///
    var numberOfItems: Int? = null
    ///
    var price: Price? = null
    ///
    var indirectAcquisition: MutableList<IndirectAcquisition> = mutableListOf()
    */
}