package dev.grabmann.adventofcode.challange5

import java.util.*

class Storage(storageSize: Int) {
    private val rows: List<Stack<Item>>

    val rowCount: Int
        get() = rows.size

    init {
        rows = buildList {
            for (i in 0 until storageSize) {
                add(Stack())
            }
        }
    }

    fun store(item: Item, index: Int) = rows[index].push(item)

    fun top(index: Int): Item = rows[index].peek()

    fun remove(index: Int): Item = rows[index].pop()
}
