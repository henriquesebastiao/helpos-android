# Retrofit
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keep class retrofit2.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.conscrypt.**

# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.**
-keep,includedescriptorclasses class com.henriquesebastiao.helpos.**$$serializer { *; }
-keepclassmembers class com.henriquesebastiao.helpos.** {
    *** Companion;
}
-keepclasseswithmembers class com.henriquesebastiao.helpos.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Hilt
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends androidx.lifecycle.ViewModel { <init>(...); }

# Coil
-dontwarn coil3.**

# ZXing
-keep class com.google.zxing.** { *; }

# Compose
-keep class androidx.compose.runtime.** { *; }
