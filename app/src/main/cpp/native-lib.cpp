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

// Заглушки для функций XMRig - теперь они определены внутри этого же файла
int xmrig_start(const char* pool, const char* wallet, int threads) {
    LOGD("Mock xmrig_start called with pool=%s, wallet=%s, threads=%d", pool, wallet, threads);
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
    const char* worker = env->GetStringUTFChars(workerName, nullptr);
    
    LOGD("startMining called - pool: %s, wallet: %s, worker: %s", pool, wallet, worker);
    
    // Вызываем нашу заглушку
    int result = xmrig_start(pool, wallet, threads);
    
    env->ReleaseStringUTFChars(poolUrl, pool);
    env->ReleaseStringUTFChars(walletAddress, wallet);
    env->ReleaseStringUTFChars(workerName, worker);
    env->ReleaseStringUTFChars(password, nullptr);
    env->ReleaseStringUTFChars(algo, nullptr);
    
    return result == 0 ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_stopMining(JNIEnv* env, jobject thiz) {
    LOGD("stopMining called");
    xmrig_stop();
    return JNI_TRUE;
}

JNIEXPORT jdouble JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getHashrate(JNIEnv* env, jobject thiz) {
    return xmrig_hashrate();
}

JNIEXPORT jlong JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getAcceptedShares(JNIEnv* env, jobject thiz) {
    return xmrig_accepted();
}

JNIEXPORT jlong JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getRejectedShares(JNIEnv* env, jobject thiz) {
    return xmrig_rejected();
}

}
