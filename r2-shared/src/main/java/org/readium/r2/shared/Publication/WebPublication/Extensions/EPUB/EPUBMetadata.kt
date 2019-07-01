package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB

import org.json.JSONObject
import org.readium.r2.shared.Publication.WebPublication.Metadata.Metadata

class EPUBMetadata(json : JSONObject): Metadata(json) {

    private val renditionKey = "rendition"
    var rendition: EPUBRendition?
        get() {
            /**
             * Todo: to complete
             */
            return null
        }
        set(newValue) {
            val json = newValue?.getJSON()
            if (json != null && json.length() != 0) {
                otherMetadata?.putOpt(renditionKey, json)
            } else {
                this.rendition = otherMetadata?.remove(renditionKey) as EPUBRendition
            }
        }

}