package app.s2c.data.parser

import app.s2c.data.model.SvgDto

class SvgIconSourceParser : IconSourceParser {
    //private val regex = "<path\\s+d=\"(.*?)\"".toRegex()
    private val regex = "<path\\s+(fill=\"(.*?)\")?(?:\\s+d=\"(.*?)\")".toRegex()

    override fun parse(source: String): SvgDto {
        TODO("Not yet implemented")
    }
}