# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\AndroidSDK\sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#gson混淆
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn com.google.gson.**






#混淆ssl证书
-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}


#Annotation混淆
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes *JavascriptInterface*

#v4包混淆
-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep class android.support.v4.* { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment


#okio
-dontwarn okio.**
-keep class okio.**{*;}


#okhttp 混淆
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**

#okhttp3混淆
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-keep interface com.squareup.okhttp3.** { *; }

#retrofit2混淆
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#Serializable和Parcelable 混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}
-keepclassmembers class * implements android.os.Parcelable {
 public <fields>;
 private <fields>;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-dontwarn java.lang.invoke.*
-dontwarn **$$Lambda$*