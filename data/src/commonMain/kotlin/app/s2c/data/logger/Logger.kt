package app.s2c.data.logger

import app.s2c.data.AppConfig

fun debug(message: Any) {
    if (!AppConfig.silent && AppConfig.debug) {
        println("D: $message")
    }
}

fun <T> debugSection(title: String, block: () -> T): T =
    if (!AppConfig.silent && AppConfig.debug) {
        startSection(title)
        block().also { endSection() }
    } else {
        block()
    }

fun <T> verboseSection(title: String, block: () -> T) =
    if (!AppConfig.silent && AppConfig.verbose) {
        startSection(title)
        block().also { endSection() }
    } else {
        block()
    }

private fun startSection(message: String) {
    println()
    val section = "=".repeat(25 - (message.length / 2))
    println("$section $message $section")
    println()
}

private fun endSection() {
    println()
    println("=".repeat(n = 50))
    println()
}

fun verbose(message: String) {
    if (!AppConfig.silent && AppConfig.verbose) {
        println("V: $message")
    }
}

@Suppress("ForbiddenComment")
fun warn(message: String) {
    if (!AppConfig.silent) {
        println("WARNING ⚠️: $message") // TODO: add color to output.
    }
}

fun output(message: String) {
    if (!AppConfig.silent) {
        println(message)
    }
}

fun printEmpty() {
    if (!AppConfig.silent) {
        println()
    }
}
