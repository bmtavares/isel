fun main() {
	println("- Greater3 -");
	
	var greater : Int = Int.MIN_VALUE;
	
	var input : Int = readInt("Please enter the first number: ");	
	greater = if(input > greater)
				input;
			  else
				greater;
	// Kotlin doesn't have ternaries. My day is ruined and my disappointment is immesurable.
	// greater = input > greater ? input : greater;
	
	input = readInt("Please enter the second number: ");
	greater = if(input > greater)
				input;
			  else
				greater;
				
	input = readInt("Please enter the third number: ");
	greater = if(input > greater)
				input;
			  else
				greater;
				
	println("The greatest number is ${greater}");
	
	// val num1 = readInt("Please enter the first number: ");
	
	// val num2 = readInt("Please enter the second number: ");
	
	// val num3 = readInt("Please enter the third number: ");
	
	// if(num1 == num2 or num2 == num3 or num1 == num3) {
		// println("Two of them are the same. Please don't try that again.");
	// }
	// else if(num1 > num2) {
		// if(num3 > num1) {
			// println("${num3} is the greater of both numbers.");
		// }
		// else {
			// println("${num1} is the greater of both numbers.");
		// }
	// }
	// else {
		// if(num3 > num2) {
			// println("${num3} is the greater of both numbers.");
		// }
		// else{
			// println("${num2} is the greater of both numbers.");
		// }
	// }
}