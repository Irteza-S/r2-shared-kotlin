package org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS

/// https://drafts.opds.io/schema/properties.schema.json
interface OPDSProperties {
    var numberOfItems/// Provides a hint about the expected number of items returned.
            : Int?
    var price/// The price of a publication is tied to its acquisition link.
            : OPDSPrice?
    var indirectAcquisition/// Indirect acquisition provides a hint for the expected media type that will be acquired after additional steps.
            : List<OPDSAcquisition>
}
