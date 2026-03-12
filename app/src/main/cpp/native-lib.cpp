#include <jni.h>
#include <android/log.h>
#include <unistd.h>

#define LOG_TAG "NativeMiner"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

// Временные заглушки для функций XMRig
static double mockHashrate = 1250.0;
static long mockAccepted = 12345;
static long mockRejected = 123;

extern "C" {

// Заглушки для демонстрации
int xmrig_start(const char* pool, const char* wallet, int threads) {
    LOGD("Mock xmrig_start called with pool=%s, wallet=%s", pool, wallet);
    return 0; // успех
}

void xmrig_stop() {
    LOGD("Mock xmrig_stop called");
}

double xmrig_hashrate() {
    return mockHashrate;
}

long xmrig_accepted() {
    return mockAccepted;
}

long xmrig_rejected() {
    return mockRejected;
}

// JNI методы
JNIEXPORT jboolean JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_startMining(
        JNIEnv* env, jobject thiz,
        jstring poolUrl, jstring walletAddress,
        jstring workerName, jstring password,
        jstring algo, jint threads) {

    const char* pool = env->GetStringUTFChars(poolUrl, nullptr);
    const char* wallet = env->GetStringUTFChars(walletAddress, nullptr);
    
    LOGD("startMining called - pool: %s, wallet: %s", pool, wallet);
    
    env->ReleaseStringUTFChars(poolUrl, pool);
    env->ReleaseStringUTFChars(walletAddress, wallet);
    
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
