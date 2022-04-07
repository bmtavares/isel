import pt.isel.canvas.*
import kotlin.math.cos
import kotlin.math.sin
// Used for keyEvent codes not included in Canvas
import java.awt.event.KeyEvent.VK_PLUS
import java.awt.event.KeyEvent.VK_MINUS

/**
 * LEIC11N 2122
 * Grupo 3
 * Bruno Tavares - 49217
 * Rui Achada - 49221
 */

// Default values for Canvas window
const val WIDTH = 500
const val HEIGHT = 500
const val BACKGROUND_COLOR = WHITE

// Used to move & zoom object
const val DELTA_MOVE = 4
const val DELTA_SCALE = 1

const val INITIAL_CENTER_X = WIDTH / 2
const val INITIAL_CENTER_Y = HEIGHT / 2
const val INITIAL_RADIUS = 100

const val ANTENNA_ANGLE = 30
// Modifiers are to be multiplied by the supplied R
const val ANTENNA_HEIGHT_MODIFIER = 0.3
const val ANTENNA_WIDTH_MODIFIER = 0.08
const val EYE_POSITION_X_MODIFIER = 0.44
const val EYE_POSITION_Y_MODIFIER = 0.4
const val EYE_SIZE_MODIFIER = 0.1

// Used for specifying whether a move/zoom is on the negative or positive direction
const val NEGATIVE_DIRECTION = -1
const val POSITIVE_DIRECTION = 1

data class Head(val x :Int, val y :Int, val radius :Int)

fun main() {
    onStart {
        var bugdroid = Head(INITIAL_CENTER_X, INITIAL_CENTER_Y, INITIAL_RADIUS)
        val arena = Canvas(WIDTH, HEIGHT, BACKGROUND_COLOR)

        arena.drawBugdroid(bugdroid)

        arena.onKeyPressed { keyEvent ->
            bugdroid = when (keyEvent.code) {
                LEFT_CODE -> bugdroid.moveOnX(NEGATIVE_DIRECTION)
                RIGHT_CODE -> bugdroid.moveOnX(POSITIVE_DIRECTION)
                UP_CODE -> bugdroid.moveOnY(NEGATIVE_DIRECTION)
                DOWN_CODE -> bugdroid.moveOnY(POSITIVE_DIRECTION)
                // KeyEvent.code for '+' differs from it's character code,
                // so constants from java.awt.event are used
                VK_PLUS -> bugdroid.zoom(POSITIVE_DIRECTION)
                // '-'.code worked, but constant used for consistency
                VK_MINUS -> bugdroid.zoom(NEGATIVE_DIRECTION)
                else -> bugdroid
            }
            arena.drawBugdroid(bugdroid)
        }

        arena.onMouseDown {
            bugdroid = Head(it.x, it.y, bugdroid.radius)
            arena.drawBugdroid(bugdroid)
        }
    }
    onFinish {
        println("Canvas closing. Goodbye.")
    }
}

/**
 * Clears the [Canvas] and then draws all the parts of the [head].
 */
fun Canvas.drawBugdroid(head: Head) {
    erase()
    drawHead(head)
    drawEye(head, NEGATIVE_DIRECTION)
    drawEye(head, POSITIVE_DIRECTION)
    drawAntenna(head, NEGATIVE_DIRECTION)
    drawAntenna(head, POSITIVE_DIRECTION)
}

/**
 * Draws the main part of a [head] to the called [Canvas].
 */
fun Canvas.drawHead(head: Head) = this.drawArc(head.x,head.y,head.radius, 0, 180, GREEN)

/**
 * Draws an eye of a [head] for a specified [direction] to the called [Canvas].
 */
fun Canvas.drawEye(head: Head, direction: Int) {
    val eyeX = head.radius.scale(EYE_POSITION_X_MODIFIER)
    val eyeY = head.radius.scale(EYE_POSITION_Y_MODIFIER)
    val radius = head.radius.scale(EYE_SIZE_MODIFIER)
    this.drawCircle(head.x + direction * eyeX, head.y - eyeY, radius, WHITE)
}

/**
 * Draws an antenna of a [head] for a specified [direction] to the called [Canvas].
 */
fun Canvas.drawAntenna(head: Head, direction: Int) {
    // Calculate the angles starting from 90º and convert them to radians, for use with cos() & sin()
    val angleRadians = (direction * ANTENNA_ANGLE - 90).toRadians()
    // Used as radius
    val antennaHalfWidth = head.radius.scale(ANTENNA_WIDTH_MODIFIER)/2

    // Starting point from edge of the head
    val antennaX1 = head.x + head.radius * cos(angleRadians)
    val antennaY1 = head.y + head.radius * sin(angleRadians)

    // End point of the antenna
    val antennaX2 = antennaX1 + head.radius.scale(ANTENNA_HEIGHT_MODIFIER) * cos(angleRadians)
    val antennaY2 = antennaY1 + head.radius.scale(ANTENNA_HEIGHT_MODIFIER) * sin(angleRadians)

    // (x,y) pair for a circle to round the antenna
    val antennaEndX = antennaX2 + antennaHalfWidth * cos(angleRadians)
    val antennaEndy = antennaY2 + antennaHalfWidth * sin(angleRadians)

    this.drawLine(antennaX1.toInt(), antennaY1.toInt(), antennaX2.toInt(), antennaY2.toInt(), GREEN, head.radius.scale(ANTENNA_WIDTH_MODIFIER))
    this.drawCircle(antennaEndX.toInt(), antennaEndy.toInt(), antennaHalfWidth, GREEN)
}

/**
 * Returns a new [Head] that moved [direction]*[DELTA_MOVE] in the X axis.
 */
fun Head.moveOnX(direction: Int) = Head(x + direction * DELTA_MOVE, y, radius)

/**
 * Returns a new [Head] that moved [direction]*[DELTA_MOVE] in the Y axis.
 */
fun Head.moveOnY(direction: Int) = Head(x, y + direction * DELTA_MOVE, radius)

/**
 * Returns a new [Head] that was zoomed in/out according to [direction].
 */
fun Head.zoom(direction: Int) = Head(x, y, radius + direction * DELTA_SCALE)

/**
 * Return values scaled using a [factor] in [Int].
 */
fun Int.scale(factor: Double) = (this * factor).toInt()

/**
 * Transforms a given value in Degrees to Radians
 */
fun Int.toRadians() = this * (Math.PI / 180)