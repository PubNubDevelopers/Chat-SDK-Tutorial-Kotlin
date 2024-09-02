package com.pubnub.chatsdk.kotlintutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.pubnub.api.UserId
import com.pubnub.api.enums.PNLogVerbosity
import com.pubnub.api.v2.PNConfiguration
import com.pubnub.chat.Chat
import com.pubnub.chat.config.ChatConfiguration
import com.pubnub.chat.init
import com.pubnub.chatsdk.kotlintutorial.ui.screens.HomeScreen
import com.pubnub.chatsdk.kotlintutorial.ui.screens.LoginScreen
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.kmp.PNFuture
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class MainActivity : ComponentActivity() {

    var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            PubNubKotlinChatSDKTutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(innerPadding, onLoginClicked = ::loginClicked)
                    //HomeScreen(innerPadding, userId = "darryn-2", name = "Darryn 2", logout = ::logoutClicked)
                }
            }
        }
    }

    private fun loginClicked(name: String) {
        if (name != "") {
            userId = convertToId(name)
            setContent {
                PubNubKotlinChatSDKTutorialTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        HomeScreen(
                            innerPadding,
                            userId = userId,
                            name = name,
                            logout = ::logoutClicked
                        )
                    }
                }
            }
        }
    }

    private fun logoutClicked() {
        setContent {
            PubNubKotlinChatSDKTutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(innerPadding, onLoginClicked = ::loginClicked)
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