fun main() {
    val intA = readInt("A? ");
    val intB = readInt("B? ");

    println("A < B == ${intA < intB}")
    println("A + B == ${intA + intB}")
    println("A - B == ${intA - intB}")
    println("A x B == ${intA * intB}")
    println("A / B == ${intA / (1f * intB)}")
}