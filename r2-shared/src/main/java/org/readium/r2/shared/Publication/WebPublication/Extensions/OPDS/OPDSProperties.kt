package org.readium.r2.shared.Publication.WebPublication.Extensions.OPDS


/// OPDS Link Properties Extension
/// https://drafts.opds.io/schema/properties.schema.json
interface OPDSProperties {
    /// Provides a hint about the expected number of items returned.
    var numberOfItems: Int?
    /// The price of a publication is tied to its acquisition link.
    var price: OPDSPrice?
    /// Indirect acquisition provides a hint for the expected media type that will be acquired after additional steps.
    var indirectAcquisition: List<OPDSAcquisition>
}
private val numberOfItemsKey = "numberOfItems"
private val priceKey = "price"
private val indirectAcquisitionKey = "indirectAcquisition"
