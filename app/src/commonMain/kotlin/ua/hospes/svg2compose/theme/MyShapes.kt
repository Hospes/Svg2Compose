package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.unit.dp

object MyShapes {
    /**
     * Shape used by small components like [Button] or [Snackbar]. Components like
     * [FloatingActionButton], [ExtendedFloatingActionButton] use this shape, but override
     * the corner size to be 50%. [TextField] uses this shape with overriding the bottom corners
     * to zero.
     */
    val small: RoundedCornerShape = RoundedCornerShape(4.dp)

    /**
     * Shape used by medium components like [Card] or [AlertDialog].
     */
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp)

    /**
     * Shape used by large components like [ModalDrawer] or [ModalBottomSheetLayout].
     */
    val large: RoundedCornerShape = RoundedCornerShape(12.dp)

    fun asMaterialShapes(): Shapes = Shapes(
        small = small,
        medium = medium,
        large = large,
    )
}