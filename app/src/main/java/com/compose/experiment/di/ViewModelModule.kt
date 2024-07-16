package com.compose.experiment.di

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.compose.experiment.MainViewModel
import com.compose.experiment.repository.DefaultUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    fun provideMainViewModule(userRepository: DefaultUserRepository, notificationManager: NotificationManagerCompat, notificationBuilder: NotificationCompat.Builder) : MainViewModel {
        return MainViewModel(userRepository, notificationManager = notificationManager, notificationBuilder = notificationBuilder)
    }

}