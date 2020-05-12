//
// Created by laomao on 2019/7/19.
//

#include <jni.h>
#include <string>
#include "art.h"

extern "C" JNIEXPORT jstring Java_com_example_jni_JniUtils_getString
  (JNIEnv *env, jclass){
    return env->NewStringUTF("hello");
}


extern "C"
JNIEXPORT void JNICALL
Java_com_example_jni_JniUtils_replace(JNIEnv *env, jclass type,jobject src, jobject dest) {
art::mirror::ArtMethod* smeth =
			(art::mirror::ArtMethod*) env->FromReflectedMethod(src);

	art::mirror::ArtMethod* dmeth =
			(art::mirror::ArtMethod*) env->FromReflectedMethod(dest);

//	reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->class_loader_ =
//			reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->class_loader_; //for plugin classloader
	reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->clinit_thread_id_ =
			reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->clinit_thread_id_;


//	reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->status_ =
//			reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->status_ -1;

	reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->status_ =
            (art::mirror::Class::Status)(reinterpret_cast<art::mirror::Class*>(smeth->declaring_class_)->status_ -1);


	//for reflection invoke
	reinterpret_cast<art::mirror::Class*>(dmeth->declaring_class_)->super_class_ = 0;

	smeth->declaring_class_ = dmeth->declaring_class_;
	smeth->access_flags_ = dmeth->access_flags_  | 0x0001;
	smeth->dex_code_item_offset_ = dmeth->dex_code_item_offset_;
	smeth->dex_method_index_ = dmeth->dex_method_index_;
	smeth->method_index_ = dmeth->method_index_;
	smeth->hotness_count_ = dmeth->hotness_count_;

	smeth->ptr_sized_fields_.dex_cache_resolved_methods_ =
			dmeth->ptr_sized_fields_.dex_cache_resolved_methods_;
	smeth->ptr_sized_fields_.dex_cache_resolved_types_ =
			dmeth->ptr_sized_fields_.dex_cache_resolved_types_;

	smeth->ptr_sized_fields_.entry_point_from_jni_ =
			dmeth->ptr_sized_fields_.entry_point_from_jni_;
	smeth->ptr_sized_fields_.entry_point_from_quick_compiled_code_ =
			dmeth->ptr_sized_fields_.entry_point_from_quick_compiled_code_;
}