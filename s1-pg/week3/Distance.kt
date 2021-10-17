fun main () {
	println("- Distance between 2 points -")
	
	print("What is X1? ")
	val x1 : Double = readLine()!!.toDouble()
	println()
	print("What is Y1? ")
	val y1 : Double = readLine()!!.toDouble()
	println()
	
	print("What is X2? ")
	val x2 : Double = readLine()!!.toDouble()
	println()
	print("What is Y2? ")
	val y2 : Double = readLine()!!.toDouble()
	println()
	
	val distance : Double = Math.sqrt(
		Math.pow((x2 - x1), 2.0) + Math.pow((y2 - y1), 2.0)
		)
	println("The total distance between ($x1,$y1) and ($x2,$y2) is $distance");
}