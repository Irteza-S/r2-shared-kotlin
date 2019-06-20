package org.readium.r2.shared.Publication

enum class ReadingProgression (val rawValue: String) {
    rtl("rtl"), ltr("ltr"), auto("auto");
    companion object {
        operator fun invoke(rawValue: String) = ReadingProgression.values().firstOrNull { it.rawValue == rawValue }
    }
}

enum class ContentLayoutStyle (val rawValue: String) {
    rtl("rtl"), ltr("ltr"), cjkVertical("cjk-vertical"), cjkHorizontal("cjk-horizontal");

    companion object {
        operator fun invoke(rawValue: String) = ContentLayoutStyle.values().firstOrNull { it.rawValue == rawValue }
    }


    constructor(language: String, readingProgression: ReadingProgression? = null) {
        val language: String = {
            val code = language.split('-').firstOrNull()
            if (code != null) {
                code
            }
            language
        }()
        when (language.toLowerCase()) {
            in "ar", "fa", "he" -> rtl
            // Any Chinese: zh-*-*
            in "zh", "ja", "ko" -> if ((readingProgression == rtl)) cjkVertical else cjkHorizontal
            else -> if ((readingProgression == rtl)) rtl else ltr
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
