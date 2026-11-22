package com.ryzen.tvremote.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material.icons.rounded.WifiFind
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ryzen.tvremote.protocol.DiscoveredDevice
import com.ryzen.tvremote.ui.theme.RemoteTheme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DevicePickerSheet(
    currentIp: String,
    connectedIp: String?,
    discoveredDevices: List<DiscoveredDevice>,
    isScanning: Boolean,
    onScanClick: () -> Unit,
    onConnect: (ip: String, name: String?) -> Unit,
    onDismiss: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    var ipInput by remember { mutableStateOf(currentIp) }
    var showManualIp by remember { mutableStateOf(false) }

    RemoteSheet(
        title = "TV Devices",
        subtitle = if (isScanning) "Searching on Wi-Fi…" else "Select a TV device to control",
        onDismiss = onDismiss,
        action = {
            IconSurface(
                icon = Icons.Rounded.Close,
                description = "Close",
                onClick = onDismiss,
                color = scheme.surfaceContainerHighest,
                size = 40.dp,
                iconSize = 20.dp
            )
        }
    ) {
        if (discoveredDevices.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(scheme.surfaceContainerHigh)
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(Icons.Rounded.WifiFind, null, tint = scheme.onSurfaceVariant, modifier = Modifier.size(28.dp))
                Text(
                    text = if (isScanning) "Searching for TVs on Wi-Fi…"
                    else "No TVs discovered. Check that your TV is on and on the same Wi-Fi, or enter its IP address below.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = scheme.onSurfaceVariant
                )
            }
        } else {
            Group {
                discoveredDevices.forEachIndexed { i, device ->
                    val isConnected = device.host == connectedIp
                    GroupRow(
                        index = i,
                        count = discoveredDevices.size,
                        title = device.name,
                        subtitle = if (isConnected) "● Connected" else "● Not Connected",
                        icon = Icons.Rounded.Tv,
                        iconBackground = if (isConnected) RemoteTheme.status.success else scheme.secondaryContainer,
                        iconTint = if (isConnected) RemoteTheme.status.onSuccess else scheme.onSecondaryContainer,
                        onClick = { onConnect(device.host, device.name) },
                        trailing = {
                            if (isConnected) {
                                Icon(
                                    Icons.Rounded.Check,
                                    contentDescription = "Connected",
                                    tint = scheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Icon(
                                    Icons.Rounded.RadioButtonUnchecked,
                                    contentDescription = "Select",
                                    tint = scheme.onSurfaceVariant,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (showManualIp) {
            SectionLabel("Connect by IP address")
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = ipInput,
                    onValueChange = { ipInput = it.trim() },
                    singleLine = true,
                    placeholder = { Text("192.168.1.100") },
                    shape = RoundedCornerShape(50),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri, imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { if (ipInput.isNotEmpty()) onConnect(ipInput, null) }),
                    modifier = Modifier.weight(1f)
                )
                IconSurface(
                    icon = Icons.AutoMirrored.Rounded.ArrowForward,
                    description = "Connect",
                    onClick = { onConnect(ipInput, null) },
                    enabled = ipInput.isNotEmpty(),
                    size = 56.dp,
                    color = scheme.primary,
                    contentColor = scheme.onPrimary
                )
            }
            Spacer(Modifier.height(12.dp))
        }

        // Manage Devices button matching the diagram
        PressSurface(
            onClick = {
                if (showManualIp) onScanClick() else showManualIp = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            color = scheme.primaryContainer,
            contentColor = scheme.onPrimaryContainer
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = if (showManualIp) Icons.Rounded.Refresh else Icons.Rounded.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = if (showManualIp) "Rescan Wi-Fi Network" else "Manage Devices",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun DevicePickerSheetPreview() {
    com.ryzen.tvremote.ui.theme.TVRemoteTheme {
        DevicePickerSheet(
            currentIp = "192.168.1.100",
            connectedIp = "192.168.1.100",
            discoveredDevices = listOf(
                com.ryzen.tvremote.protocol.DiscoveredDevice("Vicky-TV", "192.168.1.100", 6467, "_androidtvremote2._tcp."),
                com.ryzen.tvremote.protocol.DiscoveredDevice("Living Room TV", "192.168.1.101", 6467, "_androidtvremote2._tcp."),
                com.ryzen.tvremote.protocol.DiscoveredDevice("Bedroom TV", "192.168.1.102", 6467, "_androidtvremote2._tcp.")
            ),
            isScanning = false,
            onScanClick = {},
            onConnect = { _, _ -> },
            onDismiss = {}
        )
    }
}
