package br.com.teilorsoares.pantrypal

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import logcat.AndroidLogcatLogger
import logcat.LogPriority

class PantryPalApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()

        //Initialize logcat
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.DEBUG)

        //Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "food_channel_id",
                "Food Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir)
                    .maxSizeBytes(1024 * 1024 * 100) // 100MB
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }
}