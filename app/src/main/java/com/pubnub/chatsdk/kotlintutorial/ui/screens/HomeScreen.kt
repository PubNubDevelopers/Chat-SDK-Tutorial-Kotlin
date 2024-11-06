package com.pubnub.chatsdk.kotlintutorial.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.pubnub.chat.listenForEvents
import com.pubnub.chat.types.EventContent
import com.pubnub.chatsdk.kotlintutorial.await
import com.pubnub.chatsdk.kotlintutorial.createChat
import com.pubnub.chatsdk.kotlintutorial.ui.components.ChatMenuGroup
import com.pubnub.chatsdk.kotlintutorial.ui.components.Header
import com.pubnub.chatsdk.kotlintutorial.ui.components.NavBar
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.avatarBaseUrl
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.testAvatars
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.math.floor
import kotlin.time.Duration.Companion.seconds

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        HomeScreen(userId = "darryn-1", name = "Darryn 1")
    }
}

@Composable
fun HomeScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    userId: String,
    name: String,
    logout: () -> Unit = {}
) {
    PubNubKotlinChatSDKTutorialTheme {
        val scope = rememberCoroutineScope()
        var chat: Chat? by remember { mutableStateOf(null) }
        var publicChannels: List<Channel>? by remember { mutableStateOf(null) }
        var allUsers: List<User>? by remember { mutableStateOf(null) }
        var activeChannel: Channel? by remember { mutableStateOf(null) }
        LaunchedEffect(Unit) {
            chat = createChat(
                ChatConfiguration(LogLevel.VERBOSE, typingTimeout = 5.seconds),
                userId
            ).await()
            //  Update the metadata associated with myself if this is the first time I have logged in
            if (chat?.currentUser?.profileUrl == null || chat?.currentUser?.profileUrl == "") {
                val randomProfileUrl =
                    avatarBaseUrl + testAvatars[floor(Math.random() * testAvatars.size)
                        .toInt()]
                chat?.currentUser?.update(name = name, profileUrl = randomProfileUrl)?.await()
            }
            //  Note on Sorting.  You can create a sort order as follows:
            //val sortOrder: Collection<PNSortKey<PNKey>> = listOf(PNSortKey.PNAsc(PNKey.ID))
            //  Then pass it to the getChannels call using sort=sortOrder
            publicChannels =
                chat?.getChannels(filter = "id LIKE \"public*\"")?.await()?.channels?.toList()
            if (publicChannels?.size == 0) {
                //  The public channels do not exist on this keyset, create them with default values
                val customObjectGeneral = JSONObject()
                customObjectGeneral.put(
                    "profileUrl",
                    "$avatarBaseUrl/group/globe1.png"
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
                    "$avatarBaseUrl/group/globe2.png"
                )
                chat?.createPublicConversation(
                    channelId = "public-work",
                    channelName = "Work Chat",
                    channelDescription = "Public group for conversation about work",
                    channelCustom = customObjectWork
                )?.await()
                publicChannels =
                    chat?.getChannels(filter = "name LIKE \"public\"")?.await()?.channels?.toList()

            }
            //  Get all the users.  This simple application will display all recent users and allow you to just click on somebody to
            //  start a direct conversation with them
            allUsers = chat?.getUsers(sort = listOf(PNSortKey.desc(PNKey.UPDATED)), limit = 10)
                ?.await()?.users?.toList()

            //  Note: This simple application does not worry about 'inviting' users to 'join' channels.
            //  Channels are joined when you select them, and all users are shown as available for DMs.
            //  In production, you would have a separate step where only conversations you are invited to
            //  (or a member of) are shown in the conversation list.  I.e. clients would join all public channels
            //  but only be made a member of direct (1:1) or group conversations after being invited to them.
            //  Invitations are received as documented at https://pubnub.com/docs/chat/kotlin-chat-sdk/build/features/channels/invite#listen-to-invite-events
            //  chat.listenForEvents("YOUR USER ID") {event: Event<EventContent.Invite}
            /*
                chat?.currentUser?.id?.let {
                  chat?.listenForEvents(it) { event: Event<EventContent.Invite> ->
                  println("Notification: Received an invite for userId: '${event.payload.channelId}'")
                  }
                }
             */
        }
        chat?.let { chat ->

            if (activeChannel == null) {
                //  No active channel, show list of channels to choose from
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .consumeWindowInsets(innerPadding)
                ) {
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

                            ChatMenuGroup(
                                chat = chat,
                                groupName = "PUBLIC CHANNELS",
                                actionIconShown = false,
                                channels = publicChannels,
                                channelSelected = fun(selectedChannel) {
                                    activeChannel = selectedChannel
                                },
                                userSelected = {})
                            //  Private groups (i.e. a private group with multiple members) removed from this application for simplicity
//                            ChatMenuGroup(
//                                chat = chat,
//                                groupName = "PRIVATE GROUPS",
//                                actionIconShown = true,
//                                channels = null,
//                                channelSelected = fun(selectedChannel) {
//                                    activeChannel = selectedChannel
//                                },
//                                userSelected = {})
                            ChatMenuGroup(
                                chat = chat,
                                groupName = "DIRECT MESSAGES",
                                actionIconShown = true,
                                channels = null,
                                users = allUsers,
                                channelSelected = {},
                                userSelected = fun(selectedUser) {
                                    scope.launch {
                                        val result =
                                            chat.createDirectConversation(selectedUser).await()
                                        activeChannel = result.channel
                                    }
                                })


                        }
                        //Spacer(Modifier.weight(1f))
                        NavBar(logout = logout)
                    }

                }
            } else {
                ChatScreen(
                    chat = chat,
                    activeChannel = activeChannel,
                    onChannelChanged = { activeChannel = it })
            }
        }
    }
}

