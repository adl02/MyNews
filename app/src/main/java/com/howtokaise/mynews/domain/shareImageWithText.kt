import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream

suspend fun shareImageWithText(
    context: Context,
    imageUrl: String,
    articleLink: String
) {
    val appMessage = """
        🔥 Stay informed in under 50 words.
Read this article on MyNews now:
📱 App - http://bit.ly/4ketlmn

📖 Read Now - $articleLink"""

    try {
        // Load image as Bitmap
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = (loader.execute(request) as SuccessResult).drawable
        val bitmap = (result as android.graphics.drawable.BitmapDrawable).bitmap

        // Save bitmap to cache file
        val cachePath = File(context.cacheDir, "shared_images")
        cachePath.mkdirs()
        val file = File(cachePath, "shared_image.png")
        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()

        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        // Share intent
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_TEXT, appMessage)
            type = "image/*"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share via"))

    } catch (e: Exception) {
        e.printStackTrace()
    }
}
