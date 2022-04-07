import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    application
}

group = "me.bmtavares"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    flatDir { dirs("libs") }
}



tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}

application {
    mainClass.set("MainKt")
}

dependencies {     // Adicionar esta secção “dependencies” caso não exista
    implementation("pt.isel:CanvasLib-jvm:1.0.1") // Adicionar esta linha
}