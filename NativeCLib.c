#include <jni.h>
#include <stdio.h>
#include <string.h>
#include "NativeJavaLib.h"

JNIEXPORT void JNICALL Java_NativeJavaLib_processString
  (JNIEnv *env, jobject obj, jstring javaString) {
    char buffer[10];
    const char *nativeString = (*env)->GetStringUTFChars(env, javaString, 0);
    strcpy(buffer, nativeString);
    printf("Native Code: Received string and copied into buffer: %s\n", buffer);
    (*env)->ReleaseStringUTFChars(env, javaString, nativeString);
}
