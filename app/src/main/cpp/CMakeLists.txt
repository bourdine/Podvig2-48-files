cmake_minimum_required(VERSION 3.18.1)
project("lottttto-miner")

# Путь к библиотекам (предполагается, что они лежат в app/libs/${ANDROID_ABI})
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

# Импортируем библиотеку XMRig 
add_library(xmrig SHARED IMPORTED)
set_target_properties(xmrig
    PROPERTIES
    IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI}/libxmrig.so
)

# Импортируем библиотеку cpuminer (не нужна, так как только Monero)
# add_library(cpuminer SHARED IMPORTED)
# set_target_properties(cpuminer
#     PROPERTIES
#     IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs/${ANDROID_ABI}/libcpuminer.so
# )

# Наша JNI-обёртка
add_library(miner_jni SHARED native-lib.cpp)

find_library(log-lib log)

target_link_libraries(miner_jni
    xmrig
    ${log-lib}
)
