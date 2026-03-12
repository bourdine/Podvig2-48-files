cmake_minimum_required(VERSION 3.18.1)
project("lottttto-miner")

# Добавляем флаги в зависимости от архитектуры
if(CMAKE_ANDROID_ARCH_ABI STREQUAL "arm64-v8a")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -march=armv8-a+fp+simd")
endif()

# Базовая оптимизация для всех архитектур
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3 -fomit-frame-pointer -funroll-loops")

# Создаем библиотеку из нашего C++ кода
add_library(miner_jni SHARED native-lib.cpp)

# Находим системную библиотеку для логирования
find_library(log-lib log)

# Линкуем нашу библиотеку с системными библиотеками
target_link_libraries(miner_jni
    ${log-lib}
)
