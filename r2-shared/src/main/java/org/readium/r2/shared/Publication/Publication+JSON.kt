package org.readium.r2.shared.Publication

import java.text.SimpleDateFormat
import java.util.*
//Class imported from iOS

class JSONDictionary {
    var json: MutableList<Pair<String, Any?>>

    /**
     * TODO: Cast?
     */
    constructor(_json: Any? = null) {
        if(_json==null) {
            json = mutableListOf()
        } else {
            json = _json as MutableList<Pair<String, Any?>>
        }
    }
    fun pop(key: String) : Any? {
        json.forEach {
            if(it.first.equals(key)) {
                var tmp = it.copy()
                json.remove(it)
                return tmp
            }
        }
        return null
    }
}



sealed class JSONError : Exception() {
    data class parsing(val v1: Any?) : JSONError()

    val errorDescription: String?
        get() {
            return "errror"
        }
}



// let json = [
///    "multiple": ["hello", "world"]
///    "single": "hello",
/// ]
/// let values1: [String] = parseArray(json["multiple"])
/// let values2: [String] = parseArray(json["single"], allowingSingle: true)
///
/// - Parameter allowingSingle: If true, then allows the parsing of both a single value and an array.
fun <T> parseArray(json: Any?, allowingSingle: Boolean = false) : MutableList<T> {
    val values = json as? MutableList<T>
    var value :T? = null;
    if (values != null) {
        return values
    } else {
        value = json as? T
    }
    if (allowingSingle && value != null) {
        return mutableListOf(value)
    } else {
        return mutableListOf()
    }
}


fun parseDate(jsonDate: Any?) : Date?  {
    if(jsonDate is String) {
        val date = SimpleDateFormat("Y-M-dTH:m:s.SSS").parse(jsonDate)
        return date
    }
    return null
}


/// Parses a numeric value, but returns null if it is not a positive number.
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
 * TODO: Not sure about this
 */
/// Returns the value if not nil, or NSNull.
fun encodeIfNotNull(value: Any?): Any? {
    if(value!=null) {
        return value
    } else return null
}




/// Casting to Double loses precision and fails with integers, eg. json["key"] as? Double.
fun parseDouble(json: Any?) : Double? = java.lang.Double.parseDouble(json as String)
