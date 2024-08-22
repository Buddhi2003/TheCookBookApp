plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.thecookbook"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.thecookbook"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        renderscriptTargetApi = 34
        renderscriptSupportModeEnabled=true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.drawerlayout:drawerlayout:1.1.1")
    implementation("com.github.wonderkiln:blurkit-android:1.1.1")
    implementation("com.github.Dimezis:BlurView:version-2.0.3")
    implementation("jp.wasabeef:blurry:4.0.1")
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}