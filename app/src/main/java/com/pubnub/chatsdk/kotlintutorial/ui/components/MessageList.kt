package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.Message

@Composable
fun MessageList(
    messages: List<Message>,
    readReceipts: Map<Long, List<String>>,
    chat: Chat?,
    activeChannel: Channel?
) {
    var margin = 120.dp
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size)
    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        LazyColumn(modifier = Modifier.padding(bottom = margin).fillMaxWidth(), state = listState)
        {
            items(messages) {
                msg -> Message(
                chat = chat,
                activeChannel = activeChannel,
                message = msg,
                readReceipts = readReceipts,
                received = chat?.currentUser?.id != msg.userId,
            )
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = true)
        )
        {



        }
    }

}