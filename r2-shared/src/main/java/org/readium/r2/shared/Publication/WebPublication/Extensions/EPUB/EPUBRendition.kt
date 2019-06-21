package org.readium.r2.shared.Publication.WebPublication.Extensions.EPUB


/// Indicates that a resource is encrypted/obfuscated and provides relevant information for decryption.
data class EPUBRendition(var _laoyout: Layout? = null, var _orientation : Orientation? = null, var _overflow: Overflow ? = null, var _spread: Spread? = null) {

    // Hints how the layout of the resource should be presented.
    var layout: Layout? = null
    // Suggested orientation for the device when displaying the linked resource.
    var orientation: Orientation? = null
    // Suggested method for handling overflow while displaying the linked resource.
    var overflow: Overflow? = null
    // Indicates the condition to be met for the linked resource to be rendered within a synthetic spread.
    var spread: Spread? = null

    init {
        this.layout = _laoyout
        this.orientation = _orientation
        this.overflow = _overflow
        this.spread = _spread
    }


    /// Hints how the layout of the resource should be presented.
    sealed class Layout (epub: String, fallback: Layout? = null) {
        //Fixed Layout
        object fixed : Layout("fixed")
        // Apply dynamic pagination when rendering.
        object reflowable : Layout("reflowable")

        init {
            when (epub) {
                "reflowable" -> reflowable
                "pre-paginated" -> fixed
                else -> {
                    fallback ?: reflowable
                }
            }
        }
    }

    /// Suggested orientation for the device when displaying the linked resource.
    sealed class Orientation (epub: String, fallback: Orientation? = null) {
        // Specifies that the Reading System can determine the orientation to rendered the spine item in.
        object auto : Orientation("auto")
        // Specifies that the given spine item is to be rendered in landscape orientation.
        object landscape : Orientation("landscape")
        // Specifies that the given spine item is to be rendered in portrait orientation.
        object portrait : Orientation("portrait")

        init {
            when (epub) {
                "landscape" -> landscape
                "portrait" -> portrait
                "auto" -> auto
                else -> {
                    fallback ?: auto
                }
            }
        }
    }

    /// Suggested method for handling overflow while displaying the linked resource.
    sealed class Overflow (epub: String, fallback: Overflow? = null) {
        // Indicates no preference for overflow content handling by the Author.
        object auto : Overflow("auto")
        // Indicates the Author preference is to dynamically paginate content overflow.
        object paginated : Overflow("paginated")
        // Indicates the Author preference is to provide a scrolled view for overflow content, and each spine item with this property is to be rendered as separate scrollable document.
        object scrolled : Overflow("scrolled")
        // Indicates the Author preference is to provide a scrolled view for overflow content, and that consecutive spine items with this property are to be rendered as a continuous scroll.
        object scrolledContinuous : Overflow("scrolledContinuous")

        init {
            when (epub) {
                "auto" -> auto
                "paginated" -> paginated
                "scrolled-doc" -> scrolled
                "scrolled-continuous" -> scrolledContinuous
                else -> {
                    fallback ?: auto
                }
            }
        }
    }

    /// Indicates the condition to be met for the linked resource to be rendered within a synthetic spread.
    sealed class Spread (epub: String, fallback: Spread? = null) {
        // Specifies the Reading System can determine when to render a synthetic spread for the readingOrder item.
        object auto : Spread("auto")
        // Specifies the Reading System should render a synthetic spread for the readingOrder item in both portrait and landscape orientations.
        object both : Spread("both")
        // Specifies the Reading System should not render a synthetic spread for the readingOrder item.
        object none : Spread("none")
        // Specifies the Reading System should render a synthetic spread for the readingOrder item only when in landscape orientation.
        object landscape : Spread("landscape")

        init {
            when (epub) {
                "none" -> none
                "auto" -> auto
                "landscape" -> landscape
                // `portrait` is deprecated and should fallback to `both`.
                // See. https://readium.org/architecture/streamer/parser/metadata#epub-3x-11
                "both", "portrait" -> both
                else -> {
                    fallback ?: auto
                }
            }
        }
    }


}