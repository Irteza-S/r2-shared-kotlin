package org.readium.r2.shared.Publication.WebPublication

import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.parseArray
import org.readium.r2.shared.Publication.WebPublication.Metadata.Metadata

open class WebPublication {

    var context: MutableList<String>
    var metadata: Metadata
    var links: MutableList<Link>
    var readingOrder: List<Link>
    var resources: List<Link>
    var tableOfContents: List<Link>
    var otherCollections: MutableList<PublicationCollection>
    /**
     * Todo: complete manifest's getter
     */
    var manifest: ByteArray? = null


    constructor(context: MutableList<String> = mutableListOf(), metadata: Metadata, links: MutableList<Link> = mutableListOf(), readingOrder: MutableList<Link> = mutableListOf(), resources: MutableList<Link> = mutableListOf(), tableOfContents: MutableList<Link> = mutableListOf(), otherCollections: MutableList<PublicationCollection> = mutableListOf()) {
        this.context = context
        this.metadata = metadata
        this.links = links
        this.readingOrder = readingOrder
        this.resources = resources
        this.tableOfContents = tableOfContents
        this.otherCollections = otherCollections
    }

    /**
     * TODO: not sure about links/readingOrder/resources + otherCollections
     */
    /// Parses a Readium Web Publication Manifest.
    /// https://readium.org/webpub-manifest/schema/publication.schema.json
    constructor(_json: Any, normalizeHref: (String) -> String = { it }) {
        when(_json) {
            is JSONObject -> {
                try {
                    this.context = parseArray<String>(_json.getJSONArray("@context"), true).toMutableList()
                    this.metadata = Metadata(_json.getJSONObject("metadata"), normalizeHref)
                    this.otherCollections = mutableListOf()
                    this.links = parseArray<Link>(_json.getJSONArray("links"), true).filter { it.rels.isNotEmpty()}.toMutableList()
                    // `readerOrder` used to be `spine`, so we parse `spine` as a fallback.
                    this.readingOrder = if(_json.has("readingOrder")) parseArray<Link>(_json.getJSONArray("readingOrder"), true) else parseArray<Link>(_json.getJSONArray("spine"), true)
                    this.resources = parseArray<Link>(_json.getJSONArray("resources"), true)
                    this.tableOfContents = parseArray<Link>(_json.getJSONArray("toc"), true)
                    // Parses sub-collections from remaining JSON properties.
                    this.otherCollections = parseArray<PublicationCollection>(_json.getJSONArray(""), true).toMutableList()
                }   catch (e : Exception) {
                    throw JSONError.malformedJSON
                }
            }
            else -> {
                throw JSONError.malformedJSON
            }
        }
    }

    /// Replaces the links for the first found subcollection with the given role.
    /// If none is found, creates a new subcollection.
    fun setCollectionLinks(links: MutableList<Link>, role: String) {
        val collection = otherCollections.firstOrNull { it.role == role }
        if (collection != null) {
            collection.links = links
        } else {
            otherCollections.add(PublicationCollection(role, links))
        }
    }

}



