# Common KMP and Compose rules
-keep class com.yasinmoridi.miniverse.** { *; }

# Koin rules
-keepclassmembers class * {
    @org.koin.core.annotation.KoinInternalApi *;
}
-keep class org.koin.** { *; }

# Ktor rules
-keep class io.ktor.** { *; }
-dontwarn io.ktor.util.debug.IntellijIdeaDebugDetector
-dontwarn java.lang.management.**

# Kotlin Serialization
-keepattributes *Annotation*, EnclosingMethod, Signature
-keepclassmembers class ** {
    @kotlinx.serialization.Serializable *;
}
-keepclassmembers class **$companion {
    public kotlinx.serialization.KSerializer serializer();
}

# Android Compose rules (usually handled by default, but safe to have)
-keep class androidx.compose.runtime.** { *; }

# General shrinking prevention for resources used via reflection (if any)
-keepclassmembers class **.R$* {
    public static <fields>;
}