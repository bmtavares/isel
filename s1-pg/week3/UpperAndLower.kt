fun main() {
	println("- Char upper or lower case? -");

	print("Enter a character: ");
	val inputChar : Char = readLine()!![0];
	
	// Warning: Deprecated
	// val asciiValue : Int = inputChar.toInt()
	val asciiValue : Int = inputChar.code;
	
	// Lowercase
	if (asciiValue  >= 65 && asciiValue <= 90) {
		println("Your character is uppercase.");
		println("Here it is in lowercase: ${inputChar + 32}");
	}
	// Uppercase
	else if (asciiValue  >= 97 && asciiValue <= 122) {
		println("Your character is lowercase.");
		println("Here it is in uppercase: ${inputChar - 32}");
	}
	else {
		println("Oops! Looks like you didn't input a standard a-z character!");
	}
}