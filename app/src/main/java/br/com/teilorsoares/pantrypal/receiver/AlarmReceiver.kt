package br.com.teilorsoares.pantrypal.receiver

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.di.AppComponent
import br.com.teilorsoares.pantrypal.domain.model.Food
import br.com.teilorsoares.pantrypal.presentation.screens.main.MainActivity
import br.com.teilorsoares.pantrypal.presentation.util.RemindersManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AlarmReceiver : BroadcastReceiver() {

    /**
     * sends notification when receives alarm
     * and then reschedule the reminder again
     * */
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendReminderNotification(
            applicationContext = context,
            channelId = "food_channel_id"
        )
        // Remove this line if you don't want to reschedule the reminder
        RemindersManager.startReminder(context.applicationContext)
    }
}

fun NotificationManager.sendReminderNotification(
    applicationContext: Context,
    channelId: String,
) {
    val foods = runBlocking {
        AppComponent().getFoodSortedByExpireUseCase(false).first().filter { it.status == Food.Status.EXPIRED }
    }

    val contentIntent = Intent(applicationContext, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        1,
        contentIntent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val builder = NotificationCompat.Builder(applicationContext, channelId)


    if(foods.isNotEmpty()) {
        builder
            .setContentTitle("Alimentos vencendo hoje!")
            .setContentText(foods.joinToString { it.name })
    } else {
        builder
            .setContentTitle("Nenhum alimento vencendo hoje!")
            .setContentText("Você não tem nenhum alimento vencendo hoje. Parabéns!")
    }

    builder
        .setSmallIcon(R.drawable.ic_meal)
        .setColor(ContextCompat.getColor(applicationContext, R.color.brown_500))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    notify(NOTIFICATION_ID, builder.build())
}

const val NOTIFICATION_ID = 1