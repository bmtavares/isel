import pt.isel.canvas.*

const val WIDTH = 500
const val HEIGHT = 200
const val BACKGROUND_COLOR = CYAN

fun main() {
    print("Begin ")
    onStart {
        val arena = Canvas(WIDTH, HEIGHT, BACKGROUND_COLOR)
        print("Start ")

        arena.drawCircle(WIDTH / 2, HEIGHT / 2, 25, YELLOW, 5)

        var posX = WIDTH / 2
        var posY = HEIGHT / 2

        arena.onKeyPressed { keyEvent: KeyEvent ->
            when (keyEvent.code) {
                LEFT_CODE -> {
                    arena.erase()
                    posX -= 10
                    arena.drawCircle(posX, posY, 25, YELLOW, 5)
                }
                RIGHT_CODE -> {
                    arena.erase()
                    posX += 10
                    arena.drawCircle(posX, posY, 25, YELLOW, 5)
                }
                UP_CODE -> {
                    arena.erase()
                    posY -= 10
                    arena.drawCircle(posX, posY, 25, YELLOW, 5)
                }
                DOWN_CODE -> {
                    arena.erase()
                    posY += 10
                    arena.drawCircle(posX, posY, 25, YELLOW, 5)
                }
            }
        }
    }
    onFinish {
        print("Finish ")
    }
    print("End ")
}