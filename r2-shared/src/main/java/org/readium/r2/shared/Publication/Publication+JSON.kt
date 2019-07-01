package org.readium.r2.shared.Publication

import org.joda.time.LocalDateTime
import java.text.SimpleDateFormat
import java.util.*


/**
 * Todo: To be checked
 */
/// Parses a numeric value, returns null if it is not a positive number.
fun  parsePositive(jsonNumber: Any?) : Number? {
    try {
        val num = parseDouble(jsonNumber)
        val tmp = num?.compareTo(0)
        if(tmp !=null) {
            if(tmp<0) {
                return num
            }
        }
    } catch (e: NumberFormatException) {
        //input is not a numeric
    }
    return null
}

/**
 * Todo: To be checked
 */
fun parsePositiveDouble(json: Any?) : Double? {
    val double = parseDouble(json)
    if (double == null || double < 0) {
        return null
    }
    return double
}


/**
 * Todo: Complete function
 */
/// enum Example: String {
///     case hello
/// }
/// let json = ["key": "hello"]
/// let value: Example? = parseRaw(json["key"])
fun <T> parseRaw(json: Any?) : T? {
    return null
}



/**
 * Todo: To be checked
 */
/// Casting to Double loses precision and fails with integers, eg. json["key"] as? Double.
fun parseDouble(json: Any?) : Double? = java.lang.Double.parseDouble(json as String)



/// let json = [
///    "multiple": ["hello", "world"]
///    "single": "hello",
/// ]
/// let values1: [String] = parseArray(json["multiple"])
/// let values2: [String] = parseArray(json["single"], allowingSingle: true)
///
/// - Parameter allowingSingle: If true, then allows the parsing of both a single value and an array.
fun <T> parseArray(json: Any?, allowingSingle: Boolean = false) : List<T> {
    val values = json as? List<T>
    var value : Any?
    if (values != null) {
        return values
    } else  value = json as? T
    if (allowingSingle && value != null) {
        return listOf(value)
    } else {
        return listOf()
    }
}
