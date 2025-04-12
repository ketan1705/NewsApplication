package com.ken.newsapplication.domain.usecases.appentry

import com.ken.newsapplication.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}