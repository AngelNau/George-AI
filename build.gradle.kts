// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("androidx.navigation.safeargs.kotlin") version "2.6.0" apply false
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}