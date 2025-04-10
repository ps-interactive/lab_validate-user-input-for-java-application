#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

JNIEXPORT void JNICALL Java_ByteBufferNativeJavaLib_processBuffer
  (JNIEnv *env, jobject obj, jobject byteBuffer) {
    jclass bufferClass = (*env)->GetObjectClass(env, byteBuffer);
    jfieldID fid_hb = (*env)->GetFieldID(env, bufferClass, "hb", "[B");
    jfieldID fid_offset = (*env)->GetFieldID(env, bufferClass, "offset", "I");
    jmethodID mid_limit = (*env)->GetMethodID(env, bufferClass, "limit", "()I");
    if (fid_hb == NULL || fid_offset == NULL || mid_limit == NULL) {
        printf("Native Code: Could not find necessary fields/methods for ByteBuffer access.\n");
        return;
    }
    jbyteArray byteArray = (jbyteArray)(*env)->GetObjectField(env, byteBuffer, fid_hb);
    jint offset = (*env)->GetIntField(env, byteBuffer, fid_offset);
    jint limit = (*env)->CallIntMethod(env, byteBuffer, mid_limit);

    if (byteArray != NULL) {
        unsigned char* BufferBytes = (unsigned char*)(*env)->GetByteArrayElements(env, byteArray, NULL);
        if (BufferBytes != NULL) {
            printf("Native Code: Buffer Limit: %d, Offset: %d\n", limit, offset);
            printf("Native Code: Buffer Content (from offset, up to limit, max 20 shown): ");
            jint count = 0;
            for (jint i = offset; i < limit && count < 20; i++, count++) {
                 printf("%02X ", BufferBytes[i]);
            }
            printf("\n");
            (*env)->ReleaseByteArrayElements(env, byteArray, (jbyte*)BufferBytes, JNI_ABORT);
        } else {
             printf("Native Code: Failed to get byte array elements from buffer.\n");
        }
    } else {
         printf("Native Code: Could not access ByteBuffer data array ('hb' field).\n");
    }
}