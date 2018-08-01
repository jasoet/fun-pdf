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

import id.jasoet.funkommand.execute
import id.jasoet.funkommand.executeToString
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.net.URL

class HtmlToPdf(executable: String = "", configuration: Config.() -> Unit = {}) {
    private val log = LoggerFactory.getLogger(HtmlToPdf::class.java)
    private val config: Config = Config()
    private var executable: String

    init {
        configuration(config)

        this.executable = if (executable.isExecutable()) {
            executable
        } else {
            log.debug("$executable is not exists or not executable, use default binary")
            which("wkhtmltopdf")
        }

    }

    fun convert(
        input: Any,
        output: Any? = null,
        environment: Map<String, String> = emptyMap(),
        directory: String = System.getProperty("user.home"),
        processConfig: (ProcessBuilder) -> Unit = {}
    ): InputStream? {

        val inputParameter: String = when (input) {
            is InputStream, is String -> "-"
            is File -> input.absolutePath
            is URL -> {
                when (input.protocol) {
                    "https", "http", "file" -> input.toString()
                    else -> throw ProtocolNotSupportedException("Protocol ${input.protocol} not Supported")
                }
            }
            else -> throw IllegalArgumentException("Input Type ${input::class} is not Supported")
        }

        val outputParameter: String = when (output) {
            is File -> output.absolutePath
            else -> "-"
        }

        val command: List<String> = listOf(executable, "-q") + config.parameters() + inputParameter + outputParameter

        log.debug("Command to Execute ${command.joinToString(" ")}")

        val commandInput = when (input) {
            is InputStream, is String -> input
            else -> null
        }

        val commandOutput = when (output) {
            is OutputStream -> output
            else -> null
        }

        return command.execute(
            input = commandInput,
            output = commandOutput,
            environment = environment,
            directory = directory,
            config = processConfig
        )
    }

}

private fun which(binaryName: String): String {
    val operatingSystem = System.getProperty("os.name")?.toLowerCase() ?: ""
    val command = if (operatingSystem.contains("windows")) "where $binaryName" else "which $binaryName"
    val binaryLocation = command.executeToString().trim()

    val file = File(binaryLocation)
    if (!file.exists()) {
        throw ExecutableNotFoundException("Cannot find $binaryName executable")
    } else if (file.canExecute()) {
        return binaryLocation
    } else {
        throw  NotExecutableException("$binaryLocation is not executable")
    }
}

private fun String.isExecutable(): Boolean {
    val file = File(this)
    return file.exists() && file.canExecute()
}

class ExecutableNotFoundException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class NotExecutableException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class ProtocolNotSupportedException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}
