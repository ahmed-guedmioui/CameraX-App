package com.ahmed_apps.camerax_app.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.ahmed_apps.camerax_app.MainActivity
import com.ahmed_apps.camerax_app.R

/**
 * @author Ahmed Guedmioui
 */

@Composable
fun CameraScreen(
    activity: Activity
) {
    val controller = remember {
        LifecycleCameraController(
            activity.applicationContext
        ).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or
                        CameraController.VIDEO_CAPTURE
            )
        }
    }

    val cameraViewModel = hiltViewModel<CameraViewModel>()
    val isRecording by cameraViewModel.isRecording.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        val lifecycleOwner = LocalLifecycleOwner.current
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .size(45.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                "content://media/internal/images/media"
                            )
                        ).also {
                            activity.startActivity(it)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PhotoLibrary,
                    contentDescription = stringResource(R.string.open_gallery),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(1.dp))

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        if ((activity as MainActivity).arePermissionsGranted()) {
                            cameraViewModel.onRecordVideo(controller)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector =
                    if (isRecording) Icons.Default.Stop
                    else Icons.Default.Videocam,
                    contentDescription = stringResource(R.string.record_video),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        if ((activity as MainActivity).arePermissionsGranted()) {
                            cameraViewModel.onTakePhoto(controller)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector =Icons.Default.Camera,
                    contentDescription = stringResource(R.string.take_photo),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(1.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .size(45.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Cameraswitch,
                    contentDescription = stringResource(R.string.switch_camera_preview),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(26.dp)
                )
            }

        }

    }

}






















