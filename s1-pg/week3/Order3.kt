fun main() {
	println("- Order3 -")
	
	var firstNumber = readInt("Enter the first number: ")
	var secondNumber = readInt("Enter the second number: ")
	
	if(firstNumber > secondNumber){
		firstNumber += secondNumber;
		secondNumber = firstNumber - secondNumber;
		firstNumber -= secondNumber;
	}
	
	var thirdNumber = readInt("Enter the third number: ")
	
	if(secondNumber > thirdNumber){
		secondNumber += thirdNumber;
		thirdNumber = secondNumber - thirdNumber;
		secondNumber -= thirdNumber;
		
		if(firstNumber > secondNumber){
			firstNumber += secondNumber;
			secondNumber = firstNumber - secondNumber;
			firstNumber -= secondNumber;
		}
	}
	
	println("Order is {${firstNumber},${secondNumber},${thirdNumber}}")
	
}