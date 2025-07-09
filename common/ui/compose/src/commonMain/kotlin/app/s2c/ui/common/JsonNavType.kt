package app.s2c.ui.common

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write

abstract class JsonNavType<T>(override val isNullableAllowed: Boolean = false) : NavType<T>(isNullableAllowed = isNullableAllowed) {
    abstract fun fromJsonParse(value: String): T
    abstract fun T.getJsonParse(): String

    override fun get(bundle: SavedState, key: String): T? =
        bundle.read { if (!contains(key) || isNull(key)) null else getString(key).let(::parseValue) }

    override fun parseValue(value: String): T = fromJsonParse(value)

    override fun serializeAsValue(value: T): String = value.getJsonParse()

    override fun put(bundle: SavedState, key: String, value: T) {
        bundle.write { putString(key, value.getJsonParse()) }
    }
}