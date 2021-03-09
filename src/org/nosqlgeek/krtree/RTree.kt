package org.nosqlgeek.krtree

/**
 * An RTree is a tree of regions
 */
class RTree(dimension : Int, min : Point, max : Point) {

    //The root of the tree
    val root = Region(dimension, min, max)

    /**
     * If the tree has a depth specified, then we are going to pre-populate it with regions that are representing
     * a grid.
     */
    constructor(dimension : Int, min : Point, max: Point, depth : Int) : this(dimension, min, max) {

        buildgrid(root, depth = depth)
    }


    /**
     * Splits a region up into sub-region, whereby the depth is the depth of the resulting R-Tree
     */
    private fun buildgrid(region : Region, dim : Int = 0, depth : Int) {

        //Actual logic
        var lMax = region.max.div(2f, dim);
        var l = Region(region.dimension, region.min, lMax)
        var r = Region(region.dimension, region.min.plus(lMax[dim], dim), region.max)

        region.addSubRegion(l)
        region.addSubRegion(r)

        //Recursion
        var currDim = dim
        var currDepth = depth

        if (currDepth == 1 ) {
            return
        } else {
            if (currDim == root.dimension-1) currDim = 0 else currDim++
            currDepth--
            buildgrid(l, currDim, currDepth)
            buildgrid(r, currDim, currDepth)
        }
    }

    /**
     * Adds a point to the RTree
     */
    fun addPoint(p : Point) : Boolean {

        return addPoint(p, root)
    }

    /**
     * Peforms a simple bounding box query
     */
    fun bBox(box : Region) : Region {

        return bBox(box, root)
    }

    /**
     * Peforms a simple distance query
     */
    fun dist(p : Point, d : Float) : Region {

        val min = p.plus(-d)
        val max = p.plus(d)

        var box = Region(p.dimension, min, max)
        return bBox(box)
    }


    /**
     * Traverse the RTree by assuming that the bounding box fits into at least one of the regions
     *
     * TODO: Allow a bounding box to span multiple regions
     */
    private fun bBox(box : Region, region : Region) : Region {

        if (box.min.greaterThanOrEqual(region.min) && box.max.smallerThanOrEqual(region.max)) {

            box.coords.addAll(region.coords)

            for (r in region.subRegions) {
                bBox(box, r)
            }
        }

        return box
    }

    /**
     * Traverses the RTree by adding the point to the smallest sub-region
     *
     * BTW: If the RTree is a binary tree and describes grid structure, then it would be possible to use it to
     * construct a geo-hash
     */
    private fun addPoint(point : Point, region: Region ) : Boolean {

        if (point.greaterThanOrEqual(region.min) && point.smallerThanOrEqual(region.max)) {

            //If the point fits in then add it for now to this region
            region.addCoordinate(point);

            for (r in region.subRegions) {

                var fits = addPoint(point, r)

                //If the point fitted into one of the sub-regions, then remove it again from the parent region
                if (fits) region.coords.remove(point)
            }
            return true;
        } else {
            return false;
        }
    }

}