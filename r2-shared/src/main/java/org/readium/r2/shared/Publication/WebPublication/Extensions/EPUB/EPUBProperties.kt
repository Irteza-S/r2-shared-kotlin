package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB


/// EPUB Link Properties Extension
/// https://readium.org/webpub-manifest/schema/extensions/epub/properties.schema.json
interface EPUBProperties {
    /// Identifies content contained in the linked resource, that cannot be strictly identified using a media type.
    var contains: List<String>
    /// Hints how the layout of the resource should be presented.
    var layout: EPUBRendition.Layout?
    /// Location of a media-overlay for the resource referenced in the Link Object.
    var mediaOverlay: String?
    /// Suggested method for handling overflow while displaying the linked resource.
    var overflow: EPUBRendition.Overflow?
    /// Indicates the condition to be met for the linked resource to be rendered within a synthetic spread.
    var spread: EPUBRendition.Spread?
    /// Indicates that a resource is encrypted/obfuscated and provides relevant information for decryption.
    var encryption: EPUBEncryption?
}

private val containsKey = "contains"
private val layoutKey = "layout"
private val mediaOverlayKey = "media-overlay"
private val overflowKey = "overflow"
private val spreadKey = "spread"
private val encryptedKey = "encrypted"

/**
 * TODO: extension & inheritance
 **/
