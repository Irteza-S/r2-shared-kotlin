package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB

import org.readium.r2.shared.Publication.WebPublication.Link.Link
import org.readium.r2.shared.Publication.WebPublication.WebPublication

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

/**
 * TODO: inheritance
 */

var WebPublication.pageList : MutableList<Link>
    get() {
         return otherCollections.first { it.role == pageListKey }.links.toMutableList()
    }
    set (newValue) {
        setCollectionLinks(newValue, pageListKey)
    }

var WebPublication.landmarks : MutableList<Link>
    get() {
        return otherCollections.first { it.role == landmarksKey }.links.toMutableList()
    }
    set (newValue) {
        setCollectionLinks(newValue, landmarksKey)
    }

var WebPublication.listOfAudioFiles : MutableList<Link>
    get() {
        return otherCollections.first { it.role == loaKey }.links.toMutableList()
    }
    set (newValue) {
        setCollectionLinks(newValue, loaKey)
    }

var WebPublication.listOfIllustrations : MutableList<Link>
    get() {
        return otherCollections.first { it.role == loiKey }.links.toMutableList()
    }
    set (newValue) {
        setCollectionLinks(newValue, loiKey)
    }

var WebPublication.listOfTables : MutableList<Link>
    get() {
        return otherCollections.first { it.role == lotKey }.links.toMutableList()
    }
    set (newValue) {
        setCollectionLinks(newValue, lotKey)
    }

var WebPublication.listOfVideos : MutableList<Link>
    get() {
        return otherCollections.first { it.role == lovKey }.links.toMutableList()
    }
    set (newValue) {
        setCollectionLinks(newValue, lovKey)
    }