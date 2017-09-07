package com.company

import java.util.*
import kotlin.collections.ArrayList

val D1 = 1.0
val D2 = Math.sqrt(2.0)
val p = 1/1000

fun aStarSearch(start: Pair<Int, Int>, goal: Pair<Int, Int>, map: HashMap<Pair<Int, Int>, Int>): ArrayList<Pair<Int, Int>> {
    val failure = ArrayList<Pair<Int, Int>>()

    // set of node already evaluated
    val closedSet = HashSet<Pair<Int, Int>>()

    // set of currently discovered nodes
    var openSet = ArrayList<Pair<Int, Int>>()
    openSet.add(start)

    val cameFrom = HashMap<Pair<Int, Int>, Pair<Int, Int>>()

    val gScore = HashMap<Pair<Int, Int>, Double>()
    val fScore = HashMap<Pair<Int, Int>, Double>()

    for (pos: Pair<Int, Int> in map.keys) {
        if (pos.first == start.first && pos.second == start.second)
            continue

        gScore[pos] = Double.NEGATIVE_INFINITY
        fScore[pos] = Double.NEGATIVE_INFINITY
    }

    gScore[start] = 0.0
    fScore[start] = -1 * heuristicCostEstimate(start, start, goal)

    var current: Pair<Int, Int>
    var result: Pair<Pair<Int, Int>, ArrayList<Pair<Int, Int>>>
    var neighbors: ArrayList<Pair<Int, Int>>
    var tempGScore: Double

    while (!openSet.isEmpty()) {
        result = getNextNode(openSet, fScore)
        current = result.first
        openSet = result.second

        if (current.first == goal.first && current.second == goal.second) {
            return reconstructPath(cameFrom, current)
        }

        openSet = removeNode(openSet, current, fScore)
        closedSet.add(current)

        neighbors = getNeighbors(current, map)
        for (neighbor: Pair<Int, Int> in neighbors) {
            // Ignore the neighbor which is already evaluated.
            if (closedSet.contains(neighbor))
                continue

            // Discover a new node
            if (!openSet.contains(neighbor))
                openSet = insertNode(openSet, neighbor, fScore)

            // The distance from start to a neighbor
            tempGScore = gScore[current]!! + Math.sqrt(0.0 + (current.first - neighbor.first) * (current.first - neighbor.first) + (current.second - neighbor.second) * (current.second - neighbor.second))
            if (tempGScore >= gScore[neighbor]!!)
                continue

            // This path is the best until now. Record it!
            cameFrom[neighbor] = current
            gScore[neighbor] = tempGScore
            fScore[neighbor] = -1 * (gScore[neighbor]!! + heuristicCostEstimate(neighbor, start, goal))
        }
    }

    return failure
}

fun heuristicCostEstimate(pos: Pair<Int, Int>, start: Pair<Int, Int>, goal: Pair<Int, Int>, option: Int = 0): Double {
    val dx = (goal.first - pos.first).toDouble()
    val dy = Math.abs(goal.second - pos.second).toDouble()

    val result = when(option) {
        1 -> D1 * (dx + dy)
        2 -> D1 * Math.max(dx, dy) + (D2 - 1) * Math.min(dx, dy)
        3 -> D1 * Math.sqrt((dx * dx) + (dy * dy))                 // euclidean distance
        else -> D1 * (dx + dy) + (D2 - 2 * D1) * Math.min(dx, dy)  // octile distance
    }

    // Method 1 to break tie:
    return result * (1.0 + p)

    // Method 2 to break tie:
    //val cross = Math.abs(dx * (start.first - goal.first) - dy * (start.second - goal.second))
    //return result + cross * p
}

fun findNextNode(openSet: HashSet<Pair<Int, Int>>, fScore: HashMap<Pair<Int, Int>, Double>): Pair<Int, Int> {
    if (openSet.isEmpty()) {
        return Pair(0, 0)
    }

    if (openSet.size == 1) {
        return openSet.elementAt(0)
    }

    var lowScore = Double.MAX_VALUE
    var nextNode = openSet.elementAt(0)

    for (node: Pair<Int, Int> in openSet) {
        if (fScore[node]!! < lowScore) {
            lowScore = fScore[node]!!
            nextNode = node
        }
    }

    return nextNode
}

fun insertNode(openSet: ArrayList<Pair<Int, Int>>, node: Pair<Int, Int>, fScore: HashMap<Pair<Int, Int>, Double>): ArrayList<Pair<Int, Int>> {
    openSet.add(node)

    var child = openSet.size - 1
    var parent = (child - 1) / 2
    var temp: Pair<Int, Int>

    while (child != 0 && fScore[openSet[child]]!! > fScore[openSet[parent]]!!) {
        temp = openSet[child]
        openSet[child] = openSet[parent]
        openSet[parent] = temp

        child = parent
        parent = (parent - 1) / 2
    }

    return openSet
}

fun removeNode(openSet: ArrayList<Pair<Int, Int>>, node: Pair<Int, Int>, fScore: HashMap<Pair<Int, Int>, Double>): ArrayList<Pair<Int, Int>> {
    openSet.remove(node)
    return buildMaxHeap(openSet, fScore)
}

fun getNextNode(openSet: ArrayList<Pair<Int, Int>>, fScore: HashMap<Pair<Int, Int>, Double>): Pair<Pair<Int, Int>, ArrayList<Pair<Int, Int>>> {
    val nextNode = Pair(openSet[0].first, openSet[0].second)

    val newSet: ArrayList<Pair<Int, Int>>
    if (openSet.size == 1) {
        newSet = openSet
    } else {
        // replacing the first node with the last node.
        openSet[0] = openSet.removeAt(openSet.size-1)

        // heapify the remaining array-list
        newSet = buildMaxHeap(openSet, fScore)
    }

    return Pair(nextNode, newSet)
}

fun buildMaxHeap(openSet: ArrayList<Pair<Int, Int>>, fScore: HashMap<Pair<Int, Int>, Double>): ArrayList<Pair<Int, Int>> {
    var middle = openSet.size / 2 - 1
    var heapSet = openSet

    while (middle >= 0) {
        heapSet = heapifyArray(heapSet, fScore, middle, openSet.size - 1)
        middle -= 1
    }

    return heapSet
}

fun heapifyArray(openSet: ArrayList<Pair<Int, Int>>, fScore: HashMap<Pair<Int, Int>, Double>, root: Int, last: Int): ArrayList<Pair<Int, Int>> {
    val left = 2 * root + 1
    val right = left + 1
    var largest = root
    val temp: Pair<Int, Int>

    if (left <= last && fScore[openSet[left]]!! > fScore[openSet[largest]]!!)
        largest = left

    if (right <= last && fScore[openSet[right]]!! > fScore[openSet[largest]]!!)
        largest = right

    return when (largest != root) {
        true -> {
            temp = openSet[largest]
            openSet[largest] = openSet[root]
            openSet[root] = temp
            heapifyArray(openSet, fScore, largest, last)  //return value
        }
        else -> {
            openSet        //return value
        }
    }
}

fun reconstructPath(cameFrom: HashMap<Pair<Int, Int>, Pair<Int, Int>>, current: Pair<Int, Int>): ArrayList<Pair<Int, Int>> {
    val path = ArrayList<Pair<Int, Int>>()
    path.add(current)

    var pos = current
    while (cameFrom.containsKey(pos)) {
        pos = cameFrom[pos]!!
        path.add(pos)
    }

    return path
}

fun getNeighbors(current: Pair<Int, Int>, map: HashMap<Pair<Int, Int>, Int>): ArrayList<Pair<Int, Int>> {
    var neighbor: Pair<Int, Int>
    val neighbors: ArrayList<Pair<Int, Int>> = ArrayList()

    for (i in -1..1) {
        for (j in -1..1) {
            if (i == 0 && j == 0)
                continue

            neighbor = Pair(current.first + i, current.second + j)
            if (map.containsKey(neighbor) && map[neighbor] == 1) {
                neighbors.add(neighbor)
            }
        }
    }

    return neighbors
}