-dontwarn jakarta.servlet.**
-dontwarn jakarta.mail.**
-dontwarn org.codehaus.janino.**
-dontwarn org.codehaus.commons.compiler.**

-keep class ch.qos.logback.** { *; }

# Fix for "duplicate definition of program class [module-info]"
-dontnote module-info
-dontwarn module-info

# Fix for Logback warnings
-dontwarn org.tukaani.xz.**

# Fix for androidx.navigation3 and navigationevent
-keep class androidx.navigation3.** { *; }
-keep class androidx.navigationevent.** { *; }
