package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun StatsScreen() {

Column(
modifier = Modifier
.fillMaxSize()
.padding(20.dp),
verticalArrangement = Arrangement.spacedBy(20.dp)
) {

// 📊 العنوان
Text(
text = "الإحصائيات",
style = MaterialTheme.typography.headlineLarge,
fontWeight = FontWeight.Bold
)

// 💰 مجموع الدخل
Card(modifier = Modifier.fillMaxWidth()) {
Column(modifier = Modifier.padding(20.dp)) {
Text("💰 مجموع الدخل", fontWeight = FontWeight.Medium)
Spacer(Modifier.height(8.dp))
Text(
text = "0 $",
style = MaterialTheme.typography.headlineMedium
)
}
}

// 💸 مجموع المصروف
Card(modifier = Modifier.fillMaxWidth()) {
Column(modifier = Modifier.padding(20.dp)) {
Text("💸 مجموع المصروف", fontWeight = FontWeight.Medium)
Spacer(Modifier.height(8.dp))
Text(
text = "0 $",
style = MaterialTheme.typography.headlineMedium
)
}
}

// 📈 نسبة الادخار
Card(modifier = Modifier.fillMaxWidth()) {
Column(modifier = Modifier.padding(20.dp)) {
Text("📈 نسبة الادخار", fontWeight = FontWeight.Medium)
Spacer(Modifier.height(12.dp))
LinearProgressIndicator(progress = 0f)
Spacer(Modifier.height(8.dp))
Text("0%")
}
}

// 🚧 قادم
Card(
modifier = Modifier.fillMaxWidth(),
colors = CardDefaults.cardColors(
containerColor = MaterialTheme.colorScheme.surfaceVariant
)
) {
Column(modifier = Modifier.padding(20.dp)) {
Text("🚧 المزيد من الإحصائيات قريبًا")
}
}
}
}