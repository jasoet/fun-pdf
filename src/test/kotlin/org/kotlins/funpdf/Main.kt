package org.kotlins.funpdf

import org.junit.Test
import org.kotlins.funpdf.PageOrientation.LANDSCAPE
import java.io.File
import java.io.FileOutputStream
import java.net.URL

/**
 * [Documentation Here]
 *
 * @author Deny Prasetyo.
 */

class MainTest {

    @Test
    fun testPdf() {
        val pdf = Pdf {
            orientation(LANDSCAPE)
            pageSize("Letter")
            marginTop("1in")
            marginBottom("1in")
            marginLeft("1in")
            marginRight("1in")
        }

        val page = "<html><body><h1>Hello World</h1></body></html>"

        // Save the PDF generated from the above HTML into a Byte Array

        val outputStream = FileOutputStream(File("hore.pdf"))
        pdf.run(page, outputStream)

        pdf.run(URL("http://www.google.com"), File("/Users/jasoet/google.pdf"))
    }
}

