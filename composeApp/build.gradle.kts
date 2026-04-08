import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    //PARA FIREBASE
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.1.0"
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            //KTOR
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            //SERIALIZATION
            implementation(libs.kotlinx.serialization.json)

            //COIL
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            //INYECCION DE DEPENDENCIAS CON KOIN
            implementation("io.insert-koin:koin-compose:4.1.1")
            implementation("io.insert-koin:koin-compose-viewmodel:4.1.1")
            implementation("io.insert-koin:koin-compose-viewmodel-navigation:4.1.1")

            //MAS ICONOS
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.materialIconsExtended)

            //FIREBASE
            implementation("dev.gitlive:firebase-firestore:2.1.0")
            implementation("dev.gitlive:firebase-auth:2.1.0")

            //DATASTORE
            implementation("androidx.datastore:datastore:1.2.0")
            implementation("androidx.datastore:datastore-preferences:1.2.0")

            //KTOR
            implementation(libs.ktor.client.core)
            implementation("io.ktor:ktor-client-auth:3.3.0")
            implementation(libs.ktor.client.content.negotiation)
            implementation("io.ktor:ktor-server-cors:3.3.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.0")
            implementation("io.ktor:ktor-client-logging:3.3.0")

            //LAMACENAMIENTO SEGURO TOKEN
            implementation("com.russhwolf:multiplatform-settings:1.3.0")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("io.ktor:ktor-client-okhttp:3.3.0")
        }
    }
}

android {
    namespace = "dam.sequeros.klassroom"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "dam.sequeros.klassroom"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "dam.sequeros.klassroom.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dam.sequeros.klassroom"
            packageVersion = "1.0.0"
        }
    }
}
