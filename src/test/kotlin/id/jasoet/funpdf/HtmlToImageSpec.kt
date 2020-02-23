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

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldNotBeNull
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import java.nio.file.Paths
import java.util.UUID

object HtmlToImageSpec : Spek({

    val tmpDir: String = System.getProperty("java.io.tmpdir")

    given("HtmlToPdf Converter") {
        val image by lazy {
            HtmlToImage()
        }

        on("Handle string input") {

            val page = "<html><body><h1>Hello World</h1></body></html>"

            it("Should able to convert to pdf and save it in file") {
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                image.convert(page, outputFile)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and convert it to output stream") {
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                image.convert(page, outputStream)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and return InputStream") {
                val inputStreamResult = image.convert(page)
                inputStreamResult.shouldNotBeNull()

                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                IOUtils.copy(inputStreamResult, outputStream)

                outputFile.exists() shouldBe true
            }

        }

        on("Handle Url input") {
            val url = URL("https://www.google.com")

            it("Should able to convert to pdf and save it in file") {
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                image.convert(input = url, output = outputFile)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and convert it to output stream") {
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                image.convert(input = url, output = outputStream)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and return InputStream") {
                val inputStreamResult = image.convert(url)
                inputStreamResult.shouldNotBeNull()

                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                IOUtils.copy(inputStreamResult, outputStream)

                outputFile.exists() shouldBe true
            }
        }

        on("Handle File input") {
            val page = "<html><body><h1>Hello World</h1></body></html>"
            val inputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.html").toFile()
            FileUtils.writeStringToFile(inputFile, page, Charsets.UTF_32)


            it("Should able to convert to pdf and save it in file") {
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                image.convert(input = inputFile, output = outputFile)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and convert it to output stream") {
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                image.convert(input = inputFile, output = outputStream)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and return InputStream") {
                val inputStreamResult = image.convert(inputFile)
                inputStreamResult.shouldNotBeNull()

                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                IOUtils.copy(inputStreamResult, outputStream)

                outputFile.exists() shouldBe true
            }
        }

        on("Handle InputStream input") {
            val page = "<html><body><h1>Hello World</h1></body></html>"
            val sourceFile = Paths.get(tmpDir, "${UUID.randomUUID()}.html").toFile()
            FileUtils.writeStringToFile(sourceFile, page, Charsets.UTF_32)


            it("Should able to convert to pdf and save it in file") {
                val inputStream = FileInputStream(sourceFile)
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                image.convert(input = inputStream, output = outputFile)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and convert it to output stream") {
                val inputStream = FileInputStream(sourceFile)
                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                image.convert(input = inputStream, output = outputStream)

                outputFile.exists() shouldBe true
            }

            it("Should able to convert to pdf and return InputStream") {
                val inputStream = FileInputStream(sourceFile)
                val inputStreamResult = image.convert(inputStream)
                inputStreamResult.shouldNotBeNull()

                val outputFile = Paths.get(tmpDir, "${UUID.randomUUID()}.png").toFile()
                val outputStream = FileOutputStream(outputFile)
                IOUtils.copy(inputStreamResult, outputStream)

                outputFile.exists() shouldBe true
            }
        }
    }

})
