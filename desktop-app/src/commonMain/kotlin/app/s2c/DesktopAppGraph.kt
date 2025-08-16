package app.s2c

import app.s2c.di.AppGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
interface DesktopAppGraph : AppGraph