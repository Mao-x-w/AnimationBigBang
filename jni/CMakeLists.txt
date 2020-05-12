cmake_minimum_required(VERSION 3.4.1)

add_library( # Sets the name of the library.
             native-lib                        #c/cpp代码将要编译成为so库的名称，java代码加载库文件要用这个名称
             SHARED
             src/main/jni/native-lib.cpp  #cpp代码文件路径 这里可以随意添加c、c++文件
              )

find_library( # Sets the name of the path variable.
              log-lib
              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

target_link_libraries( # Specifies the target library.
                       native-lib
                       ${log-lib})