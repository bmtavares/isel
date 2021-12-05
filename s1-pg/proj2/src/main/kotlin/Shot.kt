import pt.isel.canvas.*

const val ALIEN_SHOT_COLOR = RED
const val ALIEN_SHOT_WIDTH = 4
const val ALIEN_SHOT_HEIGHT = 9

data class Shot(val offset: OffsetVector, val color: Int, val boundingBox: BoundingBox)

fun generateAlienShot() : Shot {
    val velocity = (1..4).random()
    val positionX = (0 until WIDTH).random()

    val offset = OffsetVector(0, velocity)

    return Shot(
        offset,
        ALIEN_SHOT_COLOR,
        BoundingBox(Position(positionX - ALIEN_SHOT_WIDTH / 2, 0),
            ALIEN_SHOT_WIDTH, ALIEN_SHOT_HEIGHT)
    )
}

fun Shot.updatePosition() =
    if (this.boundingBox.corner.y in 0..HEIGHT)
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