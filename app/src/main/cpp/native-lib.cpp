#include <jni.h>
#include <android/log.h>
#include <thread>
#include <atomic>
#include <cstring>
#include <unistd.h>  // ВАЖНО: добавлено для sleep()

#define LOG_TAG "NativeMiner"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// Заглушки для функций XMRig (реальные будут из библиотеки)
extern "C" {
    int xmrig_start(const char* pool, const char* wallet, int threads);
    void xmrig_stop();
    double xmrig_hashrate();
    long xmrig_accepted();
    long xmrig_rejected();
}

static std::atomic<bool> isRunning{false};
static double currentHashrate = 0.0;
static long acceptedShares = 0;
static long rejectedShares = 0;

// Поток мониторинга
void* monitorThread(void* arg) {
    while (isRunning) {
        currentHashrate = xmrig_hashrate();
        acceptedShares = xmrig_accepted();
        rejectedShares = xmrig_rejected();
        sleep(2);  // теперь будет работать
    }
    return nullptr;
}

extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_startMining(
        JNIEnv* env, jobject thiz,
        jstring poolUrl, jstring walletAddress,
        jstring workerName, jstring password,
        jstring algo, jint threads) {

    if (isRunning) {
        return JNI_TRUE;
    }

    const char* pool = env->GetStringUTFChars(poolUrl, nullptr);
    const char* wallet = env->GetStringUTFChars(walletAddress, nullptr);
    const char* pass = env->GetStringUTFChars(password, nullptr);
    const char* algoStr = env->GetStringUTFChars(algo, nullptr);

    int result = xmrig_start(pool, wallet, threads);

    if (result == 0) {
        isRunning = true;
        pthread_t thread;
        pthread_create(&thread, nullptr, monitorThread, nullptr);
        pthread_detach(thread);
    }

    env->ReleaseStringUTFChars(poolUrl, pool);
    env->ReleaseStringUTFChars(walletAddress, wallet);
    env->ReleaseStringUTFChars(password, pass);
    env->ReleaseStringUTFChars(algo, algoStr);

    return result == 0 ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT jboolean JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_stopMining(JNIEnv* env, jobject thiz) {
    if (!isRunning) {
        return JNI_TRUE;
    }
    isRunning = false;
    xmrig_stop();
    return JNI_TRUE;
}

JNIEXPORT jdouble JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getHashrate(JNIEnv* env, jobject thiz) {
    return currentHashrate;
}

JNIEXPORT jlong JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getAcceptedShares(JNIEnv* env, jobject thiz) {
    return acceptedShares;
}

JNIEXPORT jlong JNICALL
Java_com_lottttto_miner_utils_NativeMinerLib_getRejectedShares(JNIEnv* env, jobject thiz) {
    return rejectedShares;
}

}
