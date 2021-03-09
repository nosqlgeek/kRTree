package org.nosqlgeek.krtree

/**
 * Create a non-empty flot array of a specific size
 */
fun createFloatArray(size : Int) : Array<Float> {

    var result = mutableListOf<Float>()

    for (i in 0..size-1) {
        result.add(-1f)
    }

    return result.toTypedArray();
}