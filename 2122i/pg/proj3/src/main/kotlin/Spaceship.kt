import pt.isel.canvas.*

data class Spaceship(val shot: Shot?, val boundingBox: BoundingBox)

const val SPACESHIP_WIDTH = 50
const val SPACESHIP_HEIGHT = 30
const val SPACESHIP_Y = 450 - SPACESHIP_HEIGHT
const val SPACESHIP_INITIAL_X = CANVAS_WIDTH / 2 - SPACESHIP_WIDTH / 2

const val SHIP_SHOT_VELOCITY = -4
const val SHIP_SHOT_COLOR = WHITE
const val SHIP_SHOT_WIDTH = 4
const val SHIP_SHOT_HEIGHT = 7

/**
 * Creates a shot for the spaceship
 */
fun Spaceship.shoot() =
    Spaceship(
        Shot(
            OffsetVector(0, SHIP_SHOT_VELOCITY),
            SHIP_SHOT_COLOR,
            BoundingBox(
                Position(
                    this.boundingBox.corner.x + this.boundingBox.width / 2 - SHIP_SHOT_WIDTH / 2,
                    this.boundingBox.corner.y - SPACESHIP_HEIGHT
                ),
                SHIP_SHOT_WIDTH,
                SHIP_SHOT_HEIGHT
            )
        ),
        this.boundingBox
    )

/**
 * Changes the ship position according to a given X
 */
fun Spaceship.move(positionX: Int) = Spaceship(
    this.shot,
    BoundingBox(
        Position(
            positionX - this.boundingBox.width/2,
            this.boundingBox.corner.y
        ),
        this.boundingBox.width,
        this.boundingBox.height
    )
)