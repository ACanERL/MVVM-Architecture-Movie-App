plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.moviemood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.moviemood"
        minSdk = 24
        targetSdk = 33
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
        viewBinding=true
    }
}

dependencies {

    //Hilt
    implementation ("com.google.dagger:hilt-android:2.47")
    implementation("com.google.android.gms:play-services-basement:18.2.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt ("com.google.dagger:hilt-compiler:2.48")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")

    //OkHTTP client
    implementation ("com.squareup.okhttp3:okhttp:4.9.1")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")
    //rxjava2
    implementation ("io.reactivex.rxjava2:rxjava:2.1.9")
    implementation ("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    // Coroutine
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    // Coroutine Scope
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.4")
    //coil
    implementation ("io.coil-kt:coil:2.2.2")
    //paging
    implementation ("androidx.paging:paging-runtime:3.2.1")
    //shimmer effect
    implementation ("com.facebook.shimmer:shimmer:0.1.0@aar")
    //glide
    implementation  ("com.github.bumptech.glide:glide:4.12.0")
    kapt ("com.github.bumptech.glide:compiler:4.12.0")
    //lottie
    implementation ("com.airbnb.android:lottie:3.4.0")
    //swipe
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01")

    implementation ("com.airbnb.android:lottie:3.4.0")


    //Room components
    implementation ("androidx.room:room-runtime:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")
    kapt ("androidx.room:room-compiler:2.6.0")


    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    implementation  ("com.makeramen:roundedimageview:2.3.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}