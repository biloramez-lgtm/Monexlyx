package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // 🔷 العنوان
        Text(
            text = "الإعدادات",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        // 🌙 الوضع الليلي
        SettingsSwitchCard(
            title = "🌙 الوضع الليلي",
            checked = darkMode,
            onCheckedChange = onDarkModeChange
        )

        // 🔔 الإشعارات
        SettingsSwitchCard(
            title = "🔔 الإشعارات",
            checked = notificationsEnabled,
            onCheckedChange = onNotificationsChange
        )

        // 🌍 العملة
        SettingsInfoCard(
            title = "🌍 العملة",
            value = "USD ($)"
        )

        // ℹ️ عن التطبيق
        SettingsInfoCard(
            title = "ℹ️ عن التطبيق",
            value = "Monexlyx\nإدارة الأموال والادخار\nالإصدار 1.0"
        )
    }
}

/* =======================
   Components (مضافة)
   ======================= */

@Composable
private fun SettingsSwitchCard(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
private fun SettingsInfoCard(
    title: String,
    value: String
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
