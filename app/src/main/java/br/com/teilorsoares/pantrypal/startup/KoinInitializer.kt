package br.com.teilorsoares.pantrypal.startup

import android.content.Context
import androidx.startup.Initializer
import br.com.teilorsoares.pantrypal.di.AppDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {
    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidContext(context)
            modules(AppDI.provideModules())
            allowOverride(true)
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}