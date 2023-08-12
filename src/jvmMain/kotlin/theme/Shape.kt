package theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.unit.dp

val MyShapes = Shapes(
    /**
     * Shape used by small components like [Button] or [Snackbar]. Components like
     * [FloatingActionButton], [ExtendedFloatingActionButton] use this shape, but override
     * the corner size to be 50%. [TextField] uses this shape with overriding the bottom corners
     * to zero.
     */
    /**
     * Shape used by small components like [Button] or [Snackbar]. Components like
     * [FloatingActionButton], [ExtendedFloatingActionButton] use this shape, but override
     * the corner size to be 50%. [TextField] uses this shape with overriding the bottom corners
     * to zero.
     */
    small = RoundedCornerShape(4.dp),
    /**
     * Shape used by medium components like [Card] or [AlertDialog].
     */
    /**
     * Shape used by medium components like [Card] or [AlertDialog].
     */
    medium = RoundedCornerShape(6.dp),
    /**
     * Shape used by large components like [ModalDrawer] or [ModalBottomSheetLayout].
     */
    /**
     * Shape used by large components like [ModalDrawer] or [ModalBottomSheetLayout].
     */
    large = RoundedCornerShape(8.dp)
)