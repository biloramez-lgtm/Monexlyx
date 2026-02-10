package com.naliam.monexlyx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naliam.monexlyx.data.SettingsDataStore
import com.naliam.monexlyx.data.db.ExpenseViewModel
import com.naliam.monexlyx.ui.HomeScreen
import com.naliam.monexlyx.ui.SettingsScreen
import com.naliam.monexlyx.ui.StatsScreen
import com.naliam.monexlyx.ui.theme.MonexlyxTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingsStore = SettingsDataStore(applicationContext)

        setContent {

            val coroutineScope = rememberCoroutineScope()

            val darkMode by settingsStore.darkModeFlow.collectAsState(initial = false)
            val notificationsEnabled by settingsStore.notificationsFlow.collectAsState(initial = true)

            val expenseViewModel: ExpenseViewModel = viewModel()

            MonexlyxTheme(
                darkTheme = darkMode,
                dynamicColor = false
            ) {
                AppRoot(
                    expenseViewModel = expenseViewModel,
                    darkMode = darkMode,
                    notificationsEnabled = notificationsEnabled,
                    onDarkModeChange = {
                        coroutineScope.launch {
                            settingsStore.setDarkMode(it)
                        }
                    },
                    onNotificationsChange = {
                        coroutineScope.launch {
                            settingsStore.setNotifications(it)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppRoot(
    expenseViewModel: ExpenseViewModel,
    darkMode: Boolean,
    notificationsEnabled: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onNotificationsChange: (Boolean) -> Unit
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = Icons.Default.Home,
                    label = "الرئيسية"
                )
                NavItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = Icons.Default.TrendingUp,
                    label = "إحصائيات"
                )
                NavItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = Icons.Default.Settings,
                    label = "الإعدادات"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(expenseViewModel)
                1 -> StatsScreen(expenseViewModel)
                2 -> SettingsScreen(
                    darkMode = darkMode,
                    notificationsEnabled = notificationsEnabled,
                    onDarkModeChange = onDarkModeChange,
                    onNotificationsChange = onNotificationsChange
                )
            }
        }
    }
}

@Composable
private fun NavItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) }
    )
}
