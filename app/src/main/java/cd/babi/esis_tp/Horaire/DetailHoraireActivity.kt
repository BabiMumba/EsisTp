package cd.babi.esis_tp.Horaire

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import cd.babi.esis_tp.R
import com.github.chrisbanes.photoview.PhotoView
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class DetailHoraireActivity : AppCompatActivity() {
    private lateinit var pdfImageView: PhotoView
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_horaire)
        pdfImageView = findViewById(R.id.pdfImageView)
        progressBar = findViewById(R.id.progressBar)
        val promo = intent.getStringExtra("promo")
        val pdfUrl = "https://www.esisalama.com/assets/upload/horaire/pdf/HORAIRE%20$promo.pdf" // Remplacez par votre lien PDF réel
        val downloadPdfTask = DownloadPdfTask()
        downloadPdfTask.execute(pdfUrl)
    }
    private inner class DownloadPdfTask : AsyncTask<String, Int, File>() {
        override fun onPreExecute() {
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg urls: String): File? {
            val pdfUrl = urls[0]

            try {
                val url = URL(pdfUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val file = File(cacheDir, "temp.pdf")
                    val fileOutputStream = FileOutputStream(file)

                    val totalSize = connection.contentLength
                    var downloadedSize = 0

                    val inputStream = BufferedInputStream(connection.inputStream)
                    val buffer = ByteArray(4096)
                    var bytesRead: Int

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead)
                        downloadedSize += bytesRead
                        publishProgress((downloadedSize * 100 / totalSize))
                    }

                    fileOutputStream.close()
                    inputStream.close()

                    return file
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onProgressUpdate(vararg progress: Int?) {
            progressBar.progress = progress[0] ?: 0
        }

        override fun onPostExecute(result: File?) {
            progressBar.visibility = View.GONE

            if (result != null) {
                displayPdf(result)
            }
        }
    }

    private fun displayPdf(file: File) {
        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(fileDescriptor)

        val pageCount = pdfRenderer.pageCount
        val bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)

        val rendererPage = pdfRenderer.openPage(0)
        rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        rendererPage.close()

        pdfImageView.setImageBitmap(bitmap)

        pdfRenderer.close()
        fileDescriptor.close()
    }

}