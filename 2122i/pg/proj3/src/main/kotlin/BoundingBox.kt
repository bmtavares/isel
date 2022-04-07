/**
 * Class used for the limits of an object; in this case, the ship and the shot
 */
data class BoundingBox(val corner: Position, val width: Int, val height: Int)

/**
 * Extension function to detect an intersection / collision between two boundingBox; returns true if two objects collide
 */
fun BoundingBox.intersectsWith(box : BoundingBox) : Boolean {
    return (this.intersectOneSided(box) || box.intersectOneSided(this))
}

/**
 * Check if the given [BoundingBox] corners are located inside the [box] area
 */
fun BoundingBox.intersectOneSided(box : BoundingBox) : Boolean {
    if (this.corner.x in (box.corner.x..(box.corner.x+box.width)) || this.corner.x + this.width in (box.corner.x..(box.corner.x+box.width)))
        return (this.corner.y in (box.corner.y..(box.corner.y+box.height)) || this.corner.y + this.height in (box.corner.y..(box.corner.y+box.height)))
    return false
}