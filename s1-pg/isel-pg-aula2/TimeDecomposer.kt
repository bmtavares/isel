fun main() {
	println("Time Decomposer")
	println("Tempo em segundos?")
	val tempoEmSegundos: Int = 3727
	println(tempoEmSegundos)
	println("O tempo em segundos, " + tempoEmSegundos + ", decomposto fica:")
	val tempoEmHoras: Int = tempoEmSegundos / 3600
	println("Horas: " + tempoEmHoras)
	val tempoEmMinutos: Int = tempoEmSegundos % 3600 / 60
	println("Min: " + tempoEmMinutos)
	println("Seg: " + tempoEmSegundos % 3600 % 60)

}