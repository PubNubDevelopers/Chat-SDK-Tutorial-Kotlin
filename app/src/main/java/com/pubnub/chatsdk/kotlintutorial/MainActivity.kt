package com.pubnub.chatsdk.kotlintutorial

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme

import com.pubnub.api.UserId
import com.pubnub.api.enums.PNLogVerbosity
import com.pubnub.api.v2.PNConfiguration
import com.pubnub.chat.Chat
import com.pubnub.chat.config.ChatConfiguration
import com.pubnub.chat.init
import com.pubnub.kmp.PNFuture

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.pubnub.chat.config.LogLevel
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.toLowerCase
import androidx.core.view.WindowCompat
import com.pubnub.chatsdk.kotlintutorial.ui.screens.ChatScreen
import com.pubnub.chatsdk.kotlintutorial.ui.screens.HomeScreen
import com.pubnub.chatsdk.kotlintutorial.ui.screens.LoginScreen
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.testAvatars



class MainActivity : ComponentActivity() {

    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            PubNubKotlinChatSDKTutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //Greeting(
                    //    name = "Android",
                    //    modifier = Modifier.padding(innerPadding)
                    //)
                    //val loginClicked = {userId: String -> Log.d("KotlinChatSDK","Login Clicked: " + userId)}
                    //LoginScreen(innerPadding, onLoginClicked = ::loginClicked)
                    HomeScreen(innerPadding, userId = "darryn-2", name = "Darryn 2")
                    //ChatScreen()
                }
            }
        }
    }

    private fun loginClicked(name: String) {
        //Log.d("KotlinChatSDK","Login Clicked: " + name)
        //Log.d("KotlinChatSDK","Login Clicked: " + convertToId(name))
        if (name != "") {
            userId = convertToId(name)
            setContent {
                PubNubKotlinChatSDKTutorialTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        HomeScreen(
                            innerPadding,
                            userId = userId,
                            name = name
                        )
                    }
                }
            }
        }
    }

    //  Convert the user's chosen name into an ID that PubNub can use
    private fun convertToId(name: String): String {
        val pattern = "[^a-zA-Z0-9]".toRegex()
        return name.replace(pattern, "-").lowercase()
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var chat: Chat? by remember { mutableStateOf(null) }
    LaunchedEffect(Unit) {
        //chat = createChat(ChatConfiguration(LogLevel.VERBOSE)).await()
    }
    Text(
        text = "Hello $name! ${chat?.currentUser?.name}",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PubNubKotlinChatSDKTutorialTheme {
        Greeting("Android")
    }
}


fun createChat(chatConfiguration: ChatConfiguration, userId: String): PNFuture<Chat> {
    return Chat.init(
        chatConfiguration,
        PNConfiguration.builder(
            UserId(userId),
            PUBNUB_SUBSCRIBE_KEY
        ) {
            publishKey = PUBNUB_PUBLISH_KEY
            logVerbosity = PNLogVerbosity.BODY
        }.build()
    )
}

suspend fun <T> PNFuture<T>.await() = suspendCancellableCoroutine { cont ->
    async { result ->
        result.onSuccess { success ->
            cont.resume(success)
        }.onFailure { ex ->
            cont.resumeWithException(ex)
        }
    }
}