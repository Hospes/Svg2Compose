package app.s2c.data.builder

import app.s2c.data.extensions.camelCase
import app.s2c.data.extensions.pascalCase
import app.s2c.data.model.IconFileContents

class MaterialIconSourceBuilder : IconSourceBuilder() {

    override val imports: Set<String> = setOf(
        "androidx.compose.material.Icon",
        "androidx.compose.material.icons.materialIcon",
        "androidx.compose.material.icons.materialPath",
    )

    override fun IconSourceBuilderScope.stringify(icon: IconFileContents): String = with(icon) {
        """
            |${pkg?.let { "package $it" } ?: ""}
            |
            |${this@stringify.imports.sorted().joinToString("\n") { "import $it" }}
            |
            |${visibilityModifier}val $iconPropertyName: ImageVector
            |    get() {
            |        if (_${iconName.camelCase()} != null) return _${iconName.camelCase()}!!
            |
            |        _${iconName.camelCase()} = materialIcon(name = "$receiverType.${iconName.pascalCase()}") {
            |            $pathNodes
            |        }
            |        return _${iconName.camelCase()}!!
            |    }
            |@Suppress("ObjectPropertyName")
            |private var _${iconName.camelCase()}: ImageVector? = null
            |$EXTRA_CONTENT_PLACEHOLDER
            |
        """.replace(EXTRA_CONTENT_PLACEHOLDER, extraContent).trimMargin()
    }
}