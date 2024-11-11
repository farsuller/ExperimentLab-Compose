package com.compose.experiment.features.pdf_generator

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.compose.experiment.R
import java.io.OutputStream

@Composable
fun PdfGenerator() {
    val ctx = LocalContext.current
    val activity = LocalContext.current as? Activity

    if (checkPermissions(ctx)) {
        Toast.makeText(ctx, "Permissions Granted..", Toast.LENGTH_SHORT).show()
    } else {
        requestPermission(activity!!)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PDF Generator",
            fontSize = 20.sp,
            color = Color.Green,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(60.dp))
        Button(onClick = { generatePDF(ctx) }) {
            Text("Generate PDF")
        }
    }
}

fun generatePDF(context: Context) {
    // Define the PDF document dimensions
    val pageHeight = 1120
    val pageWidth = 792

    // Initialize the PdfDocument and create a PageInfo object
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    // Paint objects for styling
    val paint = Paint()
    val titlePaint = paint.apply {
        textSize = 24f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        textAlign = Paint.Align.CENTER
    }
    val cellTextPaint = paint.apply {
        textSize = 16f
        color = Color.Black.toArgb()
    }
    val linePaint = paint.apply {
        color = Color.Black.toArgb()
        strokeWidth = 2f
    }

    // Define table parameters
    val startX = 50f // Margin from the left
    val startY = 180f // Margin from the top
    val cellWidth = (pageWidth - 2 * startX) / 4 // 4 columns fit the page width with margins
    val cellHeight = 60f // Adjust as needed for row height

    // Load the logo bitmap from resources (you can replace with a file path if stored in storage)
    val logo = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.fiestahan_logo
    ) // Replace with actual resource ID

    // Set logo dimensions and position
    val logoWidth = 100 // Adjust width of the logo as needed
    val logoHeight = 100 // Adjust height of the logo as needed
    val logoX = 50f // Left margin for logo
    val logoY = 50f // Top margin for logo

    // Draw the logo on the canvas
    canvas.drawBitmap(logo, null, RectF(logoX, logoY, logoX + logoWidth, logoY + logoHeight), null)

    // Draw header title next to the logo
    val headerTitle = "Fiestahan"
    cellTextPaint.textSize = 24f
    cellTextPaint.isFakeBoldText = true
    canvas.drawText(
        headerTitle,
        logoX + logoWidth + 80f,
        logoY + logoHeight / 2 + cellTextPaint.textSize / 2,
        cellTextPaint
    )


    // Draw column headers
    val headers = listOf("Food Name", "Price", "Sold", "Stock")
    cellTextPaint.textSize = 16f
    cellTextPaint.isFakeBoldText = true

    for ((i, header) in headers.withIndex()) {
        val x = startX + i * cellWidth + (cellWidth / 2)
        startY + (cellHeight / 2) - ((cellTextPaint.descent() + cellTextPaint.ascent()) / 2)
        canvas.drawText(header, x, startY + cellHeight / 2, cellTextPaint)
    }

    // Sample data rows
    val rows = listOf(
        listOf("Lechon Manok", "Php219", "30", "100"),
        listOf("Sisig", "Php200", "45", "80"),
        listOf("Liempo", "Php199", "50", "90"),
        listOf("Lechon Manok", "Php219", "30", "100"),
        listOf("Sisig", "Php200", "45", "80"),
        listOf("Liempo", "Php199", "50", "90"),
        listOf("Lechon Manok", "Php219", "30", "100"),
        listOf("Sisig", "Php200", "45", "80"),
        listOf("Liempo", "Php199", "50", "90"),
        listOf("Lechon Manok", "Php219", "30", "100"),
        listOf("Sisig", "Php200", "45", "80"),
        listOf("Liempo", "Php199", "50", "90")
    )
    cellTextPaint.isFakeBoldText = false

    // Draw rows with data
    for ((rowIndex, row) in rows.withIndex()) {
        for ((colIndex, text) in row.withIndex()) {
            val x = startX + colIndex * cellWidth + (cellWidth / 2)
            val y = startY + (rowIndex + 1) * cellHeight + (cellHeight / 2) -
                    ((cellTextPaint.descent() + cellTextPaint.ascent()) / 2)
            canvas.drawText(text, x, y, cellTextPaint)
        }
    }

    // Draw borders
    val numRows = rows.size + 1 // includes header row

    for (rowIndex in 0..numRows) {
        val y = startY + rowIndex * cellHeight
        canvas.drawLine(startX, y, startX + headers.size * cellWidth, y, linePaint)
    }

    for (colIndex in 0..headers.size) {
        val x = startX + colIndex * cellWidth
        canvas.drawLine(x, startY, x, startY + numRows * cellHeight, linePaint)
    }

    // Finish page
    pdfDocument.finishPage(page)

    // Write PDF to MediaStore
    val resolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "Fiestahan_Menu.pdf")
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
    }

    val uri = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
    if (uri != null) {
        resolver.openOutputStream(uri).use { outputStream ->
            pdfDocument.writeTo(outputStream as OutputStream)
            Toast.makeText(context, "PDF file saved to Documents", Toast.LENGTH_SHORT).show()
        }
    }
    pdfDocument.close()
}

fun checkPermissions(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissions = listOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
        )
        permissions.all { ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }
    } else {
        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}

fun requestPermission(activity: Activity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            ),
            101
        )
    } else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            101
        )
    }
}