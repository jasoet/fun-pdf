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

package id.jasoet.funpdf.extension

import id.jasoet.funpdf.Config
import id.jasoet.funpdf.HtmlToPdf
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.URL


fun URL.convertToPdf(output: OutputStream, configuration: Config.() -> Unit = {}) {
    HtmlToPdf(configuration = configuration).convert(input = this, output = output)
}

fun URL.convertToPdf(output: File, configuration: Config.() -> Unit = {}) {
    HtmlToPdf(configuration = configuration).convert(input = this, output = output)
}

fun URL.convertToPdf(configuration: Config.() -> Unit = {}): InputStream? {
    return HtmlToPdf(configuration = configuration).convert(input = this)
}

