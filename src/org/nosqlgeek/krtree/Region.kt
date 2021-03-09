package org.nosqlgeek.krtree
import kotlin.collections.HashSet

/**
 * Implements a Region of an RTree
 */
class Region(val dimension : Int, val min : Point, val max : Point, var parent : Region? = null) {

    init {
        if (min.size != dimension) throw IllegalArgumentException("The minimum vector has the wrong dimension.")
        if (max.size != dimension) throw IllegalArgumentException("The maximum vector has the wrong dimension.")
    }

    //One region can contain sub-regions
    val subRegions = HashSet<Region>()

    //A region might contain coordinates or just sub-regions
    val coords = HashSet<Point>()

    /**
     * Check if this region has a parent
     */
    fun hasParent() : Boolean {
        var result = true
        if (parent == null) result = false
        return result
    }

    /**
     * Add a sub-region to this region
     *
     * @return True if the region belongs into the parent region and could be added
     */
    fun addSubRegion(r : Region) : Boolean {

        //Check if the sub-region fits into this region
        if (r.min.greaterThanOrEqual(this.min) && r.max.smallerThanOrEqual(this.max)) {
            subRegions.add(r)
            r.parent = this
            return true
        } else {
            return false
        }
    }

    /**
     * Add a coordinate to the region
     *
     * @return True if the coordinate belongs to the region and could be added
     */
    fun addCoordinate(c : Point) : Boolean {

        if (c.greaterThanOrEqual(this.min) && c.smallerThanOrEqual(this.max)) {
            coords.add(c)
            return true
        } else {
            return false
        }
    }

    override fun toString(): String {
        return "[%s, %s]".format(min.toString(), max.toString())
    }
}