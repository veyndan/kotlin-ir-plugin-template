/*
 * Copyright (C) 2020 Brian Norman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bnorm.template

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import kotlin.test.assertEquals
import org.jetbrains.kotlin.compiler.plugin.ComponentRegistrar
import org.junit.Test

class IrPluginTest {

//    @Test
//    fun `IR plugin want`() {
//        val result = compile(
//            sourceFile = SourceFile.kotlin(
//                "main.kt",
//                """
//                    @com.bnorm.template.constraint.Range(from = 1, to = 3)
//                    fun test(): com.bnorm.template.number.Int1To3 {
//                        return com.bnorm.template.number.Int3
//                    }
//                """
//            )
//        )
//        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
//
//        val kClazz = result.classLoader.loadClass("MainKt")
//        val test = kClazz.declaredMethods.single { it.name == "test" && it.parameterCount == 0 }
//        test.invoke(null)
//    }

    @Test
    fun `IR plugin success`() {
        val result = compile(
            sourceFile = SourceFile.kotlin(
                "main.kt",
                """
                    @com.bnorm.template.constraint.Range(from = 1, to = 3)
                    fun test(): Int {
                        return 3
                    }
                """
            )
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val kClazz = result.classLoader.loadClass("MainKt")
        val test = kClazz.declaredMethods.single { it.name == "test" && it.parameterCount == 0 }
        test.invoke(null)
    }

    @Test
    fun `IR plugin success 2`() {
        val result = compile(
            sourceFile = SourceFile.kotlin(
                "main.kt",
                """
                    @com.bnorm.template.constraint.Range(from = 1, to = 3)
                    fun test(): Int {
                        return 0 + 1
                    }
                """
            )
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val kClazz = result.classLoader.loadClass("MainKt")
        val test = kClazz.declaredMethods.single { it.name == "test" && it.parameterCount == 0 }
        test.invoke(null)
    }

    @Test
    fun `IR plugin success 3`() {
        val result = compile(
            sourceFile = SourceFile.kotlin(
                "main.kt",
                """
                    @com.bnorm.template.constraint.Range(from = 1, to = 3)
                    fun test(): Int {
                        return 0 + 1 + 1
                    }
                """
            )
        )
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val kClazz = result.classLoader.loadClass("MainKt")
        val test = kClazz.declaredMethods.single { it.name == "test" && it.parameterCount == 0 }
        test.invoke(null)
    }
}

fun compile(
    sourceFiles: List<SourceFile>,
    plugin: ComponentRegistrar = TemplateComponentRegistrar(),
): KotlinCompilation.Result {
    return KotlinCompilation().apply {
        sources = sourceFiles
        useIR = true
        compilerPlugins = listOf(plugin)
        inheritClassPath = true
    }.compile()
}

fun compile(
    sourceFile: SourceFile,
    plugin: ComponentRegistrar = TemplateComponentRegistrar(),
): KotlinCompilation.Result {
    return compile(listOf(sourceFile), plugin)
}
