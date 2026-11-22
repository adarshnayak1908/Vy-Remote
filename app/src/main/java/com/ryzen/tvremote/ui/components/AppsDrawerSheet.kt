package com.ryzen.tvremote.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ryzen.tvremote.apps.AppShortcut

@Composable
fun AppsDrawerSheet(
    shortcuts: List<AppShortcut>,
    enabled: Boolean,
    onLaunch: (AppShortcut) -> Unit,
    onAddApp: () -> Unit,
    onDismiss: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    RemoteSheet(
        title = "Apps",
        subtitle = "Streaming app shortcuts",
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
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val rows = shortcuts.chunked(3)
            rows.forEach { rowShortcuts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    rowShortcuts.forEach { shortcut ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable(
                                    enabled = enabled,
                                    role = Role.Button,
                                    onClick = {
                                        onLaunch(shortcut)
                                        onDismiss()
                                    }
                                )
                                .padding(8.dp)
                        ) {
                            AppTileIcon(
                                name = shortcut.name,
                                color = Color(shortcut.colorArgb),
                                size = 64.dp,
                                presetId = shortcut.presetId
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = shortcut.name,
                                style = MaterialTheme.typography.labelLarge,
                                color = scheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    repeat(3 - rowShortcuts.size) {
                        Spacer(Modifier.size(80.dp))
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            PressSurface(
                onClick = {
                    onDismiss()
                    onAddApp()
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
                    Icon(Icons.Rounded.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                    Text(
                        text = "Add Custom App",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun AppsDrawerSheetPreview() {
    com.ryzen.tvremote.ui.theme.TVRemoteTheme {
        AppsDrawerSheet(
            shortcuts = listOf(
                AppShortcut("1", "Netflix", "netflix", 0xFFE50914, "netflix"),
                AppShortcut("2", "Prime Video", "primevideo", 0xFF00A8E1, "primevideo"),
                AppShortcut("3", "YouTube", "youtube", 0xFFFF0033, "youtube"),
                AppShortcut("4", "Hotstar", "hotstar", 0xFF5A31F4, "hotstar"),
                AppShortcut("5", "ZEE5", "zee5", 0xFFFFFFFF, "zee5"),
                AppShortcut("6", "SonyLIV", "sonyliv", 0xFF121212, "sonyliv")
            ),
            enabled = true,
            onLaunch = {},
            onAddApp = {},
            onDismiss = {}
        )
    }
}
