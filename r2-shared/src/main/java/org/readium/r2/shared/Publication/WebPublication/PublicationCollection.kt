package org.readium.r2.shared.Publication.WebPublication

import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.parseArray
import java.lang.Exception


/// Core Collection Model
/// https://readium.org/webpub-manifest/schema/subcollection.schema.json
/// Can be used as extension point in the Readium Web Publication Manifest.
class  PublicationCollection {

    /// JSON key used to reference this collection in its parent.
    var role: String = ""
    var metadata: List<Pair<String, Any?>> = listOf()
    var links: List<Link> = listOf()
    var otherCollections: List<PublicationCollection> = listOf()


    /**
     * TODO: Complete OtherCollections
     */
     constructor(role: String, _json: Any, normalizeHref: (String) -> String = { it }) {
        this.role = role
        when (_json) {
            is List<*> -> {
                this.links = parseArray<Link>(_json, true)
            }
            is JSONObject -> {
                try {
                    this.metadata = if(_json.has("metadata")) parseArray<Pair<String, Any?>>(_json.getJSONArray("metadata"), true) else listOf<Pair<String, Any?>>()
                    this.links = parseArray<Link>(_json.getJSONArray("links"), true)
                    this.otherCollections = parseArray<PublicationCollection>(_json.getJSONArray(""))
                }   catch (e : Exception) {
                    throw JSONError.malformedJSON
                }
            }
            else -> throw JSONError.malformedJSON
        }
    }

}