fun main() {
	println("- Intersect -")
	
	val rangeAX : Int = readInt("Please enter the left side of the A [_,]: ")
	val rangeAY : Int = readInt("Please enter the right side of the A [,_]: ")
	val rangeBX : Int = readInt("Please enter the left side of the B [_,]: ")
	val rangeBY : Int = readInt("Please enter the right side of the B [,_]: ")

	print("AnB = ")
	if (rangeAY in rangeBX..rangeBY || rangeBY in rangeAX..rangeAY) {
		var rangeLeft : Int = greaterThan(rangeAX, rangeBX)
		var rangeRight : Int = lowestThan(rangeAY, rangeBY)
		print("[${rangeLeft},${rangeRight}]")
	}
	else {
		print("impossible")
	}
}