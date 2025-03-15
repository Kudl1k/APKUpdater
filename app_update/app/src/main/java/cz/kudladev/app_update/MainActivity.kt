package cz.kudladev.app_update

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import cz.kudladev.app_update.ui.theme.App_updateTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App_updateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Version 1"
                        )
                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    downloadAndInstallApk(context,"http://0.0.0.0:8080/newVersion")
                                }
                            }
                        ) {
                            Text(
                                text = "Update"
                            )
                        }
                    }
                }
            }
        }
    }
}

