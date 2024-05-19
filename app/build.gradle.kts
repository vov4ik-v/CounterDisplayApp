val versionCatalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.spm.vasylyshyn.counterdisplayapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.spm.vasylyshyn.counterdisplayapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {
    versionCatalog.libraryAliases.forEach { libraryAlias ->
        versionCatalog.findLibrary(libraryAlias).ifPresent {
            implementation(it)
        }
    }
    /*
    // Core dependencies
    implementation("androidx.core:core-ktx:${versions.coreKtx}")
    implementation("androidx.appcompat:appcompat:${versions.appcompat}")
    implementation("com.google.android.material:material:${versions.material}")
    implementation("androidx.constraintlayout:constraintlayout:${versions.constraintLayout}")
    implementation("it.sephiroth.android.library.horizontallistview:hlistview:${versions.hlistview}")

    // Google Play Services
    implementation("com.google.android.gms:play-services-base:${versions.playServicesBase}")
    implementation("com.google.android.gms:play-services-auth:${versions.playServicesAuth}")

    // Jetpack Compose dependencies
    implementation("androidx.compose.ui:ui:${versions.compose}")
    implementation("androidx.compose.material:material:${versions.compose}")
    implementation("androidx.compose.material3:material3-android:${versions.material3}")
    implementation("androidx.compose.ui:ui-tooling-preview:${versions.compose}")
    implementation("androidx.compose.runtime:runtime-livedata:${versions.compose}")
    implementation("androidx.activity:activity-compose:${versions.compose}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${versions.lifecycle}")
    implementation("androidx.compose.ui:ui-viewbinding:${versions.compose}")

    // Testing
    testImplementation("junit:junit:${versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${versions.testExtJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${versions.espressoCore}")

    // Miscellaneous
    implementation("pl.droidsonroids.gif:android-gif-drawable:${versions.gifDrawable}")
    implementation("com.github.PhilJay:MPAndroidChart:${versions.mpAndroidChart}")

    // Networking and serialization
    implementation("com.squareup.okhttp3:okhttp:${versions.okhttp}")
    implementation("com.squareup.okhttp3:logging-interceptor:${versions.loggingInterceptor}")
    implementation("com.squareup.retrofit2:retrofit:${versions.retrofit}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${versions.serializationConverter}")
    implementation("com.squareup.retrofit2:converter-gson:${versions.converterGson}")
    implementation("com.squareup.retrofit2:adapter-rxjava2:${versions.adapterRxJava2}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${versions.kotlinSerializationJson}")

    // Dependency injection
    implementation("io.insert-koin:koin-android:${versions.koin}")

    // Ktor client
    implementation("io.ktor:ktor-client-android:${versions.ktor}")
    implementation("io.ktor:ktor-client-serialization:${versions.ktor}")
    implementation("io.ktor:ktor-client-logging-jvm:${versions.ktor}")

    // Jetpack Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${versions.lifecycle}")

    // UI components
    implementation("de.hdodenhof:circleimageview:${versions.circleImageView}")
    
     */
}
