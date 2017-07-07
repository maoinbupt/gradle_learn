# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Develop\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#about proguard see http://proguard.sourceforge.net/index.html#manual/usage.html

#Specifies the number of optimization passes to be performed
-optimizationpasses 5

#Specifies not to generate mixed-case class names while obfuscating. By default, obfuscated class names can contain a mix of upper-case characters and lower-case characters
-dontusemixedcaseclassnames

#Specifies not to ignore non-public library classes. As of version 4.5, this is the default setting.
-dontskipnonpubliclibraryclasses

#Specifies not to preverify the processed class files. By default, class files are preverified if they are targeted at Java Micro Edition or at Java 6 or higher
-dontpreverify

#Specifies to print any warnings about unresolved references and other important problems, but to continue processing in any case
-ignorewarnings

#Specifies not to ignore package visible library class members (fields and methods).
-dontskipnonpubliclibraryclassmembers

-optimizations !code/simplification/arithmetic,!class/merging/*,!code/allocation/variable,!class/unboxing/enum

-printusage usage.txt
-printmapping mapping.txt
-printseeds seeds.txt

-keepattributes *Annotation*,SourceFile,LineNumberTable,InnerClasses
-keepattributes *JavascriptInterface*

#members of public
-keepclasseswithmembers class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {*;}

-keep class * implements java.io.Serializable {*;}

#class kept by android and java
-keep public class * extends android.app.Activity
-keep public class * extends android.app.ActivityGroup{
    *;
}
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference


-keep class android.content.ServiceConnection

-keep public class * extends android.view.** {
    *;
}
-keep public class * implements android.view.** {
    *;
}

-keep public class * implements java.util.Observer {
    *;
}
-keep public class * extends android.widget.**{
	*;
}

-keep public class * implements android.widget.** {
    *;
}

-keep class * extends android.content.ServiceConnection {
    *;
}