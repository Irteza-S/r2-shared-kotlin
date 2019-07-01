/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.MediaOverlays

import java.net.URL

/// The publicly accessible struct.
/// Clip is the representation of a MediaOverlay file fragment. A clip represent
/// the synchronized audio for a piece of text, it has a file where its data
/// belong, start/end times relatives to this file's data and  a duration
/// calculated from the aforementioned values.
class Clip {
    /// The relative URL.
    var relativeUrl: URL? = null
    /// The relative fragmentId.
    var fragmentId: String? = null
    /// Start time in seconds.
    var start: Double? = null
    /// End time in seconds.
    var end: Double? = null
    /// Total clip duration in seconds (end - start).
    var duration: Double? = null

    init {

    }
}

/// The Error enumeration of the MediaOverlayNode class.
///
/// - audio: Couldn't generate a proper clip due to erroneous audio property.
/// - timersParsing: Couldn't generate a proper clip due to timersParsing failure.
enum class MediaOverlayNodeError (val rawValue: String)  {
    audio("Audio Error"), timersParsing("Timers parsing error");

    companion object {
        operator fun invoke(rawValue: String) = values().firstOrNull { it.rawValue == rawValue }
    }
}

/// Represents a MediaOverlay XML node.
class MediaOverlayNode(var _text: String? = null, var _clip: Clip? = null) {

    //From iOS
    var text: String?
    var clip: Clip?


    var role: List<String>
    var children: List<MediaOverlayNode>

    init {
        this.text = _text
        this.clip = _clip
        this.clip?.fragmentId = this.fragmentId()
        this.role = listOf()
        this.children = listOf()
    }

    private fun fragmentId(): String? {
        return text?.let {
            it.split('#').last()
        } ?: run {
            null
        }
    }

    /**
     * Todo: Not in iOS anymore
     */
//    fun clip(): Clip {
//        var newClip = Clip()
//
//        val audioString = this.audio ?: throw Exception("audio")
//        val audioFileString = audioString.split('#').first()
//        val audioFileUrl = URL(audioFileString)
//
//        newClip.relativeUrl = audioFileUrl
//        val times = audioString.split('#').last()
//        newClip = parseTimer(times, newClip)
//        newClip.fragmentId = fragmentId()
//        return newClip
//    }
//
//    private fun parseTimer(times: String, clip: Clip): Clip {
//        //  Remove "t=" prefix
//        val netTimes = times.removeRange(0, 2)
//        val start = try {
//            netTimes.split(',').first()
//        } catch (e: Exception) {
//            null
//        }
//        val end = try {
//            netTimes.split(',').last()
//        } catch (e: Exception) {
//            null
//        }
//        val startTimer = start?.toDoubleOrNull() ?: throw Exception("timersParsing")
//        val endTimer = end?.toDoubleOrNull() ?: throw Exception("timerParsing")
//        clip.start = startTimer
//        clip.end = endTimer
//        clip.duration = endTimer - startTimer
//        return clip
//    }

}