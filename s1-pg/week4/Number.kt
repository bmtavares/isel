fun main() {
    val num = readInt("Número positivo? ");
    println("${num} é múltiplo de 3 == ${num % 3 == 0}")
    val count = num.toString().length
    println("${num} tem ${count} dígitos")
    val heaviest = num.toString()[0] - '0'
    val lightest = num.toString()[count - 1] - '0'
    println("${heaviest} e ${lightest} são pares == ${heaviest % 2 == 0 && lightest % 2 == 0}")
    println("${heaviest} - ${lightest} == ${heaviest - lightest}")
}