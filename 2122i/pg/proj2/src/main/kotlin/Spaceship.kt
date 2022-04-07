import pt.isel.canvas.*

data class Spaceship(val shot: Shot?, val boundingBox: BoundingBox)

const val SPACESHIP_WIDTH = 50
const val SPACESHIP_HEIGHT = 10
const val SPACESHIP_Y = 450
const val SPACESHIP_INITIAL_X = WIDTH / 2 - SPACESHIP_WIDTH / 2
const val SPACESHIP_COLOR = GREEN

const val SHIP_SHOT_VELOCITY = -4
const val SHIP_SHOT_COLOR = WHITE
const val SHIP_SHOT_WIDTH = 4
const val SHIP_SHOT_HEIGHT = 7

const val CANNON_WIDTH = 4
const val CANNON_HEIGHT = 7
const val CANNON_COLOR = YELLOW

fun Spaceship.shot() =
    Spaceship(
        Shot(
            OffsetVector(0, SHIP_SHOT_VELOCITY),
            SHIP_SHOT_COLOR,
            BoundingBox(
                Position(
                    this.boundingBox.corner.x + this.boundingBox.width / 2,
                    this.boundingBox.corner.y
                ),
                SHIP_SHOT_WIDTH,
                SHIP_SHOT_HEIGHT
            )
        ),
        this.boundingBox
    )

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