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
import java.io.Serializable


data class Group(val title: String) : Serializable {
    var metadata: OpdsMetadata
    var links: List<Link>
    var publications: List<Publication>
    var navigation: List<Link>

    init {
        this.metadata = OpdsMetadata(title)
        this.links = listOf()
        this.publications = listOf()
        this.navigation = listOf()
    }

}
