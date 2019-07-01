package org.readium.r2.shared.Publication


enum class ReadingProgression (val rawValue: String) {
    rtl("rtl"), ltr("ltr"), auto("auto");
    companion object {
        operator fun invoke(rawValue: String) = ReadingProgression.values().firstOrNull { it.rawValue == rawValue }
    }
}


/**
 * TODO: Let to Aferdita
 */
sealed class ContentLayoutStyle (var language: String, var _readingProgression: ReadingProgression? = null) {

    object rtl : ContentLayoutStyle("rtl")
    object ltr : ContentLayoutStyle("ltr")
    object cjkVertical : ContentLayoutStyle("cjk-vertical")
    object cjkHorizontal : ContentLayoutStyle("cjk-horizontal");

    init {
        val lang = language.split( "-").firstOrNull()?.let {
            it
        } ?: run { language }

        when(lang.toLowerCase()) {
            "ar", "fa", "he" -> rtl
            // Any Chinese: zh-*-*
            "zh", "ja", "ko" -> if ((_readingProgression == ReadingProgression.rtl)) cjkVertical else cjkHorizontal
            else -> if ((readingProgression == ReadingProgression.rtl)) rtl else ltr
        }
    }


    val readingProgression: ReadingProgression
        get() {
            when (this) {
                rtl, cjkVertical -> return ReadingProgression.rtl
                ltr, cjkHorizontal -> return ReadingProgression.ltr
            }
        }

}
