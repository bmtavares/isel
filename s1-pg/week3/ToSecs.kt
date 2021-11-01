fun main() {
	println("- ToSecs -")
	
	//print("Enter hours (0 to 23): ");
	//val hours : Int = readLine()!!.toInt();
	val hours = readInt("Enter hours (0 to 23): ");
	
	// if(hours < 0 || hours > 23) {
	if (!(hours in 0..23)) {
		println("Oops..! That's out of range!");
		return;
	}
	
	//print("Enter minutes (0 to 59): ");
	//val minutes : Int = readLine()!!.toInt();
	val minutes = readInt("Enter minutes (0 to 59): ");
	
	// if(minutes < 0 || minutes > 59) {
	if (!(minutes in 0..59)) {
		println("Oops..! That's out of range!");
		return;
	}
	
	//print("Enter seconds (0 to 59): ");
	//val seconds : Int = readLine()!!.toInt();
	val seconds = readInt("Enter seconds (0 to 59): ");
	
	// if(seconds < 0 || seconds > 59) {
	if (!(minutes in 0..59)) {
		println("Oops..! That's out of range!");
		return;
	}
	
	// val totalInSeconds : Int = hours * 3600 + minutes * 60 + seconds;
	
	println("The total value in seconds is: ${totalInSeconds(hours, minutes, hours)}s");

}

fun totalInSeconds(hours : Int, minutes : Int, seconds : Int) : Int {
	return hours * 3600 + minutes * 60 + seconds;
}