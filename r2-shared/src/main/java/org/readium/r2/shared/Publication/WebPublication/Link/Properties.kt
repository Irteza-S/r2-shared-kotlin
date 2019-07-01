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
import java.io.Serializable


/// Link Properties
/// https://readium.org/webpub-manifest/schema/properties.schema.json
class Properties :  Serializable {

    var page : Page? = null
    /// Suggested orientation for the device when displaying the linked resource.
    var orientation: Orientation? = null
    /// Additional properties for extensions.
    var otherProperties: JSONObject? = null

    // Trick to keep the struct equatable despite [String: Any]
    private var otherPropertiesJSON: JSONObject? = null

    constructor(orientation: Orientation? = null, page: Page? = null, otherProperties: JSONObject) {
        this.orientation = orientation
        this.page = page
        this.otherPropertiesJSON = otherProperties
    }

    /**
     * TODO : Pop?
     */
    constructor(json: JSONObject? = null) {
        if (json != null) {
            this.orientation = json.get("orientation") as Orientation
            this.page = json.get("page") as Page
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

    /**
     * TODO: Subscript + Mutating
     */
    operator fun get(key: String): Any? {
        return otherProperties?.get(key)
    }


    /**
     * Todo: Not in Swift anymore
     */
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