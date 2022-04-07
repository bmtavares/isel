import pt.isel.canvas.*

const val ALIEN_SHOT_COLOR = RED
const val ALIEN_SHOT_WIDTH = 4
const val ALIEN_SHOT_HEIGHT = 9

data class Shot(val offset: OffsetVector, val color: Int, val boundingBox: BoundingBox)

/**
 * Changes the position of a Shot and checks if it's still inside the Canvas
 */
fun Shot.updatePosition() =
    if (this.boundingBox.corner.y in 0..CANVAS_HEIGHT)
        Shot(
            this.offset,
            this.color,
            BoundingBox(
                this.boundingBox.corner.plus(this.offset),
                this.boundingBox.width,
                this.boundingBox.height
            )
        )
    else
        null