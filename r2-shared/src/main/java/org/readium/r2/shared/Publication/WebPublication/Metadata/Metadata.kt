/*
 * Module: r2-shared-kotlin
 * Developers: Aferdita Muriqi, Clément Baumann
 *
 * Copyright (c) 2018. Readium Foundation. All rights reserved.
 * Use of this source code is governed by a BSD-style license which is detailed in the
 * LICENSE file present in the project repository where this source code is maintained.
 */

package org.readium.r2.shared.Publication.WebPublication.Metadata

import org.joda.time.DateTime
import org.json.JSONObject
import org.readium.r2.shared.Publication.*
import java.io.Serializable


/// Collection type used for collection/series metadata.
/// For convenience, the JSON schema reuse the Contributor's definition.
typealias Collection = Contributor


open class Metadata : Serializable {

    var identifier: String? //URI
    var type: String? = null // URI (@type)
    var localizedTitle: String = ""
    var localizedSubtitle: String = ""
    var title: String
        get() = localizedSubtitle
        set(newValue) {
            this.localizedTitle = newValue
        }

    var modified: DateTime? = null
    var published: DateTime? = null
    var languages: List<String> = listOf()
    var sortAs: String?
    var subjects: List<Subject> = listOf()
    var authors: List<Contributor> = listOf()
    var translators: List<Contributor> = listOf()
    var editors: List<Contributor> = listOf()
    var artists: List<Contributor> = listOf()
    var illustrators: List<Contributor> = listOf()
    var letterers: List<Contributor> = listOf()
    var pencilers: List<Contributor> = listOf()
    var colorists: List<Contributor> = listOf()
    var inkers: List<Contributor> = listOf()
    var narrators: List<Contributor> = listOf()
    var contributors: List<Contributor> = listOf()
    var publishers: List<Contributor> = listOf()
    var imprints: List<Contributor> = listOf()
    /// WARNING: This contains the reading progression as declared in the publication, so it might be `auto`. To lay out the content, use `publication.contentLayout.readingProgression` to get the calculated reading progression from the declared direction and the language.
    var readingProgression: ReadingProgression?
    var description: String?
    var duration: Double?
    var numberOfPages: Int?
    var belongsToCollections: List<Collection> = listOf()
    var belongsToSeries: List<Collection> = listOf()


    /// Additional properties for extensions.
    var otherMetadata: JSONObject?
        get() {
            return otherMetadataJSON
        }
        set(newValue) {
            otherMetadataJSON = newValue
        }
    // Trick to keep the struct equatable despite [String: Any]
    var otherMetadataJSON: JSONObject? = null


    /**
     * TODO: Pop + localizedTitle + belongsToCollection & belongsToSeries
     */
    constructor(json: JSONObject, normalizeHref: (String) -> String = { it }) {
        try {
            this.title = if (json.has("title")) json.getString("title") else throw JSONError.malformedJSON
            this.identifier = json.getString("identifier")
            this.type = json.getString("type")
            this.localizedTitle = this.title
            this.localizedSubtitle = json.getString("subtitle")
            this.modified = DateTime(json.getString("modified"))
            this.published = DateTime(json.getString("published"))
            this.languages = parseArray<String>(json.getJSONArray("language"), true)
            this.sortAs = json.getString("sortAs")
            this.subjects = parseArray<Subject>(json.getJSONArray("subject"), true)
            this.authors = parseArray<Contributor>(json.getJSONArray("author"), true)
            this.translators = parseArray<Contributor>(json.getJSONArray("translator"), true)
            this.editors = parseArray<Contributor>(json.getJSONArray("editor"), true)
            this.artists = parseArray<Contributor>(json.getJSONArray("artist"), true)
            this.illustrators = parseArray<Contributor>(json.getJSONArray("illustrator"), true)
            this.letterers = parseArray<Contributor>(json.getJSONArray("letterer"), true)
            this.pencilers = parseArray<Contributor>(json.getJSONArray("penciler"), true)
            this.colorists = parseArray<Contributor>(json.getJSONArray("colorist"), true)
            this.inkers = parseArray<Contributor>(json.getJSONArray("inker"), true)
            this.narrators = parseArray<Contributor>(json.getJSONArray("narrator"), true)
            this.contributors = parseArray<Contributor>(json.getJSONArray("contributor"), true)
            this.publishers = parseArray<Contributor>(json.getJSONArray("publisher"), true)
            this.imprints = parseArray<Contributor>(json.getJSONArray("imprint"), true)
            this.readingProgression = if (json.has("readingProgression")) parseRaw<ReadingProgression>(json.get("readingProgression")) else ReadingProgression.auto
            this.description = json.getString("description")
            this.duration = parsePositiveDouble(json.getDouble("duration"))
            this.numberOfPages = parsePositive(json.getInt("numberOfPages")) as Int
            val belongsTo = json.getJSONObject("belongsTo")
            this.belongsToCollections = listOf<Contributor>(Contributor(belongsTo?.get("collection") as String, normalizeHref))
            this.belongsToSeries = listOf<Contributor>(Contributor(belongsTo?.get("series") as String, normalizeHref))
            this.otherMetadataJSON = json
            }   catch (e: Exception) {
                throw JSONError.malformedJSON
            }
    }



//    //removed in iOS
//    /// The structure used for the serialisation.
//    var multilanguageTitle: MultilanguageString? = null
//    // Contributors.
//    var direction: String = PageProgressionDirection.default.name
//    var publicationDate: String? = null
//    var rendition: Rendition = Rendition()
//    var source: String? = null
//    var epubType: List<String> = listOf()
//    var rights: String? = null
//    var rdfType: String? = null
//
//    var belongsTo: BelongsTo? = null
//
//
//    //removed in iOS
//    fun titleForLang(key: String): String? = multilanguageTitle?.multiString?.get(key)
//
//    fun writeJSON(): JSONObject {
//        val obj = JSONObject()
//        obj.putOpt("languages", getStringArray(languages as List<Any>))
//        obj.putOpt("publicationDate", publicationDate)
//        obj.putOpt("identifier", identifier)
//        obj.putOpt("modified", modified)
//        obj.putOpt("title", title)
//        obj.putOpt("rendition", rendition.getJSON())
//        obj.putOpt("source", source)
//        obj.putOpt("rights", rights)
//        tryPut(obj, subjects, "subjects")
//        tryPut(obj, authors, "authors")
//        tryPut(obj, translators, "translators")
//        tryPut(obj, editors, "editors")
//        tryPut(obj, artists, "artists")
//        tryPut(obj, illustrators, "illustrators")
//        tryPut(obj, letterers, "letterers")
//        tryPut(obj, pencilers, "pencilers")
//        tryPut(obj, colorists, "colorists")
//        tryPut(obj, inkers, "inkers")
//        tryPut(obj, narrators, "narrators")
//        tryPut(obj, contributors, "contributors")
//        tryPut(obj, publishers, "publishers")
//        tryPut(obj, imprints, "imprints")
//        return obj
//    }


    fun contentLayoutStyle(langType: LangType, pageDirection: String?) : ContentLayoutStyle {

        when(langType) {
            LangType.afh -> return ContentLayoutStyle.rtl
            LangType.cjk -> {
                if (pageDirection == ContentLayoutStyle.rtl.name)
                    return ContentLayoutStyle.cjkv
                else
                    return ContentLayoutStyle.cjkh
            }
            else -> {
                if (pageDirection == ContentLayoutStyle.rtl.name)
                    return ContentLayoutStyle.rtl
                else
                    return ContentLayoutStyle.ltr
            }
        }
    }

}

/**
 * Todo: not in Swift anymore
 */
//fun parseMetadata(metadataDict: JSONObject): Metadata {
//    val m = Metadata()
//    if (metadataDict.has("title")) {
//        m.multilanguageTitle = MultilanguageString()
//        m.multilanguageTitle?.singleString = metadataDict.getString("title")
//    }
//    if (metadataDict.has("identifier")) {
//        m.identifier = metadataDict.getString("identifier")
//    }
//    if (metadataDict.has("@type")) {
//        m.rdfType = metadataDict.getString("@type")
//    } else if (metadataDict.has("type")) {
//        m.rdfType = metadataDict.getString("type")
//    }
//    if (metadataDict.has("modified")) {
//        m.modified = DateTime(metadataDict.getString("modified")).toDate()
//    }
//    if (metadataDict.has("author")) {
//        m.authors.addAll(parseContributors(metadataDict.get("author")))
//    }
//    if (metadataDict.has("translator")) {
//        m.translators.addAll(parseContributors(metadataDict.get("translator")))
//    }
//    if (metadataDict.has("editor")) {
//        m.editors.addAll(parseContributors(metadataDict.get("editor")))
//    }
//    if (metadataDict.has("artist")) {
//        m.artists.addAll(parseContributors(metadataDict.get("artist")))
//    }
//    if (metadataDict.has("illustrator")) {
//        m.illustrators.addAll(parseContributors(metadataDict.get("illustrator")))
//    }
//    if (metadataDict.has("letterer")) {
//        m.letterers.addAll(parseContributors(metadataDict.get("letterer")))
//    }
//    if (metadataDict.has("penciler")) {
//        m.pencilers.addAll(parseContributors(metadataDict.get("penciler")))
//    }
//    if (metadataDict.has("colorist")) {
//        m.colorists.addAll(parseContributors(metadataDict.get("colorist")))
//    }
//    if (metadataDict.has("inker")) {
//        m.inkers.addAll(parseContributors(metadataDict.get("inker")))
//    }
//    if (metadataDict.has("narrator")) {
//        m.narrators.addAll(parseContributors(metadataDict.get("narrator")))
//    }
//    if (metadataDict.has("contributor")) {
//        m.contributors.addAll(parseContributors(metadataDict.get("contributor")))
//    }
//    if (metadataDict.has("publisher")) {
//        m.publishers.addAll(parseContributors(metadataDict.get("publisher")))
//    }
//    if (metadataDict.has("imprint")) {
//        m.imprints.addAll(parseContributors(metadataDict.get("imprint")))
//    }
//    if (metadataDict.has("published")) {
//        m.publicationDate = metadataDict.getString("published")
//    }
//    if (metadataDict.has("description")) {
//        m.description = metadataDict.getString("description")
//    }
//    if (metadataDict.has("source")) {
//        m.source = metadataDict.getString("source")
//    }
//    if (metadataDict.has("rights")) {
//        m.rights = metadataDict.getString("rights")
//    }
//    if (metadataDict.has("subject")) {
//
//        when(val subjectDictUntyped = metadataDict.get("subject")) {
//            is String -> {
//                val subject = Subject()
//                subject.name = subjectDictUntyped
//                m.subjects.add(subject)
//            }
//            is Array<*> -> {
//                for(i in 0 until subjectDictUntyped.size - 1) {
//                    val subject = Subject()
//                    subject.name = subjectDictUntyped[i] as String
//                    m.subjects.add(subject)
//                }
//            }
//            is JSONArray -> {
//                val subjDict = metadataDict.getJSONArray("subject")
//                for (i in 0 until subjDict.length()) {
//                    when(val subObject = subjDict.get(i)){
//                        is String -> {
//                            val subject = Subject()
//                            subject.name = subObject
//                            m.subjects.add(subject)
//                        }
//                        is JSONObject->{
//                            val sub = subjDict.getJSONObject(i)
//                            val subject = Subject()
//                            if (sub.has("name")) {
//                                subject.name = sub.getString("name")
//                            }
//                            if (sub.has("sort_as")) {
//                                subject.sortAs = sub.getString("sort_as")
//                            }
//                            if (sub.has("scheme")) {
//                                subject.scheme = sub.getString("scheme")
//                            }
//                            if (sub.has("code")) {
//                                subject.code = sub.getString("code")
//                            }
//                            m.subjects.add(subject)
//                        }
//                    }
//                }
//            }
//        }
//
//
//    }
//    if (metadataDict.has("belongs_to")) {
//        val belongsDict = metadataDict.getJSONObject("belongs_to")
//        val belongs = BelongsTo()
//        if (belongsDict.has("series")) {
//
//            if (belongsDict.get("series") is JSONObject) {
//                m.belongsTo?.series?.add(Collection(belongsDict.getString("series")))
//            } else if (belongsDict.get("series") is JSONArray) {
//                val array = belongsDict.getJSONArray("series")
//                for (i in 0 until array.length()) {
//                    val string = array.getString(i)
//                    m.belongsTo?.series?.add(Collection(string))
//                }
//            }
//        }
//
//        if (belongsDict.has("collection")) {
//            when {
//                belongsDict.get("collection") is String -> m.belongsTo?.collection?.add(Collection(belongsDict.getString("collection")))
//                belongsDict.get("collection") is JSONObject -> belongs.series.add(parseCollection(belongsDict.getJSONObject("collection")))
//                belongsDict.get("collection") is JSONArray -> {
//                    val array = belongsDict.getJSONArray("collection")
//                    for (i in 0 until array.length()) {
//                        val obj = array.getJSONObject(i)
//                        belongs.series.add(parseCollection(obj))
//                    }
//                }
//            }
//        }
//        m.belongsTo = belongs
//    }
//
//    if (metadataDict.has("duration")) {
//        m.duration = metadataDict.getInt("duration")
//    }
//    if (metadataDict.has("language")) {
//        if (metadataDict.get("language") is JSONObject) {
//            m.languages.add(metadataDict.getString("language"))
//        } else if (metadataDict.get("language") is JSONArray) {
//            val array = metadataDict.getJSONArray("language")
//            for (i in 0 until array.length()) {
//                val string = array.getString(i)
//                m.languages.add(string)
//            }
//        }
//    }
//
//    return m
//}

enum class LangType {
    cjk, afh, other
}


enum class PageProgressionDirection {
    default,
    ltr,
    rtl
}

enum class ContentLayoutStyle {
    ltr,
    rtl,
    cjkv,
    cjkh;

    companion object {
        fun layout(name: String): ContentLayoutStyle = valueOf(name)
    }
}