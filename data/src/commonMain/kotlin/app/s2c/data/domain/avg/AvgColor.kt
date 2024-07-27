package app.s2c.data.domain.avg

import app.s2c.data.domain.compose.ComposeColor
import kotlin.jvm.JvmInline

@JvmInline
value class AvgColor(val value: String) {
    override fun toString(): String = value
}

fun AvgColor.toComposeColor(): ComposeColor = ComposeColor(value)
