package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {

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
Card(modifier = Modifier.fillMaxWidth()) {
Row(
modifier = Modifier
.fillMaxWidth()
.padding(20.dp),
verticalAlignment = Alignment.CenterVertically,
horizontalArrangement = Arrangement.SpaceBetween
) {
Text("🌙 الوضع الليلي")
Switch(
checked = false,
onCheckedChange = { }
)
}
}

// 🔔 الإشعارات
Card(modifier = Modifier.fillMaxWidth()) {
Row(
modifier = Modifier
.fillMaxWidth()
.padding(20.dp),
verticalAlignment = Alignment.CenterVertically,
horizontalArrangement = Arrangement.SpaceBetween
) {
Text("🔔 الإشعارات")
Switch(
checked = true,
onCheckedChange = { }
)
}
}

// 🌍 العملة
Card(modifier = Modifier.fillMaxWidth()) {
Column(modifier = Modifier.padding(20.dp)) {
Text(
text = "🌍 العملة",
fontWeight = FontWeight.Medium
)
Spacer(Modifier.height(8.dp))
Text(
text = "USD ($)",
style = MaterialTheme.typography.bodyMedium,
color = MaterialTheme.colorScheme.onSurfaceVariant
)
}
}

// ℹ️ عن التطبيق
Card(modifier = Modifier.fillMaxWidth()) {
Column(modifier = Modifier.padding(20.dp)) {
Text(
text = "ℹ️ عن التطبيق",
fontWeight = FontWeight.Medium
)
Spacer(Modifier.height(8.dp))
Text(
text = "Monexlyx\nإدارة الأموال والادخار\nالإصدار 1.0",
style = MaterialTheme.typography.bodyMedium
)
}
}
}
}