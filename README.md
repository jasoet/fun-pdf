# Html to Pdf Converter 

[![Build Status](https://travis-ci.org/jasoet/fun-pdf.svg?branch=master)](https://travis-ci.org/jasoet/fun-pdf)
[![codecov](https://codecov.io/gh/jasoet/fun-pdf/branch/master/graph/badge.svg)](https://codecov.io/gh/jasoet/fun-pdf)
[![Download](https://api.bintray.com/packages/jasoet/fun/fun-pdf/images/download.svg) ](https://bintray.com/jasoet/fun/fun-pdf/_latestVersion)

Library to convert Html to Pdf using `wkhtmltopdf` library. This library only tested on Linux and macOS

## Features
- Define custom executable location, if not found will find executable by running `which wkthtmltopdf`
- Accept `File`, `String`, `URL` or `InputStream` as standard input.
- Redirect standard output to `File` or `OutputStream` (including `System.out`).
- Accept `Map` as environment variable.
- Return `BufferedInputStream` if output is not redirected, null if otherwise.
- `InputStream` from a command will be easily to be piped (as input) to other command. 

## Install wkhtmltopdf
`wkhtmltopdf` library can be downloaded from [here](https://wkhtmltopdf.org/downloads.html)

### Linux
```bash
sudo apt-get update -q
sudo apt-get install -yq wget xfonts-base xfonts-75dpi curl
curl -sLO https://downloads.wkhtmltopdf.org/0.12/0.12.5/wkhtmltox_0.12.5-1.xenial_amd64.deb
sudo dpkg -i wkhtmltox_0.12.5-1.xenial_amd64.deb
```

### macOS
```bash
brew cask install wkhtmltopdf
```

## Gradle

### Add maven repository url
```groovy
repositories {
    maven {
       url "https://dl.bintray.com/jasoet/fun"
    }
}
```

### Add dependency 
```groovy
compile 'id.jasoet:fun-pdf:<version>'
```

## Usage

### Configurable and Cacheable  
```kotlin
val pdf by lazy {
        HtmlToPdf(executable = "/usr/bin/wkhtmltopdf") {
            orientation(PageOrientation.LANDSCAPE)
            pageSize("Letter")
            marginTop("1in")
            marginBottom("1in")
            marginLeft("1in")
            marginRight("1in")
        }
}
```

### Html String as input
```kotlin
val htmlString = "<html><body><h1>Hello World</h1></body></html>"

// convert and save if to file
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = pdf.convert(input = htmlString,output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
val inputStream = pdf.convert(input = htmlString) // InputStream is open and ready to use
```

### File as input
```kotlin
val file = File("/home/jasoet/source.html")

// convert and save if to file
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = pdf.convert(input = file,output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
val inputStream = pdf.convert(input = file) // InputStream is open and ready to use
```

### Url as input
```kotlin
val url = URL("https://www.google.com")

// convert and save if to file
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = pdf.convert(input = url,output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
val inputStream = pdf.convert(input = url) // InputStream is open and ready to use
```

### InputStream as Input
```kotlin
// get input stream from other executable
val inputStream = listOf("ssh", "jasoet@10.10.2.3","\"cat /home/jasoet/remotefile.html\"").execute()

// Save it to file
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = pdf.convert(input = url,output = outputFile) // will always return null if output is redirected

```

## Using extensions

### String extension
```kotlin

val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = "<html><body><h1>Hello World</h1></body></html>".convertToPdf(output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
val inputStream = "<html><body><h1>Hello World</h1></body></html>".convertToPdf {
                    orientation(PageOrientation.LANDSCAPE)
                    pageSize("Letter")
                    marginTop("1in")
                    marginBottom("1in")
                    marginLeft("1in")
                    marginRight("1in")
               }
// InputStream is open and ready to use
```

### File extension
```kotlin
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = File("/home/jasoet/source.html").convertToPdf(output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
val inputStream = File("/home/jasoet/source.html").convertToPdf {
                    orientation(PageOrientation.LANDSCAPE)
                    pageSize("Letter")
                    marginTop("1in")
                    marginBottom("1in")
                    marginLeft("1in")
                    marginRight("1in")
               }
// InputStream is open and ready to use
```

### Url extension
```kotlin
// convert and save if to file
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
val inputStream = URL("https://www.google.com").convertToPdf(output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
val inputStream = URL("https://www.google.com").convertToPdf {
                    orientation(PageOrientation.LANDSCAPE)
                    pageSize("Letter")
                    marginTop("1in")
                    marginBottom("1in")
                    marginLeft("1in")
                    marginRight("1in")
               } 
// InputStream is open and ready to use
```

### InputStream as Input
```kotlin
val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()

// get input stream from other executable and save if to file
val inputStream = listOf("ssh", "jasoet@10.10.2.3","\"cat /home/jasoet/remotefile.html\"").execute()
                  .convertToPdf(output = outputFile) {
                    orientation(PageOrientation.LANDSCAPE)
                    pageSize("Letter")
                    marginTop("1in")
                    marginBottom("1in")
                    marginLeft("1in")
                    marginRight("1in")
               } 

```

See [unit test files](https://github.com/jasoet/fun-pdf/blob/master/src/test/kotlin/id/jasoet/funpdf/).
