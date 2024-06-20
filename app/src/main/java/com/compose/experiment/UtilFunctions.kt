package com.compose.experiment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import java.text.DecimalFormat
import java.text.NumberFormat

val creditCardLambda = { it: AnnotatedString ->
    // Making XXXX-XXXX-XXXX-XXXX string.
    val trimmed = if (it.text.length >= 16) it.text.substring(0..15) else it.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += "-"
    }

    /**
     * The offset translator should ignore the hyphen characters, so conversion from
     *  original offset to transformed text works like
     *  - The 4th char of the original text is 5th char in the transformed text.
     *  - The 13th char of the original text is 15th char in the transformed text.
     *  Similarly, the reverse conversion works like
     *  - The 5th char of the transformed text is 4th char in the original text.
     *  - The 12th char of the transformed text is 10th char in the original text.
     */
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 16) return offset + 3
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 1
            if (offset <= 14) return offset - 2
            if (offset <= 19) return offset - 3
            return 16
        }
    }

    TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

fun creditCard(text: AnnotatedString): TransformedText {
    // Making XXXX-XXXX-XXXX-XXXX string.
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""
    for (i in trimmed.indices) {
        out += trimmed[i]
        if (i % 4 == 3 && i != 15) out += "-"
    }

    /**
     * The offset translator should ignore the hyphen characters, so conversion from
     *  original offset to transformed text works like
     *  - The 4th char of the original text is 5th char in the transformed text.
     *  - The 13th char of the original text is 15th char in the transformed text.
     *  Similarly, the reverse conversion works like
     *  - The 5th char of the transformed text is 4th char in the original text.
     *  - The 12th char of the transformed text is 10th char in the original text.
     */
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 1
            if (offset <= 11) return offset + 2
            if (offset <= 16) return offset + 3
            return 19
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 4) return offset
            if (offset <= 9) return offset - 1
            if (offset <= 14) return offset - 2
            if (offset <= 19) return offset - 3
            return 16
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

class NumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 11) text.text.substring(0, 11) else text.text
        val out = StringBuilder()

        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1 || i == 4 || i == 7) out.append(' ')
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 1 -> offset
                    offset <= 4 -> offset + 1
                    offset <= 7 -> offset + 2
                    offset <= 11 -> offset + 3
                    else -> 14
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 6 -> offset - 1
                    offset <= 10 -> offset - 2
                    offset <= 14 -> offset - 3
                    else -> 11
                }
            }
        }

        return TransformedText(AnnotatedString(out.toString()), numberOffsetTranslator)
    }
}


fun oneDot(it: AnnotatedString): TransformedText {
    return TransformedText(
        AnnotatedString.Builder().run {
            it.forEach {
                append(it)
                append(".")
            }
            toAnnotatedString()
        },
        object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset * 2
            }

            override fun transformedToOriginal(offset: Int): Int {
                return offset / 2
            }
        }
    )
}

fun Static(it: AnnotatedString): TransformedText {
    return TransformedText(
        AnnotatedString("Jetpack Compose is great".repeat(100).take(it.length)),
        OffsetMapping.Identity
    )
}

object DateVisualTransformation : VisualTransformation {
    fun transform(original: String): String {
        val trimmed: String = original.take(8)
        if (trimmed.length < 4) return trimmed
        if (trimmed.length == 4) return "$trimmed-"
        val (year, monthAndOrDate) = trimmed.chunked(4)
        if (trimmed.length == 5) return "$year-$monthAndOrDate"
        if (trimmed.length == 6) return "$year-$monthAndOrDate-"
        val (month, date) = monthAndOrDate.chunked(2)
        return "$year-$month-$date"
    }

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(AnnotatedString(transform(text.text)),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    if (offset <= 3) return offset
                    if (offset <= 5) return offset + 1
                    if (offset <= 7) return offset + 2
                    return 10
                }

                override fun transformedToOriginal(offset: Int): Int {
                    if (offset <= 4) return offset
                    if (offset <= 7) return offset - 1
                    if (offset <= 10) return offset - 2
                    return 8
                }
            })
    }
}

class CreditCardClass : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var formatted = ""
        for (i in trimmed.indices) {
            formatted += trimmed[i]
            if (i % 4 == 3 && i != 15) formatted += "-"
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }

        }

        return TransformedText(
            AnnotatedString(formatted),
            mapping
        )
    }
}

object CreditCardObject : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var formatted = ""
        for (i in trimmed.indices) {
            formatted += trimmed[i]
            if (i % 4 == 3 && i != 15) formatted += "-"
        }
        val mapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }

        }

        return TransformedText(
            AnnotatedString(formatted),
            mapping
        )
    }
}

class DateVisualTransformationFormatted : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Make the string DD-MM-YYYY
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        var output = ""
        for (i in trimmed.indices) {
            output += trimmed[i]
            if (i < 4 && i % 2 == 1) output += "-"
        }
        val dateTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                // [offset [0 - 1] remain the same]
                if (offset <= 1) return offset
                // [2 - 3] transformed to [3 - 4] respectively
                if (offset <= 3) return offset + 1
                // [4 - 7] transformed to [6 - 9] respectively
                if (offset <= 7) return offset + 2
                return 10
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset - 1
                if (offset <= 9) return offset - 2
                return 8
            }

        }

        return TransformedText(
            AnnotatedString(output),
            dateTranslator
        )
    }
}

class ThousandSeparatorTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val symbols = DecimalFormat().decimalFormatSymbols
        val decimalSeparator = symbols.decimalSeparator

        var outputText = ""
        var integerPart = 0L
        var decimalPart = ""

        if (text.text.isNotEmpty()) {
            val number = text.text.toDouble()
            integerPart = number.toLong()
            outputText += NumberFormat.getIntegerInstance().format(integerPart)
            if (text.text.contains(decimalSeparator)) {
                decimalPart = text.text.substring(text.text.indexOf(decimalSeparator))
                if (decimalPart.isNotEmpty()) {
                    outputText += decimalPart
                }
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return outputText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return text.length
            }
        }

        return TransformedText(
            text = AnnotatedString(outputText),
            offsetMapping = numberOffsetTranslator
        )
    }
}

private fun String.withThousands(separator: Char = ','): String {
    val original = this
    return buildString {
        original.indices.forEach { position ->
            val realPosition = original.lastIndex - position
            val character = original[realPosition]
            insert(0, character)
            if (position != 0 && realPosition != 0 && position % 3 == 2) {
                insert(0, separator)
            }
        }
    }
}

fun priceFilter(
    text: String,
    thousandSeparator: (String) -> String = { text -> text.withThousands() },
): TransformedText {
    val out = thousandSeparator(text)
    val offsetMapping = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val rightOffset = text.lastIndex - offset
            val commasToTheRight = rightOffset / 3
            return out.lastIndex - rightOffset - commasToTheRight
        }

        override fun transformedToOriginal(offset: Int): Int {
            val totalCommas = ((text.length - 1) / 3).coerceAtLeast(0)
            val rightOffset = out.length - offset
            val commasToTheRight = rightOffset / 4
            return (offset - (totalCommas - commasToTheRight))
        }
    }
    return TransformedText(AnnotatedString(out), offsetMapping)
}

val NavHostController.canBackStack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED

@Composable
fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit,
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    return this.then(
        Modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
    )
}

fun getAppVersion(context: Context): String {
    return try {
        val packageManager: PackageManager = context.packageManager
        val packageInfo: PackageInfo = packageManager.getPackageInfo(context.packageName, 0)
        val versionCode: Long = PackageInfoCompat.getLongVersionCode(packageInfo)

        val versionName: String = packageInfo.versionName
        "v$versionCode.$versionName"
    } catch (e: PackageManager.NameNotFoundException) {
        "version N/A"
    }
}

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

fun sendEmail(
    toEmail: String,
    subject: String,
    message: String,
    context: Context,
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$toEmail")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, message)
    }

    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // Handle the case where the user doesn't have an email app installed.
        // You can display a message or prompt the user to install an email app.
        // For simplicity, we'll just log a message.
        println("No email app installed on the device.")
    }
}
