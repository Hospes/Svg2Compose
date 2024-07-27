package app.s2c.data.parser

import app.s2c.data.model.SvgDto

interface IconSourceParser {
    fun parse(source: String): SvgDto
}