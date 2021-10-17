fun main() {
	val num : Int = 156
	
	println("Primeiro digito: " + (num / 100))
	println("Segundo digito: " + (num % 100 / 10))
	println("Terceiro digito: " + (num % 100 % 10))

	println("Soma = " + ((num / 100) + (num % 100 / 10) + (num % 100 % 10)))
}