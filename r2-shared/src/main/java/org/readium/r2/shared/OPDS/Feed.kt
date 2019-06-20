/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.OPDS

import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.Publication
import org.readium.r2.shared.Publication.WebPublication.Metadata.Metadata
import java.io.Serializable
import java.net.URL


data class Feed(val title: String) : Serializable {
    var metadata: OpdsMetadata
    var links: List<Link>
    var facets: List<Facet>
    var groups: List<Group>
    var publications: List<Publication>
    var navigation: List<Link>
    var context: List<String>

    init {
        this.metadata = OpdsMetadata(title)
        this.links = listOf()
        this.facets = listOf()
        this.groups = listOf()
        this.publications = listOf()
        this.navigation = listOf()
        this.context = listOf()
    }

    /// Return a String representing the URL of the searchLink of the feed.
    ///
    /// - Returns: The HREF value of the search link
    internal fun getSearchLinkHref(): String? {
        val searchLink = links.firstOrNull { it.rels.contains("search") as Boolean }
        return searchLink?.href
    }
}

