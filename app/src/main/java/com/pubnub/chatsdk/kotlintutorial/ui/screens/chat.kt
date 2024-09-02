package com.pubnub.chatsdk.kotlintutorial.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.Membership
import com.pubnub.chat.Message
import com.pubnub.chat.streamUpdatesOn
import com.pubnub.chat.types.ChannelType
import com.pubnub.chatsdk.kotlintutorial.await
import com.pubnub.chatsdk.kotlintutorial.ui.components.Header
import com.pubnub.chatsdk.kotlintutorial.ui.components.MessageInput
import com.pubnub.chatsdk.kotlintutorial.ui.components.MessageList
import com.pubnub.chatsdk.kotlintutorial.ui.components.PresenceIndicator
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

/*
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        ChatScreen(typingMessageShown = true, onChannelChanged = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview2() {
    PubNubKotlinChatSDKTutorialTheme {
        ChatScreen(typingMessageShown = false, onChannelChanged = {})
    }
}
 */

@Composable
fun ChatScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    chat: Chat?,
    typingMessageShown: Boolean = false,
    activeChannel: Channel? = null,
    onChannelChanged: (Channel?) -> Unit
) {
    var messages: List<Message> by remember(activeChannel) { mutableStateOf(listOf()) }
    var readReceipts: Map<Long, List<String>> by remember { mutableStateOf(mapOf(1L to listOf(""))) }
    val headerText = remember { mutableStateOf(activeChannel?.name.toString()) }
    val headerProfileUrl =
        remember { mutableStateOf(activeChannel?.custom?.get("profileUrl").toString()) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(activeChannel) {
        var unsubscribe: AutoCloseable? = null
        try {
            val channelMemberships: Set<Membership>? = chat?.currentUser?.getMemberships(filter = "channel.id == '" + activeChannel?.id + "'")?.await()?.memberships
            var myChannelMembership: Membership? = null
            if (channelMemberships?.isNotEmpty() == true)
            {
                myChannelMembership = channelMemberships.first()
            }
            //  todo something with receiptStream?
            //  Stream Read Receipts
            if (activeChannel?.type != ChannelType.PUBLIC)
            {
                val receiptStream = activeChannel?.streamReadReceipts { receipts ->
                    readReceipts = receipts
                    Log.d("LOGD", "Read Receipts Received:")
                    receipts.forEach { (messageTimetoken, users) ->
                        Log.d("LOGD", "Message Timetoken: $messageTimetoken was read by users: $users")
                    }}
            }
            unsubscribe = activeChannel?.join {
                messages = messages + it
                scope.launch {
                    myChannelMembership?.setLastReadMessage(it)?.await()
                }
                //  todo should I be doing something with autoClosable?  This is for message reactions
                // stream updates for the fetched messages
                val autoClosable = Message.streamUpdatesOn(messages = messages) { updatedMessages ->
                    updatedMessages.forEach { updatedMessage ->
                        Log.d("LOGD", "Message Updated2: " + updatedMessages.size)
                        //println("-=Updated message: $updatedMessage")
                    }
                    messages = updatedMessages.toList()
                }




                }?.await()?.disconnect
            activeChannel?.getHistory(count = 20)?.await()?.let {
                messages = it.messages + messages
                if (!messages.isEmpty())
                {
                    scope.launch {
                        myChannelMembership?.setLastReadMessageTimetoken(messages.last().timetoken)
                            ?.await()
                        Log.d(
                            "LOGD",
                            "Setting last read message timetoken to " + messages.last().timetoken
                        )
                    }
                }
            }
            // stream updates for the fetched messages
            if (!messages.isEmpty())
            {
                val autoClosable = Message.streamUpdatesOn(messages = messages) { updatedMessages ->
                    updatedMessages.forEach { updatedMessage ->
                        Log.d("LOGD", "Message Updated: " + updatedMessages.size)
                        //println("-=Updated message: $updatedMessage")
                    }
                    messages = updatedMessages.toList()
                }
            }


            if (activeChannel?.type == ChannelType.DIRECT) {
                val members = activeChannel?.getMembers()?.await()?.members
                if (members != null) {
                    for (member in members) {
                        if (member.user.id != chat?.currentUser?.id) {
                            //  This is the other person in the chat
                            headerText.value = member?.user?.name.toString()
                            headerProfileUrl.value = member?.user?.profileUrl.toString()
                        }
                    }
                }
            }

            awaitCancellation()
        } catch (e: CancellationException) {
            withContext(NonCancellable) {
                unsubscribe?.close()
            }
        }
    }
    PubNubKotlinChatSDKTutorialTheme {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxHeight()
                .consumeWindowInsets(innerPadding)
        ) {
            //activeChannel?.let { TypingIndicator2(it) }

            Header(
                chatLayout = true, backAction = fun() {
                    onChannelChanged(null)
                }, title = headerText.value,//"Sarah Johannsen",
                avatarUrl = headerProfileUrl.value,//"https://chat-sdk-demo-web.netlify.app/avatars/f/68.jpg",
                avatarPresence = PresenceIndicator.ONLINE
            )

            Box() {
                MessageList(
                    chat = chat, activeChannel = activeChannel, messages = messages, readReceipts = readReceipts
                )

                MessageInput(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color.White),
                    activeChannel = activeChannel,
                    //typingMessageShown = typingMessageShown,
                    sendMessage = fun(messageText) {
                        scope.launch {
                            activeChannel?.sendText(messageText)?.await()
                        }
                    },
                    startTyping = fun() {
                        scope.launch {
                            activeChannel?.let { channel ->
                                if (channel.type != ChannelType.PUBLIC) {
                                    channel.startTyping().await()
                                }
                            }
                        }
                    })

            }


        }
    }
}
/*
@Composable
fun TypingIndicator2(channel: Channel) {
    if (channel.type != ChannelType.PUBLIC) {
        val typingIndicators: Collection<String> by produceState<Collection<String>>(listOf()) {
            val sub = channel.getTyping {
                println("Got typing $it")
                value = it
            }
            awaitDispose {
                println("disposing")
                sub.close()
            }
        }
        if (typingIndicators.isNotEmpty()) {
            Text("Typing: " + typingIndicators.joinToString(", "))
        }
    }
}

 */