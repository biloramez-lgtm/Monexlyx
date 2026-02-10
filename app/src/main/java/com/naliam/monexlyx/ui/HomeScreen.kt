package com.naliam.monexlyx.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {

Column(
modifier = Modifier
.fillMaxSize()
.padding(20.dp),
verticalArrangement = Arrangement.spacedBy(20.dp)
) {

// 🔷 اسم التطبيق
Text(
text = "Monexlyx",
style = MaterialTheme.typography.headlineLarge,
fontWeight = FontWeight.Bold
)

// 💰 كرت الرصيد الحالي
Card(
modifier = Modifier.fillMaxWidth(),
colors = CardDefaults.cardColors(
containerColor = MaterialTheme.colorScheme.primaryContainer
)
) {
Column(modifier = Modifier.padding(20.dp)) {

Text(
text = "💰 الرصيد الحالي",
fontWeight = FontWeight.Medium
)

Spacer(Modifier.height(8.dp))

Text(
text = "0 $",
style = MaterialTheme.typography.displaySmall,
fontWeight = FontWeight.Bold
)

Spacer(Modifier.height(6.dp))

Text(
text = "آخر تحديث: اليوم",
style = MaterialTheme.typography.bodySmall,
color = MaterialTheme.colorScheme.onSurfaceVariant
)
}
}

// 🎯 كرت هدف الادخار
Card(modifier = Modifier.fillMaxWidth()) {
Column(modifier = Modifier.padding(20.dp)) {

Row(
modifier = Modifier.fillMaxWidth(),
horizontalArrangement = Arrangement.SpaceBetween
) {
Text(
text = "🎯 هدف الادخار",
fontWeight = FontWeight.Medium
)
Text(
text = "0 / 1000 $",
style = MaterialTheme.typography.bodySmall
)
}

Spacer(Modifier.height(12.dp))

LinearProgressIndicator(
progress = 0f,
modifier = Modifier
.fillMaxWidth()
.height(8.dp)
)

Spacer(Modifier.height(8.dp))

Text(
text = "0% مكتمل",
style = MaterialTheme.typography.bodyMedium
)
}
}

// 🎁 كرت النقاط / الهدايا
Card(
modifier = Modifier.fillMaxWidth(),
colors = CardDefaults.cardColors(
containerColor = MaterialTheme.colorScheme.secondaryContainer
)
) {
Column(modifier = Modifier.padding(20.dp)) {

Text(
text = "🎁 نقاط التحفيز",
fontWeight = FontWeight.Medium
)

Spacer(Modifier.height(8.dp))

Text(
text = "0 نقطة",
style = MaterialTheme.typography.headlineSmall,
fontWeight = FontWeight.Bold
)
}
}

// ➕ زر إضافة
Button(
onClick = { },
modifier = Modifier.fillMaxWidth()
) {
Text("➕ إضافة عملية")
}
}
}