/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.DRM

import java.io.Serializable


class Drm(var brand: Brand) : Serializable {

    var scheme: Scheme

    var profile: String? = null
    var license: DrmLicense? = null

    enum class Brand(var value: String) : Serializable {
        Lcp("LCP")
    }

    enum class Scheme(var value: String) : Serializable {
        Lcp("http://readium.org/2014/01/lcp")
    }

    init {
        when (brand) {
            Brand.Lcp -> scheme = Scheme.Lcp
        }
    }

}


/**
 * TODO : Change DATA object
 */
/*
/// Shared DRM behavior for a particular license/publication.
/// DRMs can be very different beasts, so DRMLicense is not meant to be a generic interface for all DRM behaviors (eg. loan return). The goal of DRMLicense is to provide generic features that are used inside Readium's projects directly. For example, data decryption or copy of text selection in the navigator.
/// If there's a need for other generic DRM features, it can be implemented as a set of adapters in the client app, to cater to the interface's needs and capabilities.
interface DRMLicense {

    /// Encryption profile, if available.
    val encryptionProfile: String?
    /// Depichers the given encrypted data to be displayed in the reader.
    fun decipher(data: Data) : Data?
    /// Returns whether the user can copy extracts from the publication.
    val canCopy: Boolean
    /// Processes the given text to be copied by the user.
    /// For example, you can save how much characters was copied to limit the overall quantity.
    /// - Returns: The (potentially modified) text to put in the user clipboard, or nil if the user is not allowed to copy it.
    fun copy(text: String) : String?
}
 */

/**
 * TODO : DRMLicense interface
 */
/*
val DRMLicense.encryptionProfile: String?
    get() = null
val DRMLicense.canCopy: Boolean
    get() = true

fun DRMLicense.copy(text: String) : String? =
        if (canCopy) text else null
*/

