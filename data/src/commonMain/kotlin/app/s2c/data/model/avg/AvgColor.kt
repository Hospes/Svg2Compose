package app.s2c.data.model.avg

import app.s2c.data.model.compose.ComposeColor

@JvmInline
value class AvgColor(val value: String) {
    override fun toString(): String = value
}

fun AvgColor.toComposeColor(): ComposeColor = ComposeColor(value)
