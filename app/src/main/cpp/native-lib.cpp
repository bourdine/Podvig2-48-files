#include <jni.h>
#include <android/log.h>
#include <unistd.h>

#define LOG_TAG "NativeMiner"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

// Моковые данные для демонстрации
static double mockHashrate = 1250.0;
static long mockAccepted = 12345;
static long mockRejected = 123;

extern "C" {

// JNI методы
JNIEXPORT jboolean JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_startMining(
        JNIEnv* env, jobject thiz,
        jstring poolUrl, jstring walletAddress,
        jstring workerName, jstring password,
        jstring algo, jint threads) {

    const char* pool = env->GetStringUTFChars(poolUrl, nullptr);
    const char* wallet = env->GetStringUTFChars(walletAddress, nullptr);
    const char* worker = env->GetStringUTFChars(workerName, nullptr);
    const char* pass = env->GetStringUTFChars(password, nullptr);
    const char* algorithm = env->GetStringUTFChars(algo, nullptr);
    
    LOGD("startMining called - pool: %s, wallet: %s, worker: %s, threads: %d, algo: %s", 
         pool, wallet, worker, threads, algorithm);
    
    env->ReleaseStringUTFChars(poolUrl, pool);
    env->ReleaseStringUTFChars(walletAddress, wallet);
    env->ReleaseStringUTFChars(workerName, worker);
    env->ReleaseStringUTFChars(password, pass);
    env->ReleaseStringUTFChars(algo, algorithm);
    
    return JNI_TRUE;
}

JNIEXPORT jboolean JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_stopMining(JNIEnv* env, jobject thiz) {
    LOGD("stopMining called");
    return JNI_TRUE;
}

JNIEXPORT jdouble JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getHashrate(JNIEnv* env, jobject thiz) {
    return mockHashrate;
}

JNIEXPORT jlong JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getAcceptedShares(JNIEnv* env, jobject thiz) {
    return mockAccepted;
}

JNIEXPORT jlong JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getRejectedShares(JNIEnv* env, jobject thiz) {
    return mockRejected;
}

}
