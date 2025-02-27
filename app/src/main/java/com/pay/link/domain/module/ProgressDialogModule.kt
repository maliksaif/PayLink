package com.pay.link.domain.module

import android.app.Activity
import com.pay.link.presentation.utils.CustomProgressDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ProgressDialogModule {
    @Provides
    fun provideCustomProgressDialog(activity: Activity?): CustomProgressDialog {
        return CustomProgressDialog(activity)
    }
}
