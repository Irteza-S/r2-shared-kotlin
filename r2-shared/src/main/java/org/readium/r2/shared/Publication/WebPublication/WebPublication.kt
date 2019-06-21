package org.readium.r2.shared.Publication.WebPublication

import org.readium.r2.shared.Publication.JSONDictionary
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
     * OtherCollections
     */
    /// Parses a Readium Web Publication Manifest.
    /// https://readium.org/webpub-manifest/schema/publication.schema.json
    constructor(_json: Any, normalizeHref: (String) -> String = { it }) {
        var json = JSONDictionary(_json)
        if(json != null) {
            this.context = parseArray(json.pop("@context"), true)
            this.metadata = Metadata(json.pop("metadata"), normalizeHref)
            this.otherCollections = mutableListOf()
            this.links = mutableListOf(Link(json.pop("links"), normalizeHref)).filter { it.rels.isNotEmpty()}.toMutableList()
            // `readerOrder` used to be `spine`, so we parse `spine` as a fallback.
            this.readingOrder = mutableListOf(Link(json.pop("readingOrder") ?: json.pop("spine"), normalizeHref)).filter { it.type!=null }
            this.resources = mutableListOf(Link(json.pop("resources"), normalizeHref)).filter { it.type!=null }
            this.tableOfContents = mutableListOf(Link(json.pop("toc"), normalizeHref))
            // Parses sub-collections from remaining JSON properties.
            //this.otherCollections = mutableListOf(PublicationCollection(json.json, normalizeHref))
        } else throw JSONError.parsing(this)
    }


    /**
     * TODO : Else
     */
    /// Replaces the links for the first found subcollection with the given role.
    /// If none is found, creates a new subcollection.
    fun setCollectionLinks(links: MutableList<Link>, role: String) {
        val collection = otherCollections.firstOrNull { it.role == role }
        if (collection != null) {
            collection.links = links
        } else {
            //otherCollections.add(PublicationCollection(role, links))
        }
    }

    /**
     * TODO : var manifest
     */
}



