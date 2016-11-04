package org.kotlins.funpdf

import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.util.UUID

/**
 * Pdf class
 *
 * @author Deny Prasetyo.
 */

class Pdf(executablePath: String = "", configuration: Config.() -> Unit) {
    private val log = LoggerFactory.getLogger(Pdf::class.java)
    private val config: Config = Config()
    private var executable: String

    init {
        configuration(config)
        executable = if (executablePath.isBlank()) {
            try {
                findExecutable()
            } catch (e: ExecutableNotFoundException) {
                log.debug(e.message, e)
                log.info("Executable not found, try default `wkhtmltopdf` command.")
                "wkhtmltopdf"
            } catch (e: NotExecutableException) {
                log.debug(e.message, e)
                log.info("${e.message}, try default `wkhtmltopdf` command.")
                "wkhtmltopdf"
            }
        } else {
            val executableFile = File(executablePath)
            if (!executableFile.exists()) {
                log.info("$executableFile not exists, Ignoring.")
            }

            if (executableFile.canExecute()) {
                log.info("$executableFile not executable, Ignoring.")
            }
            executablePath
        }

    }


    fun run(inputStream: InputStream, outputStream: OutputStream): Int {
        return execute(inputStream, outputStream)
    }

    fun run(inputStream: InputStream, outputFile: File): Int {
        return execute(inputStream, outputFile)
    }

    fun run(text: String, outputStream: OutputStream): Int {
        return execute(text, outputStream)
    }

    fun run(text: String, outputFile: File): Int {
        return execute(text, outputFile)
    }

    fun run(file: File, outputStream: OutputStream): Int {
        return execute(file, outputStream)
    }

    fun run(file: File, outputFile: File): Int {
        return execute(file, outputFile)
    }

    fun run(url: URL, outputStream: OutputStream): Int {
        return execute(url, outputStream)
    }

    fun run(url: URL, outputFile: File): Int {
        return execute(url, outputFile)
    }

    private fun execute(input: Any, output: Any): Int {
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

        log.debug("Command to Execute ${command.joinToString(" ")}")

        val tmpDir: String = System.getProperty("java.io.tmpdir")

        val processBuilder = ProcessBuilder(command)

        when (input) {
            is InputStream -> {
                log.debug("Accept InputStream Input")
                val inputFile = File(tmpDir, UUID.randomUUID().toString())
                FileUtils.copyInputStreamToFile(input, inputFile)
                processBuilder.redirectInput(inputFile)
            }
            is String -> {
                log.debug("Accept String Input")
                val inputFile = File(tmpDir, UUID.randomUUID().toString())
                FileUtils.writeStringToFile(inputFile, input, "UTF-8")
                processBuilder.redirectInput(inputFile)
            }
        }

        return when (output) {
            is OutputStream -> {
                val inputFile = File(tmpDir, UUID.randomUUID().toString())
                processBuilder.redirectOutput(inputFile)
                val exitCode = processBuilder.start().waitFor()

                FileInputStream(inputFile).use { fis ->
                    output.use {
                        IOUtils.copy(fis, output)
                    }
                }

                exitCode
            }
            else -> {
                processBuilder.start().waitFor()
            }
        }
    }

    private fun findExecutable(): String {
        val os = System.getProperty("os.name")?.toLowerCase() ?: ""
        val cmd = if (os.contains("windows")) "where wkhtmltopdf" else "which wkhtmltopdf"

        val command = cmd.simpleExecute()
        val file = File(command)
        if (!file.exists()) {
            throw ExecutableNotFoundException("Cannot find wkhtmltopdf executable")
        } else if (file.canExecute()) {
            return command
        } else {
            throw  NotExecutableException("$command is not executable")
        }
    }

    private fun String.simpleExecute(): String {
        val process = ProcessBuilder(this.split(" ")).start()
        process.waitFor()
        return try {
            process.inputStream.use {
                IOUtils.toString(it, "UTF-8")
            }
        } catch (e: Exception) {
            log.debug(e.message, e)
            ""
        }
    }
}

class ExecutableNotFoundException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class NotExecutableException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class TypeNotSupportedException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}

class ProtocolNotSupportedException(message: String, cause: Throwable?) : Exception(message, cause) {
    constructor(message: String) : this(message, null)
}
