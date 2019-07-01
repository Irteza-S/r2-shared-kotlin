/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.OPDS

import java.io.Serializable
import java.util.*


data class OpdsMetadata(var _title: String) : Serializable {
    var title: String
    var numberOfItem: Int? = null
    var itemsPerPage: Int? = null
    var currentPage: Int? = null
    var modified: Date? = null
    var position: Int? = null
    var rdfType: String? = null

    init {
        this.title = _title
    }
}
