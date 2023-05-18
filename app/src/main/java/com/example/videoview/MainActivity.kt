package com.example.videoview

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.videoview.ui.theme.VideoViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContent {
            VideoViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}
@Composable
fun MyApp(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "startScreen"){
        composable("startScreen"){
            MyScreen(navController)
        }
        composable("lastScreen")
        {
            LastScreen()
        }
    }
}
@Composable
fun MyScreen(navController: NavController) {
    val videoUri = remember { Uri.parse("android.resource://com.example.videoview/" + R.raw.my_video) }

    Box(modifier = Modifier.fillMaxSize()) {
        ComposeWithXML(videoUri,navController)
    }
}


@Composable
fun ComposeWithXML(videouri : Uri,navController: NavController)
{
    AndroidView(factory = {
        context -> VideoView(context).apply {
            setVideoURI(videouri)
            start() } },
        update = { view ->
            view.setVideoURI(videouri)
            view.setOnCompletionListener {
                navController.navigate("lastScreen")
            }
            view.start()
    }
    )
}

@Composable
fun LastScreen(){
    Text("Hi Welcome to the last screen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VideoViewTheme {
    }
}