package app.s2c.data.builder

import app.s2c.data.extensions.camelCase
import app.s2c.data.extensions.pascalCase
import app.s2c.data.model.IconFileContents

class MaterialIconSourceBuilder : IconSourceBuilder() {

    override fun IconSourceBuilderScope.stringify(icon: IconFileContents): String = with(icon) {
        """
            |package $pkg
            |
            |${imports.sorted().joinToString("\n") { "import $it" }}
            |
            |${visibilityModifier}val $iconPropertyName: ImageVector
            |    get() {
            |        if (_${iconName.camelCase()} != null) return _${iconName.camelCase()}!!
            |
            |        _${iconName.camelCase()} = materialIcon(name = "$theme.${iconName.pascalCase()}") {
            |            $pathNodes
            |        }
            |        return _${iconName.camelCase()}!!
            |    }
            |$EXTRA_CONTENT_PLACEHOLDER
            |@Suppress("ObjectPropertyName")
            |private var _${iconName.camelCase()}: ImageVector? = null
            |
        """.replace(EXTRA_CONTENT_PLACEHOLDER, extraContent).trimMargin()
    }
}