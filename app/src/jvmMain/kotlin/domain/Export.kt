package domain

import model.Svg
import java.io.File
import java.io.FileWriter
import java.io.IOException

object Export {

    fun exportFile(filePath: String, svg: Svg): Boolean {
        try {
            with(
                File(filePath).apply {
                    if (!parentFile.canWrite() && !parentFile.setWritable(true)) return false
                    createNewFile()
                }
            ) {
                FileWriter(this).apply {
                    write(
                        svg.`package` +
                                svg.imports +
                                svg.imageVectorCode.replace(
                                    "[IconName]",
                                    "${this@with.nameWithoutExtension.firstOrNull()?.uppercase()}" +
                                            "${this@with.nameWithoutExtension.substring(1)}",
                                ).replace(
                                    "[iconName]",
                                    "${this@with.nameWithoutExtension.firstOrNull()?.lowercase()}" +
                                            "${this@with.nameWithoutExtension.substring(1)}",
                                )
                    )
                    close()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun getCodeToCopy(iconParent: String, iconGroup: String, iconName: String, svg: Svg): String = svg.imports + svg.imageVectorCode
        .replace("[IconParent]", "${iconParent.firstOrNull()?.uppercase()}${iconParent.substring(1)}")
        .replace("[iconParent]", "${iconParent.firstOrNull()?.lowercase()}${iconParent.substring(1)}")
        .replace("[IconGroup]", "${iconGroup.firstOrNull()?.uppercase()}${iconGroup.substring(1)}")
        .replace("[iconGroup]", "${iconGroup.firstOrNull()?.lowercase()}${iconGroup.substring(1)}")
        .replace("[IconName]", "${iconName.firstOrNull()?.uppercase()}${iconName.substring(1)}")
        .replace("[iconName]", "${iconName.firstOrNull()?.lowercase()}${iconName.substring(1)}")
}
