import pt.isel.canvas.*
import java.awt.event.KeyEvent.*

/**
 * LEIC11N 2122
 * Grupo 3
 * Bruno Tavares - 49217
 * Rui Achada - 49221
 * Noemi Ferreira - 49226
 */

// Default values for Canvas window
const val CANVAS_WIDTH = 700
const val CANVAS_HEIGHT = 500
const val CANVAS_BACKGROUND_COLOR = BLACK
// Value used to "center" the game over text
const val TEXT_X_SPACING = 2.6
const val TEXT_SIZE = 32
const val TEXT_GAME_OVER = "GAME OVER"
const val GAME_OVER_COLOR = RED
const val GAME_OVER_SYMBOL_FILE = "skull"
// 1 second / 70 frames = 14 milliseconds per frame
const val MS_PER_FRAME = 14
// Every 35 frames, the aliens move and one is randomly chosen to shoot
// This is different from the 30 frames that were requested, as to be in line with the example program
const val ALIEN_MOVEMENT_MS = MS_PER_FRAME * 35
const val TEXT_WIN = "YOU WIN"
const val WIN_COLOR = GREEN
const val WIN_SYMBOL_FILE = "white_flag"


fun main() {
    onStart {
        val arena = Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, CANVAS_BACKGROUND_COLOR)
        // Initial state of the game; mutable point
        var gameState = Game(
            emptyList<Shot>(),
            Spaceship(
                null,
                BoundingBox(Position(SPACESHIP_INITIAL_X, SPACESHIP_Y), SPACESHIP_WIDTH, SPACESHIP_HEIGHT)
            ),
            GameState.Playing,
            emptyList<Alien>(),
            0,
            0,
            1
        ).loadAliensFromFile()

        // Mouse event for left or right click
        arena.onMouseDown {
            gameState = gameState.spaceshipShot()
        }
        // Key event for keyboard inputs
        arena.onKeyPressed {
            gameState = when(it.code) {
                // attempts to shoot the Ship shot
                VK_SPACE -> gameState.spaceshipShot()
                else -> gameState
            }
        }

        // Mouse event for mouse movement; changes Ship x position and verifies if it's within the boundaries of the canvas
        arena.onMouseMove { mouse ->
            gameState = when (mouse.x) {
                in (SPACESHIP_WIDTH / 2..(CANVAS_WIDTH - SPACESHIP_WIDTH / 2)) -> gameState.alterShip(gameState.ship.move(mouse.x))
                else -> gameState
            }
        }

        // Every 250 milliseconds...
        arena.onTimeProgress(ALIEN_MOVEMENT_MS) {
            if (gameState.state == GameState.Playing) {
                gameState = gameState.tickAnimation().moveAliens().generateAlienShot()
            }
        }

        // The game must show, approximately 70 frames per second
        arena.onTimeProgress(MS_PER_FRAME) {
            if (gameState.state == GameState.Playing) {
                gameState = gameState.tick()
                arena.drawAll(gameState)
            }

        }
    }
    onFinish {
        println("Canvas closing. Goodbye.")
    }
}

/**
 * Draws all game objects
 */
fun Canvas.drawAll(gameState: Game) {
    this.erase()

    this.drawSpaceship(gameState.ship)
    this.drawAliens(gameState)
    this.drawShots(gameState)
    this.drawPoints(gameState.points)

    when(gameState.state) {
        GameState.Lose -> this.drawGameOver()
        GameState.Win -> this.drawWin()
    }
}

/**
 * Draws Ship with a cannon
 */
fun Canvas.drawSpaceship(spaceship: Spaceship) {
    this.drawImage(
        "spaceship",
        spaceship.boundingBox.corner.x,
        spaceship.boundingBox.corner.y,
        spaceship.boundingBox.width,
        spaceship.boundingBox.height
    )
    // The code below can be used for testing and debugging. Creates a visible bounding box for the spaceship
    this.drawRect(
        spaceship.boundingBox.corner.x,
        spaceship.boundingBox.corner.y,
        spaceship.boundingBox.width,
        spaceship.boundingBox.height,
        RED,
        1
    )
}

/**
 * Draws aliens shots and ship shot, if exists
 */
fun Canvas.drawShots(gameState: Game) {
    gameState.alienShots.forEach {
        this.drawShot(it)
    }

    if (gameState.ship.shot != null)
        this.drawShot(gameState.ship.shot)
}

/**
 * Draws a shot in Canvas
 */
fun Canvas.drawShot(shot: Shot) {
    this.drawRect(
        shot.boundingBox.corner.x,
        shot.boundingBox.corner.y,
        shot.boundingBox.width,
        shot.boundingBox.height,
        shot.color
    )
}

/**
 * Draws "GAME OVER" text and 2 skulls
 */
fun Canvas.drawGameOver() {
    this.drawText(
        (CANVAS_WIDTH / TEXT_X_SPACING).toInt(),
        CANVAS_HEIGHT - TEXT_SIZE / 4,
        TEXT_GAME_OVER,
        GAME_OVER_COLOR,
        TEXT_SIZE
    )
    this.drawImage(GAME_OVER_SYMBOL_FILE, (CANVAS_WIDTH / TEXT_X_SPACING).toInt()-TEXT_SIZE,
        CANVAS_HEIGHT - TEXT_SIZE / 4 - TEXT_SIZE,
        TEXT_SIZE,
        TEXT_SIZE
    )
    this.drawImage(GAME_OVER_SYMBOL_FILE, (CANVAS_WIDTH - CANVAS_WIDTH / TEXT_X_SPACING).toInt()+TEXT_SIZE,
        CANVAS_HEIGHT - TEXT_SIZE / 4 - TEXT_SIZE,
        TEXT_SIZE,
        TEXT_SIZE
    )
}

/**
 * Draws "WIN" text and 2 Flags
 */
fun Canvas.drawWin() {
    this.drawText(
        (CANVAS_WIDTH / 2.4).toInt(),
        CANVAS_HEIGHT - TEXT_SIZE / 4,
        TEXT_WIN,
        WIN_COLOR,
        TEXT_SIZE
    )
    this.drawImage(WIN_SYMBOL_FILE, (CANVAS_WIDTH / TEXT_X_SPACING).toInt()-TEXT_SIZE,
        CANVAS_HEIGHT - TEXT_SIZE / 4 - TEXT_SIZE,
        TEXT_SIZE,
        TEXT_SIZE
    )
    this.drawImage(WIN_SYMBOL_FILE, (CANVAS_WIDTH - CANVAS_WIDTH / TEXT_X_SPACING).toInt()+TEXT_SIZE,
        CANVAS_HEIGHT - TEXT_SIZE / 4 - TEXT_SIZE,
        TEXT_SIZE,
        TEXT_SIZE
    )
}

/**
 * Draws the current game score
 */
fun Canvas.drawPoints(points: Int) {
    this.drawText(
        TEXT_SIZE / 2,
        CANVAS_HEIGHT - TEXT_SIZE / 2,
        points.toString(),
        WHITE,
        TEXT_SIZE
    )
}

/**
 * Draws an alien with a given step/animation
 */
fun Canvas.drawAlien(alien: Alien, step: Int) {
    this.drawImage("invaders|${alien.type.getSubimage(step)}",alien.boundingBox.corner.x,alien.boundingBox.corner.y,alien.boundingBox.width,alien.boundingBox.height)
// The code below can be used for testing and debugging. Creates a visible bounding box for the aliens
    this.drawRect(
        alien.boundingBox.corner.x,
        alien.boundingBox.corner.y,
        alien.boundingBox.width,
        alien.boundingBox.height,
        RED,
        1
    )
    this.drawRect(
        alien.hitBox.corner.x,
        alien.hitBox.corner.y,
        alien.hitBox.width,
        alien.hitBox.height,
        MAGENTA,
        1
    )
}

/**
 * Iterates through all aliens and draws each of them
 */
fun Canvas.drawAliens(gameState: Game) {
    gameState.aliens.forEach{
        this.drawAlien(it, gameState.animationStep)
    }
}

// The code below can be used for testing and debugging.
// Draws a line in the spaceship's Y coordinate to check alien collision with the spaceship.
fun Canvas.drawInvisibleLine() {
    this.drawRect(
        1,
        SPACESHIP_Y,
        CANVAS_WIDTH,
        20,
        RED,
        1
    )
}