/*
 * Copyright (C)2018 - Deny Prasetyo <jasoet87@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.jasoet.funpdf

enum class PageOrientation {
    LANDSCAPE, PORTRAIT
}

enum class Toggle {
    ENABLE, DISABLE
}

class Parameter<in T : Any>(private val name: String, private val defaultValue: T? = null) {

    private var value: T? = null

    operator fun invoke(value: T) {
        this.value = value
    }

    fun compile(): List<String> {
        val value: T? = this.value ?: this.defaultValue
        return when (value) {
            value == null -> emptyList()
            is PageOrientation -> {
                val orientation = if (value == PageOrientation.LANDSCAPE) "Landscape" else "Portrait"
                listOf("--$name", orientation)
            }
            is Int -> listOf("--$name", "$value")
            is Float -> listOf("--$name", "%.2f".format(value))
            is String -> listOf("--$name", value)
            is Boolean -> if (value) listOf("--$name") else emptyList()
            is Toggle -> if (value == Toggle.ENABLE) listOf("--$name") else listOf("--no-$name")
            is List<*> -> value.filter { it is String }.map { it as String }.map { listOf("--$name", it) }.flatten()
            else -> emptyList()
        }
    }
}
