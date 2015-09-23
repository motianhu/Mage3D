LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_JNI_SHARED_LIBRARIES := 
    liba3m \
    libja3m \

LOCAL_STATIC_JAVA_LIBRARIES := ngin3d \

LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := Mage3D

LOCAL_SDK_VERSION := current

include $(BUILD_PACKAGE)

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := ngin3d:libs/ngin3d.jar \

LOCAL_PREBUILT_LIBS := 
    liba3m:lib/armeabi/liba3m.so \
    libja3m:lib/armeabi/libja3m.so \
    
# Use the following include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
