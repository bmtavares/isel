data class BoundingBox(val corner: Position, val width: Int, val height: Int)

fun BoundingBox.intersectsWith(box : BoundingBox) : Boolean {
    if (this.corner.x in (box.corner.x..(box.corner.x+box.width)) || this.corner.x + this.width in (box.corner.x..(box.corner.x+box.width)))
        return (this.corner.y in (box.corner.y..(box.corner.y+box.height)) || this.corner.y + this.height in (box.corner.y..(box.corner.y+box.height)))
    return false
}