import pt.isel.canvas.*

const val SQUID_POINTS = 30
const val CRAB_POINTS = 20
const val OCTOPUS_POINTS = 10
const val ALIEN_WIDTH = 56
const val ALIEN_HEIGHT = 40
const val HORIZONTAL_MOVEMENT = 4
const val VERTICAL_MOVEMENT = 20
const val ALIEN_SPRITE_WIDTH = 112
const val ALIEN_SPRITE_HEIGHT = 78
const val SQUID_ROW = ALIEN_SPRITE_HEIGHT * 0
const val CRAB_ROW = ALIEN_SPRITE_HEIGHT * 1
const val OCTOPUS_ROW = ALIEN_SPRITE_HEIGHT * 2
const val ALIEN_SHOT_VELOCITY_MIN = 1
const val ALIEN_SHOT_VELOCITY_MAX = 4

/**
 * Lists all the possible alien types in a specific order
 */
enum class AlienType(val value: Int){
    Squid(0),
    Crab(1),
    Octopus(2)
}

data class Alien(val type: AlienType, val boundingBox: BoundingBox, val hitBox: BoundingBox)

/**
 * Returns the [AlienType] color that refers to this [value]
 */
fun getAlienType(value: Int) = when (value) {
    AlienType.Squid.value -> AlienType.Squid
    AlienType.Crab.value -> AlienType.Crab
    AlienType.Octopus.value -> AlienType.Octopus
    else -> AlienType.Squid
}

/**
 * Returns the [Canvas] subimage coordinates that refers to this [AlienType]
 */
fun AlienType.getSubimage(step: Int) = when (this) {
    AlienType.Squid -> "${ALIEN_SPRITE_WIDTH*step},$SQUID_ROW,$ALIEN_SPRITE_WIDTH,$ALIEN_SPRITE_HEIGHT"
    AlienType.Crab -> "${ALIEN_SPRITE_WIDTH*step},$CRAB_ROW,$ALIEN_SPRITE_WIDTH,$ALIEN_SPRITE_HEIGHT"
    AlienType.Octopus -> "${ALIEN_SPRITE_WIDTH*step},$OCTOPUS_ROW,$ALIEN_SPRITE_WIDTH,$ALIEN_SPRITE_HEIGHT"
}

/**
 * Returns the value of points awarded by eliminating this [AlienType]
 */
fun AlienType.getPoints() = when (this) {
    AlienType.Squid -> SQUID_POINTS
    AlienType.Crab -> CRAB_POINTS
    AlienType.Octopus -> OCTOPUS_POINTS
}

/**
 * Used to move an alien with a given offset
 */
fun Alien.move(offset : OffsetVector) =
    Alien(
        this.type,
        BoundingBox(
            this.boundingBox.corner + offset,
            this.boundingBox.width,
            this.boundingBox.height
        ),
        BoundingBox(
            this.hitBox.corner + offset,
            this.hitBox.width,
            this.hitBox.height
        ),
    )