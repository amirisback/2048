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

    implementation(Androidx.appCompat)
    implementation(Androidx.Core.ktx)
    implementation(Androidx.preferenceKtx)
    implementation(Androidx.constraintLayout)

    implementation(Androidx.Lifecycle.viewmodelKtx)
    implementation(Androidx.Lifecycle.livedataKtx)

    implementation(Androidx.Room.runtime)
    implementation(Androidx.Room.ktx)
    implementation(Androidx.Room.rxJava3)

    implementation(Google.material)
    implementation(Google.gson)
    implementation(Google.admob)

    implementation(Square.okhttp)
    implementation(Square.okhttpLogging)

    implementation(Square.Retrofit2.retrofit)
    implementation(Square.Retrofit2.converterGson)
    implementation(Square.Retrofit2.adapterRxJava3)

    implementation(Reactivex.rxJava3)
    implementation(Reactivex.rxAndroid3)

    implementation(Frogo.sdk)
    implementation(Frogo.ui)
    implementation(Frogo.consumeApi)
    implementation(Frogo.recyclerView)
    implementation(Frogo.admob)
    implementation(Frogo.log)
    implementation(Frogo.notification)

    implementation(Koin.core)
    implementation(Koin.android)
    implementation(Koin.androidCompat)
    implementation(Koin.androidxWorkManager)
    implementation(Koin.androidxCompose)
    implementation(Koin.ktor)

    implementation(Util.glide)

    kapt(Androidx.Lifecycle.compiler)
    kapt(Androidx.Room.compiler)
    kapt(Util.glideCompiler)
    
}