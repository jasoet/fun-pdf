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


class Config {
    val allow = Parameter<List<String>>("allow")
    val defaultHeader = Parameter<Boolean>("default-header")
    val disableExternalLinks = Parameter<Boolean>("disable-external-links")
    val disableInternalLinks = Parameter<Boolean>("disable-internal-links")
    val disableJavascript = Parameter<Boolean>("disable-javascript")
    val noPdfCompression = Parameter<Boolean>("no-pdf-compression")
    val disableSmartShrinking = Parameter<Boolean>("disable-smart-shrinking")
    val javascriptDelay = Parameter<Int>("javascript-delay")
    val convertForms = Parameter<Boolean>("forms")
    val encoding = Parameter("encoding", "UTF-8")
    val grayScale = Parameter<Boolean>("grayscale")
    val lowQuality = Parameter<Boolean>("lowquality")
    val marginBottom = Parameter<String>("margin-bottom")
    val marginLeft = Parameter<String>("margin-left")
    val marginRight = Parameter<String>("margin-right")
    val marginTop = Parameter<String>("margin-top")
    val minimumFontSize = Parameter<Int>("minimum-font-size")
    val background = Parameter<Toggle>("background")
    val orientation = Parameter<PageOrientation>("orientation")
    val pageHeight = Parameter<String>("page-height")
    val pageOffset = Parameter<String>("page-offset")
    val pageSize = Parameter<String>("page-size")
    val pageWidth = Parameter<String>("page-width")
    val title = Parameter<String>("title")
    val tableOfContent = Parameter<Boolean>("toc")
    val zoom = Parameter<Float>("zoom")
    val footerCenter = Parameter<String>("footer-center")
    val footerFontName = Parameter<String>("footer-font-name")
    val footerFontSize = Parameter<String>("footer-font-size")
    val footerHtml = Parameter<String>("footer-html")
    val footerLeft = Parameter<String>("footer-left")
    val footerLine = Parameter<Boolean>("footer-line")
    val footerRight = Parameter<String>("footer-right")
    val footerSpacing = Parameter<Float>("footer-spacing")
    val headerCenter = Parameter<String>("header-center")
    val headerFontName = Parameter<String>("header-font-name")
    val headerFontSize = Parameter<String>("header-font-size")
    val headerHtml = Parameter<String>("header-html")
    val headerLeft = Parameter<String>("header-left")
    val headerLine = Parameter<Toggle>("header-line")
    val headerRight = Parameter<String>("header-right")
    val headerSpacing = Parameter<Float>("header-spacing")
    val tableOfContentDepth = Parameter<Int>("toc-depth")
    val tableOfContentDisableBackLinks = Parameter<Boolean>("toc-disable-back-links")
    val tableOfContentDisableLinks = Parameter<Boolean>("toc-disable-links")
    val tableOfContentFontName = Parameter<String>("toc-font-name")
    val tableOfContentHeaderFontName = Parameter<String>("toc-header-font-name")
    val tableOfContentHeaderFontSize = Parameter<Int>("toc-header-font-size")
    val tableOfContentHeaderText = Parameter<String>("toc-header-text")
    val tableOfContentLevel1FontSize = Parameter<Int>("toc-l1-font-size")
    val tableOfContentLevel1Indentation = Parameter<Int>("toc-l1-indentation")
    val tableOfContentLevel2FontSize = Parameter<Int>("toc-l2-font-size")
    val tableOfContentLevel2Indentation = Parameter<Int>("toc-l2-indentation")
    val tableOfContentLevel3FontSize = Parameter<Int>("toc-l3-font-size")
    val tableOfContentLevel3Indentation = Parameter<Int>("toc-l3-indentation")
    val tableOfContentLevel4FontSize = Parameter<Int>("toc-l4-font-size")
    val tableOfContentLevel4Indentation = Parameter<Int>("toc-l4-indentation")
    val tableOfContentLevel5FontSize = Parameter<Int>("toc-l5-font-size")
    val tableOfContentLevel5Indentation = Parameter<Int>("toc-l5-indentation")
    val tableOfContentLevel6FontSize = Parameter<Int>("toc-l6-font-size")
    val tableOfContentLevel6Indentation = Parameter<Int>("toc-l6-indentation")
    val tableOfContentLevel7FontSize = Parameter<Int>("toc-l7-font-size")
    val tableOfContentLevel7Indentation = Parameter<Int>("toc-l7-indentation")
    val tableOfContentNoDots = Parameter<Boolean>("toc-no-dots")
    val outline = Parameter<Toggle>("outline")
    val outlineDepth = Parameter<Int>("outline-depth")
    val printMediaType = Parameter<Toggle>("print-media-type")
    val userStyleSheet = Parameter<String>("user-style-sheet")
    val username = Parameter<String>("username")
    val password = Parameter<String>("password")
    val viewportSize = Parameter<String>("viewport-size")


    private val listParameters =
        listOf(allow,
            defaultHeader,
            disableExternalLinks,
            disableInternalLinks,
            disableJavascript,
            noPdfCompression,
            disableSmartShrinking,
            javascriptDelay,
            convertForms,
            encoding,
            grayScale,
            lowQuality,
            marginBottom,
            marginLeft,
            marginRight,
            marginTop,
            minimumFontSize,
            background,
            orientation,
            pageHeight,
            pageOffset,
            pageSize,
            pageWidth,
            title,
            tableOfContent,
            zoom,
            footerCenter,
            footerFontName,
            footerFontSize,
            footerHtml,
            footerLeft,
            footerLine,
            footerRight,
            footerSpacing,
            headerCenter,
            headerFontName,
            headerFontSize,
            headerHtml,
            headerLeft,
            headerLine,
            headerRight,
            headerSpacing,
            tableOfContentDepth,
            tableOfContentDisableBackLinks,
            tableOfContentDisableLinks,
            tableOfContentFontName,
            tableOfContentHeaderFontName,
            tableOfContentHeaderFontSize,
            tableOfContentHeaderText,
            tableOfContentLevel1FontSize,
            tableOfContentLevel1Indentation,
            tableOfContentLevel2FontSize,
            tableOfContentLevel2Indentation,
            tableOfContentLevel3FontSize,
            tableOfContentLevel3Indentation,
            tableOfContentLevel4FontSize,
            tableOfContentLevel4Indentation,
            tableOfContentLevel5FontSize,
            tableOfContentLevel5Indentation,
            tableOfContentLevel6FontSize,
            tableOfContentLevel6Indentation,
            tableOfContentLevel7FontSize,
            tableOfContentLevel7Indentation,
            tableOfContentNoDots,
            outline,
            outlineDepth,
            printMediaType,
            userStyleSheet,
            username,
            password,
            viewportSize)

    fun parameters(): List<String> {
        return this.listParameters.map { it.compile() }.flatten()
    }
}

