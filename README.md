# Html to Pdf Converter 

[![Build Status](https://travis-ci.org/jasoet/fun-pdf.svg?branch=master)](https://travis-ci.org/jasoet/fun-pdf)
[![codecov](https://codecov.io/gh/jasoet/fun-pdf/branch/master/graph/badge.svg)](https://codecov.io/gh/jasoet/fun-pdf)
[![Download](https://api.bintray.com/packages/jasoet/fun/fun-pdf/images/download.svg) ](https://bintray.com/jasoet/fun/fun-pdf/_latestVersion)

Library to convert Html to Pdf using `wkhtmltopdf` library. This library only tested on Linux and macOS

## Features
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
See [unit test file](https://github.com/jasoet/fun-pdf/blob/master/src/test/kotlin/id/jasoet/funpdf/HtmlToPdfSpec.kt).
