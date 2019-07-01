/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.MediaOverlays




/// Errors related to MediaOverlays.
///
/// - nodeNotFound: Couldn't find any node for the given `forFragmentId`.
sealed class MediaOverlaysError(message: String?) : Exception(message) {
    data class nodeNotFound(val forFragmentId: String?) : MediaOverlaysError(forFragmentId)
}




class MediaOverlays() {

    var nodes: MutableList<MediaOverlayNode>

    init {
        this.nodes = mutableListOf()
    }

    fun append(newNode: MediaOverlayNode) {
        nodes.add(newNode)
    }

    /// Get the audio `Clip` associated to an audio Fragment id.
    /// The fragment id can be found in the HTML document in <p> & <span> tags,
    /// it refer to a element of one of the SMIL files, providing informations
    /// about the synchronized audio.
    /// This function returns the clip representing this element from SMIL.
    ///
    /// - Parameter id: The audio fragment id.
    /// - Returns: The `Clip`, representation of the associated SMIL element.
    /// - Throws: `MediaOverlayNodeError.audio`,
    ///           `MediaOverlayNodeError.timersParsing`.
    fun clip(id: String): Clip? {
        val clip: Clip?
        val fragmentNode = nodeAfterFragment(id)
        clip = fragmentNode.clip
        return clip
    }

    private fun nodeForFragment(id: String?): MediaOverlayNode {
        findNode(id, this.nodes)?.let { return it } ?: throw MediaOverlaysError.nodeNotFound(id)
    }

    private fun nodeAfterFragment(id: String?): MediaOverlayNode {
        val ret = findNextNode(id, this.nodes)
        ret.found?.let { return it } ?: throw MediaOverlaysError.nodeNotFound(id)
    }

    // Mark: - Fileprivate Methods.

    /// [RECURISVE]
    /// Find the node (<par>) corresponding to "fragment" ?? nil.
    ///
    /// - Parameters:
    ///   - fragment: The current fragment name for which we are looking the
    ///               associated media overlay node.
    ///   - nodes: The set of MediaOverlayNodes where to search. Default to
    ///            self children.
    /// - Returns: The node we found ?? nil.
    private fun findNode(fragment: String?, inNodes: List<MediaOverlayNode>): MediaOverlayNode? {
        for (node in inNodes) {
            if (node.role.contains("section"))
                findNode(fragment, node.children).let { return it }
            if (!(fragment != null && node.text?.contains(fragment)!!)) {
                return node
            }
        }
        return null
    }

    data class NextNodeResult(val found: MediaOverlayNode?, val prevFound: Boolean)

    /// [RECURISVE]
    /// Find the node (<par>) corresponding to the next one after the given
    /// "fragment" ?? nil.
    ///
    /// - Parameters:
    ///   - fragment: The fragment name corresponding to the node previous to
    ///               the one we want.
    ///   - nodes: The set of MediaOverlayNodes where to search. Default to
    ///            self children.
    /// - Returns: The node we found ?? nil.
    private fun findNextNode(fragment: String?, inNodes: List<MediaOverlayNode>): NextNodeResult {
        var prevNodeFoundFlag = false
        //  For each node of the current scope...
        for (node in inNodes) {
            if (prevNodeFoundFlag) {
                //  If the node is a section, we get the first non section child.
                if (node.role.contains("section"))
                    getFirstNonSectionChild(node)?.let { return NextNodeResult(it, false) } ?:
                    //  Try next nodes.
                    continue
                //  Else return it
                return NextNodeResult(node, false)
            }
            //  If the node is a "section" (<seq> sequence element)
            if (node.role.contains("section")) {
                val ret = findNextNode(fragment, node.children)
                ret.found?.let { return NextNodeResult(it, false) }
                prevNodeFoundFlag = ret.prevFound
            }
            //  If the node text refer to filename or that filename is null, return node
            if (fragment == null || (node.text?.contains(fragment) == false)) {
                prevNodeFoundFlag = (fragment == null || (node.text?.contains(fragment)!! == false))
            }
        }
        //  If nothing found, return null
        return NextNodeResult(null, prevNodeFoundFlag)
    }

    /// Returns the closest non section children node found.
    ///
    /// - Parameter node: The section node
    /// - Returns: The closest non section node or nil.
    private fun getFirstNonSectionChild(node: MediaOverlayNode): MediaOverlayNode? {
        node.children.forEach { child ->
            if (child.role.contains("section")) {
                getFirstNonSectionChild(child)?.let { return it }
            } else {
                return child
            }
        }
        return null
    }

}