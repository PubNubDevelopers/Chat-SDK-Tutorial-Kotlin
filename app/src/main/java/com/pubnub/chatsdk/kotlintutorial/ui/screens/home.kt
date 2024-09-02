package com.pubnub.chatsdk.kotlintutorial.ui.screens

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.api.models.consumer.objects.PNKey
import com.pubnub.api.models.consumer.objects.PNMemberKey
import com.pubnub.api.models.consumer.objects.PNSortKey
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.Event
import com.pubnub.chat.User
import com.pubnub.chat.config.ChatConfiguration
import com.pubnub.chat.config.LogLevel
import com.pubnub.chat.types.EventContent
import com.pubnub.chatsdk.kotlintutorial.await
import com.pubnub.chatsdk.kotlintutorial.createChat
import com.pubnub.chatsdk.kotlintutorial.ui.components.Avatar
import com.pubnub.chatsdk.kotlintutorial.ui.components.AvatarSize
import com.pubnub.chatsdk.kotlintutorial.ui.components.ChatMenuGroup
import com.pubnub.chatsdk.kotlintutorial.ui.components.Header
import com.pubnub.chatsdk.kotlintutorial.ui.components.NavBar
import com.pubnub.chatsdk.kotlintutorial.ui.components.PresenceIndicator
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.avatarBaseUrl
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.testAvatars
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy800
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.time.Duration.Companion.seconds

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        HomeScreen(userId = "darryn-1", name = "Darryn 1")
    }
}

@Composable
fun HomeScreen(innerPadding: PaddingValues = PaddingValues(0.dp), userId: String, name: String) {
    PubNubKotlinChatSDKTutorialTheme {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        var searchString by remember { mutableStateOf("") }
        var chat: Chat? by remember { mutableStateOf(null) }
        var publicChannels: List<Channel>? by remember { mutableStateOf(null) }
        var allUsers: List<User>? by remember { mutableStateOf(null) }
        var activeChannel: Channel? by remember { mutableStateOf(null) }
        LaunchedEffect(Unit) {
            chat = createChat(ChatConfiguration(LogLevel.VERBOSE,typingTimeout = 5.seconds), userId).await()
            //  Update the metadata associated with myself if this is the first time I have logged in
            if (chat?.currentUser?.profileUrl == null || chat?.currentUser?.profileUrl == "") {
                val randomProfileUrl =
                    avatarBaseUrl + testAvatars[Math.floor(Math.random() * testAvatars.size)
                        .toInt()]
                chat?.currentUser?.update(name = name, profileUrl = randomProfileUrl)?.await()
            }
            publicChannels =
                chat?.getChannels(filter = "id LIKE \"public*\"")?.await()?.channels?.toList()
            Log.d("DLOG", "Channel Count: " + publicChannels?.size)
            if (publicChannels?.size == 0) {
                Log.d("DLOG", "Creating Channels")
                //  The public channels do not exist on this keyset, create them with default values
                val customObjectGeneral = JSONObject()
                customObjectGeneral.put(
                    "profileUrl",
                    "https://chat-sdk-demo-web.netlify.app/group/globe1.png"
                )
                chat?.createPublicConversation(
                    channelId = "public-general",
                    channelName = "General Chat",
                    channelDescription = "Public group for general conversation",
                    channelCustom = customObjectGeneral
                )?.await()
                val customObjectWork = JSONObject()
                customObjectWork.put(
                    "profileUrl",
                    "https://chat-sdk-demo-web.netlify.app/group/globe2.png"
                )
                chat?.createPublicConversation(
                    channelId = "public-work",
                    channelName = "Work Chat",
                    channelDescription = "Public group for conversation about work",
                    channelCustom = customObjectWork
                )?.await()
                publicChannels =
                    chat?.getChannels(filter = "name LIKE \"public\"")?.await()?.channels?.toList()
                //  todo join public channels??  No, this was only if you want to show the membership of public channels
            }
            //  Get all the users.  This simple application will disolay all recent users and allow you to just click on somebody to
            //  start a direct conversation with them
            allUsers = chat?.getUsers(sort = listOf(PNSortKey.desc(PNKey.UPDATED)), limit = 10)?.await()?.users?.toList()

            //  Listen for events (not used in this demo, but to show the principle)
            //  todo how are you supposed to call this?
            //chat?.listenForEvents(channelId = chat?.currentUser!!.id, callback = {})

        }
        chat?.let { chat ->

            if (activeChannel == null) {
                //  No active channel, show list of channels to choose from
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .consumeWindowInsets(innerPadding)
                ) {
                    //Surface(
                    //    color = Navy800, modifier = Modifier
                    //        .height(27.dp)
                    //        .fillMaxWidth()
                    //) {}
                    Header()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .weight(weight = 1f, fill = true)
                        )
                        {


                            //val publicChannels by produceState(listOf<Channel>()) {
                            //    while (isActive) {
                            //        value = chat.getChannels(filter = "name LIKE \"public\"").await().channels.toList()
                            //        delay(15.seconds)
                            //    }
                            //}
                            //Log.d("DLOG", "" + publicChannels.size)

                            ChatMenuGroup(
                                chat = chat,
                                groupName = "PUBLIC CHANNELS",
                                actionIconShown = false,
                                channels = publicChannels,
                                channelSelected = fun(selectedChannel) {
                                    activeChannel = selectedChannel
                                },
                                userSelected = {})
                            ChatMenuGroup(
                                chat = chat,
                                groupName = "PRIVATE GROUPS",
                                actionIconShown = true,
                                channels = null,
                                channelSelected = fun(selectedChannel) {
                                    activeChannel = selectedChannel
                                },
                                userSelected = {})
                            ChatMenuGroup(
                                chat = chat,
                                groupName = "DIRECT MESSAGES",
                                actionIconShown = true,
                                channels = null,
                                users = allUsers,
                                channelSelected = {},
                                userSelected = fun(selectedUser){
                                    scope.launch {
                                        val result = chat.createDirectConversation(selectedUser).await()
                                        activeChannel = result.channel
                                    }
                                })


                        }
                        //Spacer(Modifier.weight(1f))
                        NavBar()
                    }

                }
            } else {
                ChatScreen(chat = chat, activeChannel = activeChannel, onChannelChanged = {activeChannel = it})
            }
        }
    }
}

