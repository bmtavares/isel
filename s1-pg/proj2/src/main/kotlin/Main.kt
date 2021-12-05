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
const val WIDTH = 700
const val HEIGHT = 500
const val BACKGROUND_COLOR = BLACK
// Value used to "center" the game over text
const val TEXT_Y_SPACING = 2.6
const val TEXT_SIZE = 32
const val TEXT_GAME_OVER = "GAME OVER"
const val GAME_OVER_COLOR = RED
const val GAME_OVER_SYMBOL_FILE = "skull"
const val ALIEN_SHOT_GENERATION_MS = 250
const val MS_PER_FRAME = 14

fun main() {
    onStart {
        val arena = Canvas(WIDTH, HEIGHT, BACKGROUND_COLOR)
        var gameState = Game(
            emptyList<Shot>(),
            Spaceship(
                null,
                BoundingBox(Position(SPACESHIP_INITIAL_X, SPACESHIP_Y), SPACESHIP_WIDTH, SPACESHIP_HEIGHT)
            ),
            false
        )

        arena.onMouseDown {
            gameState = gameState.spaceshipShot()
        }

        arena.onKeyPressed {
            gameState = when(it.code) {
                VK_SPACE -> gameState.spaceshipShot()
                else -> gameState
            }
        }

        arena.onMouseMove { mouse ->
            gameState = when (mouse.x) {
                in (SPACESHIP_WIDTH / 2..(WIDTH - SPACESHIP_WIDTH / 2)) -> gameState.alterShip(gameState.ship.move(mouse.x))
                else -> gameState
            }
        }

        // A cada 250 milissegundos..
        arena.onTimeProgress(ALIEN_SHOT_GENERATION_MS) {
            if (!gameState.over) {
                // ..pode haver mais um disparo dos aliens com 50% de probabilidade.
                if ((0..1).random() != 0) {
                    gameState = gameState.alterShots(gameState.alienShots + generateAlienShot())
                }
            }
        }

        // O jogo deve apresentar, aproximadamente, 70 frames por segundo.
        arena.onTimeProgress(MS_PER_FRAME) {
            if (!gameState.over) {
                gameState = gameState.tick()
                arena.drawAll(gameState)
            }

        }
    }
    onFinish {
        println("Canvas closing. Goodbye.")
    }
}

fun Canvas.drawAll(gameState: Game) {
    this.erase()

    this.drawSpaceship(gameState.ship)
    this.drawShots(gameState)

    if (gameState.over) {
        this.drawGameOver()
    }
}

fun Canvas.drawSpaceship(spaceship: Spaceship) {
    this.drawRect(
        spaceship.boundingBox.corner.x,
        spaceship.boundingBox.corner.y,
        spaceship.boundingBox.width,
        spaceship.boundingBox.height,
        SPACESHIP_COLOR
    )
    this.drawRect(
        spaceship.boundingBox.corner.x + spaceship.boundingBox.width / 2 - CANNON_WIDTH / 2,
        spaceship.boundingBox.corner.y - CANNON_HEIGHT,
        CANNON_WIDTH,
        CANNON_HEIGHT,
        CANNON_COLOR
    )
}

fun Canvas.drawShots(gameState: Game) {
    gameState.alienShots.forEach {
        this.drawShot(it)
    }

    if (gameState.ship.shot != null)
        this.drawShot(gameState.ship.shot)
}

fun Canvas.drawShot(shot: Shot) {
    this.drawRect(
        shot.boundingBox.corner.x,
        shot.boundingBox.corner.y,
        shot.boundingBox.width,
        shot.boundingBox.height,
        shot.color
    )
}

fun Canvas.drawGameOver() {
    this.drawText(
        (WIDTH / TEXT_Y_SPACING).toInt(),
        HEIGHT - TEXT_SIZE / 4,
        TEXT_GAME_OVER,
        GAME_OVER_COLOR,
        TEXT_SIZE
    )
    this.drawImage(GAME_OVER_SYMBOL_FILE, (WIDTH / TEXT_Y_SPACING).toInt()-TEXT_SIZE,
        HEIGHT - TEXT_SIZE / 4 - TEXT_SIZE,
        TEXT_SIZE,
        TEXT_SIZE
    )
    this.drawImage(GAME_OVER_SYMBOL_FILE, (WIDTH - WIDTH / TEXT_Y_SPACING).toInt()+TEXT_SIZE,
        HEIGHT - TEXT_SIZE / 4 - TEXT_SIZE,
        TEXT_SIZE,
        TEXT_SIZE
    )
}
