enum class GameState(){
    Lose,
    Win,
    Playing
}

/**
 * Game state to be used as mutable point
 */
data class Game (val alienShots:List<Shot>, val ship:Spaceship, val state:GameState, val aliens:List<Alien>, val points: Int, val animationStep: Int, val alienDirection: Int)

/**
 * extension function used to change the list of shots; without repeating ship and over
  */
fun Game.alterShots(list : List<Shot>) = Game(list, this.ship, this.state, this.aliens, this.points, this.animationStep, this.alienDirection)

/**
 * extension function used to change the Ship;
  */
fun Game.alterShip(ship : Spaceship) = Game(this.alienShots, ship, this.state, this.aliens, this.points, this.animationStep, this.alienDirection)

/**
 * Extension function used to change the [Game.aliens]
 */
fun Game.alterAliens(list : List<Alien>) = Game(this.alienShots, this.ship, this.state, list, this.points, this.animationStep, this.alienDirection)

/**
 * Extension function used to change the [Game.points]
 */
fun Game.alterPoints(points : Int) = Game(this.alienShots, this.ship, this.state, this.aliens, this.points + points, this.animationStep, this.alienDirection)

/**
 * Extension function used to change the [Game.animationStep] to the next value
 */
fun Game.tickAnimation() = Game(this.alienShots, this.ship, this.state, this.aliens, this.points, if(this.animationStep == 1) 0 else 1, this.alienDirection)

/**
 * Extension function used to change the [Game.alienDirection] to the next value
 */
fun Game.changeDirection() = Game(this.alienShots, this.ship, this.state, this.aliens, this.points, this.animationStep, if(this.alienDirection == 1) -1 else 1)

/**
 * extension function for game over (loss);
 */
fun Game.over() = Game(this.alienShots, this.ship, GameState.Lose, this.aliens, this.points, this.animationStep, this.alienDirection)
//fun Game.over() = this.copy(state = GameState.Lose)

/**
 * extension function for game over (win);
 */
fun Game.win() = Game(this.alienShots, this.ship, GameState.Win, this.aliens, this.points, this.animationStep, this.alienDirection)

/**
 * checks if any shots collide with player; returns a boolean
 */
fun Game.checkCollisionWithPlayer() =
    alienShots.map{ it.boundingBox.intersectsWith(this.ship.boundingBox) }.filter{ it }.size > 0

/**
 * checks if any aliens collide with the line of the ship; returns a boolean
 */
fun Game.checkCollisionWithInvisibleLine() =
    aliens.map{ it.boundingBox.intersectsWith(BoundingBox(Position(1,SPACESHIP_Y),CANVAS_WIDTH,20)) }.filter{ it }.size > 0

/**
 * checks if any alien shots collide with the ship shot and returns a list of non-colliding alien shots
 */
fun Game.checkShotCollisions() =
    alienShots.filter{ !it.boundingBox.intersectsWith(this.ship.shot!!.boundingBox) }

/**
 * checks if any aliens collide with spaceship shot and returns them in a list
 */
fun Game.checkShotCollisionsWithAliens() =
    aliens.filter{ it.hitBox.intersectsWith(this.ship.shot!!.boundingBox) }

/**
 * Shoots a ship shot if there isn't any on the screen
 */
fun Game.spaceshipShot() =
    if (this.ship.shot == null)
        this.alterShip(this.ship.shoot())
    else
        this

/**
 * A tick represents a frame of our game.
 * Changes the position of the shots, verifies if it's game over (win or loss) & checks collisions
 */
fun Game.tick() : Game {
    val state = this.tickAlienShots().tickShipShot().tickShotsCollisions()

    return if (state.aliens.isEmpty())
        state.win()
    else if (state.checkCollisionWithPlayer() || state.checkCollisionWithInvisibleLine())
        state.over()
    else
        state
}

/**
 * changes position of alien shots and returns Game
 */
fun Game.tickAlienShots() = this.alterShots(this.alienShots.map {
    Shot(
        it.offset,
        it.color,
        BoundingBox(
            it.boundingBox.corner.plus(it.offset),
            it.boundingBox.width,
            it.boundingBox.height
        )
    )
})

/**
 * changes position of the Ship shot and returns Game; only if the shot exists
 */
fun Game.tickShipShot() = this.alterShip(Spaceship(this.ship.shot?.updatePosition(), this.ship.boundingBox))

/**
 * gets all shots, checks collisions between shots, updates the scoreboard and returns Game with the new shots
 */
fun Game.tickShotsCollisions() : Game {
    if (this.ship.shot != null) {
        // First, it checks if any aliens were shot
        val aliensShoot = checkShotCollisionsWithAliens()

        // if only 1 alien was shot,
        if (aliensShoot.size == 1) {
            // returns the new game state with the removed shot aliens, the removed ship shot and the added points
            return this.alterAliens(aliens - aliensShoot)
                .alterPoints(aliensShoot.fold(0){ total, alien -> total + alien.type.getPoints() })
                .alterShip(Spaceship(null, this.ship.boundingBox))
        }

        // Else, checks all non-colliding shots
        val lastingShots = this.checkShotCollisions()

        // If the number of lasting shots is lower than the total number of alien shots, it means that the ship shot collided with an alien shot
        if (lastingShots.size < this.alienShots.size) {
            // remove the collided alien shots and add the respective points
            return this.alterShots(lastingShots).alterPoints(this.alienShots.size - lastingShots.size)
        }
    }

    return this
}

/**
 * Moves the aliens and checks the collision with the arena boundaries.
 * If there is a collision, changes the aliens direction and moves one row down.
 */
fun Game.moveAliens() =
    if(this.checkAlienBoundaries())
        this.changeDirection().alterAliens(this.aliens.map{
            it.move(OffsetVector(0, VERTICAL_MOVEMENT))
        })
    else
        this.alterAliens(this.aliens.map{
            it.move(OffsetVector(HORIZONTAL_MOVEMENT*this.alienDirection, 0))
        })

/**
 * Checks if the aliens are outside the arena boundaries
 */
fun Game.checkAlienBoundaries() : Boolean =
    this.aliens.filter {
        !it.boundingBox.intersectsWith(BoundingBox(Position(ALIEN_WIDTH*(-1)*this.alienDirection,0), CANVAS_WIDTH, CANVAS_HEIGHT))
    }.size > 0

/**
 * Reads a structured txt file to load the aliens (alien type and position (x,y))
 * To access the file directly from the resources folder, we had to use the java function getResource
 */
fun Game.loadAliensFromFile() : Game {
    val fileLines = this::class.java.getResource("aliens.txt").readText().split("\r\n")

    val aliensFromFile = fileLines.map {
        it.split(',').map { cell -> cell.toInt() }
    }.map { line ->
        Alien(
            getAlienType(line[0]),
            BoundingBox(
                Position(line[1], line[2]),
                ALIEN_WIDTH,
                ALIEN_HEIGHT
            ),
            BoundingBox(
                Position(
                    line[1] + ALIEN_WIDTH / 6,
                    line[2] + ALIEN_HEIGHT / 6
                ),
                ALIEN_WIDTH - 2 * (ALIEN_WIDTH / 6),
                ALIEN_HEIGHT- 2 * (ALIEN_HEIGHT / 6)
            )
        )
    }

    return this.alterAliens(aliensFromFile)
}

/**
 * Generates an enemy shot from an alien position
 */
fun Game.generateAlienShot() : Game {
    if(aliens.size > 0){
        // Get a random alien that will shoot
        val luckyAlien = this.aliens.random()

        // Randomize a velocity for the new shot
        val velocity = (ALIEN_SHOT_VELOCITY_MIN..ALIEN_SHOT_VELOCITY_MAX).random()
        val offset = OffsetVector(0, velocity)

        // Create the shot from the alien position
        val shot = Shot(
            offset,
            ALIEN_SHOT_COLOR,
            BoundingBox(
                Position(
                    luckyAlien.boundingBox.corner.x + ALIEN_WIDTH / 2 - ALIEN_SHOT_WIDTH / 2,
                    luckyAlien.boundingBox.corner.y + ALIEN_HEIGHT
                ),
                ALIEN_SHOT_WIDTH,
                ALIEN_SHOT_HEIGHT
            )
        )

        return this.alterShots(this.alienShots + shot)
    }
    return this
}