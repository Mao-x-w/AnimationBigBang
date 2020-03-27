package com.laomao.apt_compiler.kotlin.utils

import com.bennyhuo.aptutils.types.ClassType

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 下午5:41
 */

val CONTEXT=ClassType("android.content.Context")
val INTENT=ClassType("android.content.Intent")
val ACTIVITY=ClassType("android.app.Activity")
val BUNDLE=ClassType("android.os.Bundle")



val ACTIVITYBUILDER=ClassType("com.laomao.apt_api.ActivityBuilder")
val BUNDLE_UTILS=ClassType("com.laomao.apt_api.BundleUtils")