fun main() {
	println("- Greater2 -");
	
	val num1 = readInt("Please enter the first number: ");
	
	val num2 = readInt("Please enter the second number: ");
	
	if(num1 == num2) {
		println("They are the same. Please don't try that again.");
	}
	else if(num1 > num2) {
		println("${num1} is the greater of both numbers.");
	}
	else {
		println("${num2} is the greater of both numbers.");
	}
}