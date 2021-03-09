# kRTree

This is a simple RTree (Region-Tree) implementation in Kotlin. This project has more academical character. The idea is to illustrate how an RTree works.

## Point

A point is just a vector of numeric (floating point) coordinates. The class does also provide methods for comparing points or for performing simple arithmetic operations. A point can have an additional id, which could reference back to another data item to which the point belongs. Here an example:

```
var point = Point(arrayOf(230f, 230f))
```

## Region

A region is described by a dimension, a minimum point (lower left corner) and a maximum point (higher right corner). In addition, a region can have sub-regions or points (coordinates) associated. Here an example:

```
var region = Region(2, Point(arrayOf(700f, 100f)), Point(arrayOf(900f, 400f)))
```

## RTree

An RTree starts with a root region and provides methods for traversing the tree of regions (sub-regions of sub-regions and so on). You can either manually add regions to the tree, or use the depth argument in the constructor to build a tree that uses grid cells as regions, whereby the tree would have the given maximum depth. Here an example:

```
var rtree = RTree(2, Point(arrayOf(0f, 0f)), Point(arrayOf(WIDTH, HEIGHT)),2)
```

## Bounding Box Query

```
var rtree = RTree(2, Point(arrayOf(0f, 0f)), Point(arrayOf(WIDTH, HEIGHT)),2)
val p1 = Point(arrayOf(750f, 250f))
val p2 = Point(arrayOf(800f, 300f))
val p3 = Point(arrayOf(200f, 900f))

rtree.addPoint(p1)
rtree.addPoint(p2)
rtree.addPoint(p3)

var box = Region(2, Point(arrayOf(700f, 100f)), Point(arrayOf(900f, 400f)))
val points = rtree.bBox(box).coords
assertEquals("[[750.0, 250.0], [800.0, 300.0]]", points.toString())
println(points.toString())
```

## Distance Query

```
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
```