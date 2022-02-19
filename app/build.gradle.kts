plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}


android {

    compileSdk = ProjectSetting.PROJECT_COMPILE_SDK

    defaultConfig {
        applicationId = ProjectSetting.PROJECT_APP_ID
        minSdk = ProjectSetting.PROJECT_MIN_SDK
        targetSdk = ProjectSetting.PROJECT_TARGET_SDK
        versionCode = ProjectSetting.PROJECT_VERSION_CODE
        versionName = ProjectSetting.PROJECT_VERSION_NAME

        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Naming APK // AAB
        setProperty("archivesBaseName", "${ProjectSetting.NAME_APK}(${versionName})")

        // Inject app name for debug
        resValue("string", "app_name", ProjectSetting.NAME_APP)
        resValue("string", "app_name_subtitle", ProjectSetting.NAME_APP_SUB)
        resValue("string", "theme_color_scheme_reskin", ProjectSetting.NAME_THEME_COLOR_SCHEME)

        // Inject admob id for debug
        resValue("string", "admob_publisher_id", AdmobValue.debugAdmobPublisherId)
        resValue("string", "admob_banner", AdmobValue.debugAdmobBanner)
        resValue("string", "admob_interstitial", AdmobValue.debugAdmobInterstitial)
        resValue("string", "admob_interstitial_video", AdmobValue.debugAdmobInterstitialVideo)
        resValue("string", "admob_rewarded", AdmobValue.debugAdmobRewarded)
        resValue("string", "admob_rewarded_interstitial", AdmobValue.debugAdmobRewardedInterstitial)
        resValue("string", "admob_native_advanced", AdmobValue.debugAdmobNativeAdvanced)
        resValue("string", "admob_native_advanced_video", AdmobValue.debugAdmobNativeAdvancedVideo)

    }

    signingConfigs {
        create("release") {
            // You need to specify either an absolute path or include the
            // keystore file in the same directory as the build.gradle file.
            // [PROJECT FOLDER NAME/app/[COPY YOUT KEY STORE] .jks in here
            storeFile = file(ProjectSetting.PLAYSTORE_STORE_FILE)
            storePassword = ProjectSetting.PLAYSTORE_STORE_PASSWORD
            keyAlias = ProjectSetting.PLAYSTORE_KEY_ALIAS
            keyPassword = ProjectSetting.PLAYSTORE_KEY_PASSWORD
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false

            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )

            // Generated Signed APK / AAB
            signingConfig = signingConfigs.getByName("release")

            // Inject app name for release
            resValue("string", "app_name", ProjectSetting.NAME_APP)
            resValue("string", "app_name_subtitle", ProjectSetting.NAME_APP_SUB)
            resValue("string", "theme_color_scheme_reskin", ProjectSetting.NAME_THEME_COLOR_SCHEME)

            // Inject admob id for release
            resValue("string", "admob_publisher_id", AdmobValue.releaseAdmobPublisherId)
            resValue("string", "admob_banner", AdmobValue.releaseAdmobBanner)
            resValue("string", "admob_interstitial", AdmobValue.releaseAdmobInterstitial)
            resValue("string", "admob_interstitial_video", AdmobValue.releaseAdmobInterstitialVideo)
            resValue("string", "admob_rewarded", AdmobValue.releaseAdmobRewarded)
            resValue("string", "admob_rewarded_interstitial", AdmobValue.releaseAdmobRewardedInterstitial)
            resValue("string", "admob_native_advanced", AdmobValue.releaseAdmobNativeAdvanced)
            resValue("string", "admob_native_advanced_video", AdmobValue.releaseAdmobNativeAdvancedVideo)

        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        resources {
            excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

}


dependencies {

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")

    implementation("androidx.room:room-runtime:2.4.1")
    implementation("androidx.room:room-ktx:2.4.1")
    implementation("androidx.room:room-rxjava2:2.4.1")
    implementation("androidx.room:room-guava:2.4.1")

    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.google.android.material:material:1.5.0")
    implementation("com.google.android.gms:play-services-ads:20.5.0")

    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.9.0")

    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    implementation("io.reactivex.rxjava2:rxjava:2.2.21")

    implementation("com.github.frogobox:frogo-android-sdk:2.0.7")
    implementation("com.github.frogobox:frogo-android-ui-kit:1.0.5")
    implementation("com.github.frogobox:frogo-consume-api:1.0.8")

    implementation("com.github.amirisback:frogo-recycler-view:4.0.2")
    implementation("com.github.amirisback:frogo-log:2.0.6")
    implementation("com.github.amirisback:frogo-admob:4.1.5")
    implementation("com.github.amirisback:frogo-notification:1.0.9")

    implementation("com.github.bumptech.glide:glide:4.12.0")

    implementation("com.facebook.stetho:stetho:1.5.1")
    implementation("com.readystatesoftware.chuck:library:1.1.0")

    api("com.google.dagger:dagger:2.38.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0-native-mt")

    kapt("androidx.lifecycle:lifecycle-compiler:2.5.0-alpha02")
    kapt("androidx.room:room-compiler:2.4.1")
    kapt("com.google.dagger:dagger-compiler:2.37")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

    implementation("io.insert-koin:koin-core:3.1.4") // Koin core features
    implementation("io.insert-koin:koin-android:3.1.4") // Koin main features for Android (Scope,ViewModel ...)
    implementation("io.insert-koin:koin-android-compat:3.1.4") // Koin Java Compatibility
    implementation("io.insert-koin:koin-androidx-workmanager:3.1.4") // Koin for Jetpack WorkManager
    implementation("io.insert-koin:koin-androidx-compose:3.1.4") // Koin for Jetpack Compose
    
}
