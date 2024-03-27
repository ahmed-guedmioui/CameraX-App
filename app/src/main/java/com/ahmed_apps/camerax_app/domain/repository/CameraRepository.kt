package com.ahmed_apps.camerax_app.domain.repository

import androidx.camera.view.LifecycleCameraController

/**
 * @author Ahmed Guedmioui
 */
interface CameraRepository {

    suspend fun takePhoto(
        controller: LifecycleCameraController
    )

    suspend fun recordVideo(
        controller: LifecycleCameraController
    )

}