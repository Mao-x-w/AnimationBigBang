package com.test.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        println "this is my first javassist demo version update"

        project.android.registerTransform(new MyPluginTransform(project))
    }
}