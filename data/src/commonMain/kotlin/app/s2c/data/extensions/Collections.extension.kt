package app.s2c.data.extensions

inline fun <reified T> Iterable<*>.firstInstanceOfOrNull(): T? =
    firstOrNull { it is T } as? T
