data class Position(val x: Int, val y: Int)
/**
 * operator + overload: sums an offset to a Position (referred to by this keyword) and returns the resulting Position
 */
operator fun Position.plus(offset: OffsetVector) = Position(this.x + offset.dx, this.y + offset.dy)
/**
 * operator - overload
 */
operator fun Position.minus(offset: OffsetVector) = Position(this.x - offset.dx, this.y - offset.dy)