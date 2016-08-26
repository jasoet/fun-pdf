package org.kotlins.funpdf

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.util.UUID

/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo.
 */

class Pdf(configuration: Config.() -> Unit) {
    private val config: Config = Config()
    private lateinit var executable: String

    init {
        configuration(config)
        executable = findExecutable()
        executable.validateExecutable()
    }

    constructor(executablePath: String, configuration: Config.() -> Unit) : this(configuration) {
        executable = executablePath
        executable.validateExecutable()
    }

    fun run(inputStream: InputStream, outputStream: OutputStream) {
        execute(inputStream, outputStream)
    }

    fun run(inputStream: InputStream, outputFile: File) {
        execute(inputStream, outputFile)
    }

    fun run(text: String, outputStream: OutputStream) {
        execute(text, outputStream)
    }

    fun run(text: String, outputFile: File) {
        execute(text, outputFile)
    }

    fun run(file: File, outputStream: OutputStream) {
        execute(file, outputStream)
    }

    fun run(file: File, outputFile: File) {
        execute(file, outputFile)
    }

    fun run(url: URL, outputStream: OutputStream) {
        execute(url, outputStream)
    }

    fun run(url: URL, outputFile: File) {
        execute(url, outputFile)
    }

    private fun execute(input: Any, output: Any) {
        val inputCommand = when (input) {
            is InputStream, is String -> "-"
            is File -> input.absolutePath
            is URL -> {
                when (input.protocol) {
                    "https", "http", "file" -> input.toString()
                    else -> throw ProtocolNotSupportedException("Protocol ${input.protocol} not Supported")
                }
            }
            else -> throw TypeNotSupportedException("Input Type not Supported")
        }

        val outputCommand = when (output) {
            is File -> output.absolutePath
            is OutputStream -> "-"
            else -> throw TypeNotSupportedException("Output Type not Supported")
        }

        val command: List<String> = listOf(executable, "-q") + config.parameters() + inputCommand + outputCommand

        val tmpDir: String = System.getProperty("java.io.tmpdir")

        val processBuilder = ProcessBuilder(command)

        when (input) {
            is InputStream -> {
                val inputFile = File(tmpDir, UUID.randomUUID().toString())
                FileUtils.copyInputStreamToFile(input, inputFile)
                processBuilder.redirectInput(inputFile)
            }
            is String -> {
                val inputFile = File(tmpDir, UUID.randomUUID().toString())
                FileUtils.writeStringToFile(inputFile, input, "UTF-8")
                processBuilder.redirectInput(inputFile)
            }
        }

        when (output) {
            is OutputStream -> {
                val inputFile = File(tmpDir, UUID.randomUUID().toString())
                processBuilder.redirectOutput(inputFile)
                FileInputStream(inputFile).use {
                    IOUtils.copy(it, output)
                }
            }
        }

        val process = processBuilder.start()
        process.waitFor()
    }
}


fun String.validateExecutable(): Boolean {
    val file = File(this)
    if (file.canExecute()) {
        return true
    } else {
        throw throw NoExecutableException("$this is not executable")
    }
}


fun findExecutable(): String {
    val os = System.getProperty("os.name")?.toLowerCase() ?: ""
    val cmd = if (os.contains("windows")) "where wkhtmltopdf" else "which wkhtmltopdf"

    return try {
        cmd.execute()
    } catch (r: Exception) {
        throw NoExecutableException("Cannot find wkhtmltopdf executable", r)
    }
}


fun String.execute(): String {
    val process = ProcessBuilder(this).start()
    process.waitFor()
    return process.inputStream.use {
        IOUtils.toString(it, "UTF-8")
    }
}

class NoExecutableException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class TypeNotSupportedException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class ProtocolNotSupportedException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}
