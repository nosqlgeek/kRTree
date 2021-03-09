package org.nosqlgeek.krtree

import kotlin.test.assertEquals
import kotlin.test.assertTrue

//Test space constants
val WIDTH = 1000f
val HEIGHT = 1000f

/**
 * Test suite
 */
fun main(args: Array<String>) {
    simpleGridTest()
    addPointTest()
    bBoxQueryTest()
    distQueryTest()
}

/**
 * Test if the grid building works as expected
 */
fun simpleGridTest() {
    println("-- simpleGridTest")
    var rtree = RTree(2, Point(arrayOf(0f, 0f)), Point(arrayOf(WIDTH, HEIGHT)),2)

    val root = rtree.root
    println("root: $root")
    assertEquals("[[0.0, 0.0], [1000.0, 1000.0]]", root.toString())
    val l = root.subRegions.elementAt(0)
    assertEquals("[[0.0, 0.0], [500.0, 1000.0]]", l.toString())
    println("l: $l")
    val ll = l.subRegions.elementAt(0)
    assertEquals("[[0.0, 0.0], [500.0, 500.0]]", ll.toString())
    println("ll: $ll")
    val lr = l.subRegions.elementAt(1)
    assertEquals("[[0.0, 500.0], [500.0, 1000.0]]", lr.toString())
    println("lr: $lr")
    val r = root.subRegions.elementAt(1)
    assertEquals("[[500.0, 0.0], [1000.0, 1000.0]]", r.toString())
    println("r: $r")
    val rl = r.subRegions.elementAt(0)
    assertEquals("[[500.0, 0.0], [1000.0, 500.0]]", rl.toString())
    println("rl: $rl")
    val rr = r.subRegions.elementAt(1)
    assertEquals("[[500.0, 500.0], [1000.0, 1000.0]]", rr.toString())
    println("rr: $rr")
}

/**
 * Test if adding a point returns the right result
 */
fun addPointTest() {

    println("-- addPointTest")
    var rtree = RTree(2, Point(arrayOf(0f, 0f)), Point(arrayOf(WIDTH, HEIGHT)),2)
    val point = Point(arrayOf(750f, 250f))
    rtree.addPoint(point)

    //We expect to find the point in region rl
    var root = rtree.root
    val r = root.subRegions.elementAt(1)
    val rl = r.subRegions.elementAt(0)
    val rr = r.subRegions.elementAt(1)

    assertTrue {root.coords.isEmpty()}
    println("Points in root: ${root.coords.toString()}")

    assertTrue {r.coords.isEmpty()}
    println("Points in r: ${r.coords.toString()}")

    assertEquals("[[750.0, 250.0]]", rl.coords.toString())
    println("Points in rl: ${rl.coords.toString()}")

    assertTrue {rr.coords.isEmpty()}
    println("Points in rr: ${rr.coords.toString()}")
}

/**
 * Test if a simple bounding box query works
 */
fun bBoxQueryTest() {

    println("-- bBoxQueryTest")
    var rtree = RTree(2, Point(arrayOf(0f, 0f)), Point(arrayOf(WIDTH, HEIGHT)),2)
    val p1 = Point(arrayOf(750f, 250f))
    val p2 = Point(arrayOf(800f, 300f))
    val p3 = Point(arrayOf(200f, 900f))

    rtree.addPoint(p1)
    rtree.addPoint(p2)
    rtree.addPoint(p3)

    var box = Region(2, Point(arrayOf(700f, 100f)), Point(arrayOf(900f, 400f)))
    var points = rtree.bBox(box).coords
    assertEquals("[[750.0, 250.0], [800.0, 300.0]]", points.toString())
    println(points.toString())
}

/**
* Test if a simple distance query works
*/
fun distQueryTest() {
    println("-- distQueryTest")
    var rtree = RTree(2, Point(arrayOf(0f, 0f)), Point(arrayOf(WIDTH, HEIGHT)),2)
    val p1 = Point(arrayOf(230f, 230f))
    val p2 = Point(arrayOf(240f, 240f))
    val p3 = Point(arrayOf(900f, 900f))

    rtree.addPoint(p1)
    rtree.addPoint(p2)
    rtree.addPoint(p3)

    var coords = rtree.dist(Point(arrayOf(200f,200f)), 50f).coords
    assertEquals("[[230.0, 230.0], [240.0, 240.0]]", coords.toString())
    println(coords)
}

