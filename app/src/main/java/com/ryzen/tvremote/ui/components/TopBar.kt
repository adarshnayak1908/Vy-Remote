package com.ryzen.tvremote.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material.icons.rounded.TvOff
import androidx.compose.material.icons.rounded.UnfoldMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ryzen.tvremote.protocol.ConnectionStatus
import com.ryzen.tvremote.protocol.KeyDirection
import com.ryzen.tvremote.ui.theme.Motion
import com.ryzen.tvremote.ui.theme.RemoteTheme

import androidx.compose.material.icons.rounded.GridView

@Composable
fun TopBar(
    status: ConnectionStatus,
    deviceName: String,
    hasTv: Boolean,
    onDevicePickerClick: () -> Unit,
    onAppsDrawerClick: () -> Unit,
    onPowerKey: (KeyDirection) -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val connected = status is ConnectionStatus.Connected

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TV Devices Drawer button (TV icon with status indicator)
        PressSurface(
            onClick = onDevicePickerClick,
            modifier = Modifier.size(60.dp),
            description = "TV Devices. ${if (hasTv) deviceName else "No TV"}"
        ) {
            StatusBadge(status)
        }

        // Apps Drawer button (Grid icon + "Apps")
        PressSurface(
            onClick = onAppsDrawerClick,
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 4.dp),
            description = "Streaming Apps"
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.GridView,
                    contentDescription = null,
                    tint = scheme.onSurface,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "Apps",
                    style = MaterialTheme.typography.titleMedium,
                    color = scheme.onSurface
                )
            }
        }

        Spacer(Modifier.weight(1f))

        // Tap toggles power; hold for the TV's power menu.
        HoldableKey(
            icon = Icons.Rounded.PowerSettingsNew,
            description = "Power",
            longPressLabel = "Power menu",
            onKey = onPowerKey,
            size = 60.dp,
            iconSize = 26.dp,
            color = scheme.errorContainer,
            contentColor = scheme.onErrorContainer,
            enabled = connected
        )
        IconSurface(
            icon = Icons.Rounded.Settings,
            description = "Settings",
            onClick = onSettingsClick,
            size = 60.dp,
            iconSize = 24.dp
        )
    }
}

/** Circle that blooms into a cookie when the TV connects; a tumbling loader while it tries. */
@Composable
private fun StatusBadge(status: ConnectionStatus) {
    val scheme = MaterialTheme.colorScheme
    val connected = status is ConnectionStatus.Connected
    val busy = status is ConnectionStatus.Connecting || status is ConnectionStatus.NeedsPairing
    val isError = status is ConnectionStatus.Error

    val bloom = animateFloatAsState(if (connected) 1f else 0f, Motion.spatial(), label = "bloom")
    val container by animateColorAsState(
        targetValue = when {
            connected -> RemoteTheme.status.success
            isError -> scheme.errorContainer
            else -> scheme.surfaceContainerHighest
        },
        animationSpec = Motion.effects(),
        label = "badgeColor"
    )
    val tint = when {
        connected -> RemoteTheme.status.onSuccess
        isError -> scheme.onErrorContainer
        else -> scheme.onSurfaceVariant
    }

    MorphBox(
        from = Polygons.Circle,
        to = Polygons.Cookie,
        progress = { bloom.value },
        rotation = { bloom.value * 45f },
        color = container,
        modifier = Modifier.size(42.dp)
    ) {
        if (busy) {
            LoadingMorph(color = RemoteTheme.status.warning, size = 22.dp)
        } else {
            Icon(
                imageVector = if (isError) Icons.Rounded.TvOff else Icons.Rounded.Tv,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun TopBarPreview() {
    com.ryzen.tvremote.ui.theme.TVRemoteTheme {
        TopBar(
            status = com.ryzen.tvremote.protocol.ConnectionStatus.Connected("192.168.1.100", "Vicky-TV"),
            deviceName = "Vicky-TV",
            hasTv = true,
            onDevicePickerClick = {},
            onAppsDrawerClick = {},
            onPowerKey = {},
            onSettingsClick = {}
        )
    }
}
