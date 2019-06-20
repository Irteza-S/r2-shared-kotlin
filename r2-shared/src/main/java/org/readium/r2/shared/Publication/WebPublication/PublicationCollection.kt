package org.readium.r2.shared.Publication.WebPublication

import org.json.JSONArray
import org.json.JSONObject
import org.readium.r2.shared.Publication.JSONDictionary
import org.readium.r2.shared.Publication.JSONError
import org.readium.r2.shared.Publication.WebPublication.Link.Link


/// Core Collection Model
/// https://readium.org/webpub-manifest/schema/subcollection.schema.json
/// Can be used as extension point in the Readium Web Publication Manifest.
class  PublicationCollection {

    /// JSON key used to reference this collection in its parent.
    var role: String = ""
    var metadata: List<Pair<String, Any?>> = listOf()
    var links: List<Link> = listOf()
    var otherCollections: List<PublicationCollection> = listOf()


    /*
    /**TODO otherCollections **/
     constructor(role: String, _json: Any, normalizeHref: (String) -> String = { it }) {
        this.role = role
        var json = JSONDictionary(_json)
        val title = json.pop("title") as String
        if (title == null) {
            throw JSONError.parsing(this)
        }
        when (json) {
            is List<*> -> {
                this.links = listOf<Link>(Link(json, normalizeHref))
            }
            is JSONDictionary -> {
                this.metadata = if(json.pop("metadata") is List<Pair<String, Any?>>) json.pop("metadata") else listOf<Pair<String, Any?>>()
                this.links = listOf<Link>(Link(json.pop("links"), normalizeHref))
                this.otherCollections = listOf<PublicationCollection>()
            }
        }
    }*/

}