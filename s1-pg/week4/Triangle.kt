fun main() {
    val sideA = readInt("Lado A? ");
    val sideB = readInt("Lado B? ");
    val sideC = readInt("Lado C? ");

    val type = if (sideA == sideB && sideB == sideC) {
        "Equilátero"
    } else if (sideA == sideB || sideA == sideC || sideB == sideC) {
        "Isósceles"
    } else if (sideA > (sideB + sideC) || sideB > (sideA + sideC) || sideC > (sideA + sideB)) {
        "Impossível"
    } else {
        "Escaleno"
    }

    println("O triângulo (${sideA},${sideB},${sideC}) é ${type}")
}