package dev.grabmann.adventofcode.challange5

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun main() {
    val file = Path("input.txt")
    val lines = file.readLines()
    val storage = parseStorage(lines)
    val moves = parseMoves(lines)
    moves.forEach { storage.applyMove(it) }
    val output = storage.allTopItems.joinToString(separator = "") { it.id }
    println(output)
}

fun parseMoves(lines: List<String>): List<Move> {
    val splitIndex = lines.indexOfFirst { it.isEmpty() }
    val moveLines = lines.drop(splitIndex + 1)
    val moveRegex = """move (?<count>\d+) from (?<from>\d+) to (?<to>\d+)""".toRegex()
    return moveLines.map {
        val match = moveRegex.matchEntire(it)!!
        val count = match.groups["count"]!!
        val from = match.groups["from"]!!
        val to = match.groups["to"]!!
        Move(
            from = from.value.toInt() - 1,
            to = to.value.toInt() - 1,
            count = count.value.toInt()
        )
    }
}

fun parseStorage(lines: List<String>): Storage {
    val splitIndex = lines.indexOfFirst { it.isEmpty() }
    val tempLines = lines.slice(0 until splitIndex).reversed()
    val storageSize = tempLines.first().chunked(4) {
        it.trim().toString().toInt()
    }.last()
    val storage = Storage(storageSize)
    val storageLines = tempLines.drop(1)
    for (line in storageLines) {
        line.chunked(4) {
            it.trim().replace("""[\[\]]""".toRegex(), "")
        }.forEachIndexed { index, item ->
            if (item.isNotEmpty()) {
                storage.store(Item(item), index)
            }
        }
    }
    return storage
}

fun Storage.applyMove(move: Move) {
    (0 until move.count).map { this.remove(move.from) }.reversed().forEach { this.store(it, move.to) }
}

val Storage.allTopItems get() = (0 until rowCount).map { top(it) }
