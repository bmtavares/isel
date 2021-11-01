fun main() {
	println("- Union -")

	val rangeAX : Int = readInt("Please enter the left side of the A [_,]: ")
	val rangeAY : Int = readInt("Please enter the right side of the A [,_]: ")
	val rangeBX : Int = readInt("Please enter the left side of the B [_,]: ")
	val rangeBY : Int = readInt("Please enter the right side of the B [,_]: ")
	
	print("A+B = ")
	if (rangeAY in rangeBX..rangeBY || rangeBY in rangeAX..rangeAY) {
		var rangeLeft : Int = lowestThan(rangeAX, rangeBX)
		var rangeRight : Int = greaterThan(rangeAY, rangeBY)
		print("[${rangeLeft},${rangeRight}]")
	}
	else {
		print("[${rangeAX},${rangeAY}] + [${rangeBX},${rangeBY}]")
	}
}
