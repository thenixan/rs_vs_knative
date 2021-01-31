import kotlinx.cinterop.*
import platform.posix.FILE
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen

fun main() {
    val file = fopen("../input", "r")
        ?: throw IllegalArgumentException("Cannot open input file")
    val result = file.useLines { lines ->
        lines.map(::mapInputToPassword)
            .filter { (rule, s) -> rule.isValid(s) }
            .count()
    }
    println("Result: $result")
}

fun mapInputToPassword(s: String): Pair<PasswordRule, String> = s
    .split(':')
    .let { PasswordRule(it[0]) to it[1].trim() }

class PasswordRule(s: String) {
    private val from: Int
    private val to: Int
    private val letter: Char

    init {
        val a = s.split('-', ' ')
        from = a[0].toInt()
        to = a[1].toInt()
        letter = a[2].toCharArray()[0]
    }

    fun isValid(password: String): Boolean =
        password.toCharArray()
            .filter { c -> c == letter }
            .size
            .let { s -> s in from..to }
}
















fun <T> CPointer<FILE>.useLines(f: (Sequence<String>) -> T): T {
    val file = this
    val s = sequence {
        memScoped {
            val readBufferLength = 64 * 1024
            val buffer = allocArray<ByteVar>(readBufferLength)
            var line = fgets(buffer, readBufferLength, file)
                ?.toKString()
            while (line != null) {
                yield(line.trim())
                line = fgets(buffer, readBufferLength, file)
                    ?.toKString()
            }
        }
    }
    val result = f(s)
    fclose(file)
    return result
}















