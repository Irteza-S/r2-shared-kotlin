package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB

import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.WebPublication.WebPublication

//From iOS

interface EPUBPublication {
    /// Provides navigation to positions in the Publication content that correspond to the locations of page boundaries present in a print source being represented by this EPUB Publication.
    var pageList: MutableList<Link>

    /// Identifies fundamental structural components of the publication in order to enable Reading Systems to provide the User efficient access to them..
    var landmarks: MutableList<Link>

    var listOfAudioFiles: MutableList<Link>
    var listOfIllustrations: MutableList<Link>
    var listOfTables: MutableList<Link>
    var listOfVideos: MutableList<Link>

}

private val pageListKey = "page-list"
private val landmarksKey = "landmarks"
private val loaKey = "loa"
private val loiKey = "loi"
private val lotKey = "lot"
private val lovKey = "lov"


var WebPublication.pageList : List<Link>
    get() {
        val tmp = otherCollections.first { landmarksKey }
    }
    set (value) {

    }