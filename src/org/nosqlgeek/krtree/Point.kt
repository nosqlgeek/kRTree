package org.nosqlgeek.krtree
import kotlin.collections.ArrayList

/**
 * A point is just a list of coordinates. Points can have an id. This id allows to reference other data items.
 */
class Point(val dimension : Int, var id : String = "") : ArrayList<Float>(dimension) {

    /**
     * Alternatively allow to pass an array of values over
     */
    constructor(a: Array<Float>, id: String = "") : this(a.size, id){
        this.addAll(a)
    }

    /**
     * Compare two vectors component-wise
     *
     * @return True if this point is smaller or equal than another point
     */
    fun smallerThanOrEqual(another : Point) : Boolean {

        for (i in 0..dimension-1) {
            if (this[i] > another[i]) return false
        }

        return true
    }


    /**
     * Compare two vectors by comparing component-wise
     *
     * @return True if x is greater or equal than y
     */
    fun greaterThanOrEqual(another : Point) : Boolean {

        for (i in 0..dimension-1) {
            if (this[i] < another[i]) return false
        }

        return true
    }

    /**
     * Divide only the given dimension
     */
    fun div(num : Float, dim : Int) : Point {

        var result = this.clone() as Point
        result[dim] = this[dim] / num

        return result
    }

    /**
     * Add a value to a given dimension
     */
    fun plus(num : Float, dim: Int) : Point {

        var result = this.clone() as Point
        result[dim] = this[dim] + num

        return result
    }

    /**
     * Add a value to all dimensions
     */
    fun plus(num : Float) : Point {

        var result = this.clone() as Point

        for ((i, e) in this.withIndex()) {
            result[i] = e + num
        }

        return result
    }
}