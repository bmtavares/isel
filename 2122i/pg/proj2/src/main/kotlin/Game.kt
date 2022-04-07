data class Game (val alienShots:List<Shot>, val ship:Spaceship, val over:Boolean)

fun Game.alterShots(list : List<Shot>) = Game(list, this.ship, this.over)

fun Game.alterShip(ship : Spaceship) = Game(this.alienShots, ship, this.over)

fun Game.over() = Game(this.alienShots, this.ship, true)

fun Game.checkCollisionWithPlayer() =
    alienShots.map{ it.boundingBox.intersectsWith(this.ship.boundingBox) }.filter{ it }.size > 0

fun Game.checkShotCollisions() =
    alienShots.filter{ !it.boundingBox.intersectsWith(this.ship.shot!!.boundingBox) }

fun Game.spaceshipShot() =
    if (this.ship.shot == null)
        this.alterShip(this.ship.shot())
    else
        this

fun Game.tick() : Game {
    val state = this.tickAlienShots().tickShipShot().tickShotsCollisions()

    return if (state.checkCollisionWithPlayer())
        state.over()
    else
        state
}

fun Game.tickAlienShots() = this.alterShots(this.alienShots.mapNotNull {
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

fun Game.tickShipShot() = this.alterShip(Spaceship(this.ship.shot?.updatePosition(), this.ship.boundingBox))

fun Game.tickShotsCollisions() : Game {
    if (this.ship.shot != null) {
        // Keep all non-colliding shots
        val lastingShots = this.checkShotCollisions()

        // If <, means that the ship shot collided with an alien shot
        if (lastingShots.size < this.alienShots.size) {
            return this.alterShip(Spaceship(null, this.ship.boundingBox)).alterShots(lastingShots)
        }
    }

    return this
}