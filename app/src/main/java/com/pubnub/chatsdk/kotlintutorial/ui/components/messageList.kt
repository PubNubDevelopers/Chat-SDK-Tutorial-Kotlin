package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.Message
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Teal100
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography


//@Preview(showBackground = true)
@Composable
fun MessageList(
    messages: List<Message>,
    readReceipts: Map<Long, List<String>>,
    //typingMessageShown: Boolean = false,
    chat: Chat?,
    activeChannel: Channel?
) {
    var margin = 120.dp
    //if (typingMessageShown) margin = 150.dp
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        /*modifier = Modifier.fillMaxHeight()*/
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
                //avatarUrl = "https://chat-sdk-demo-web.netlify.app/avatars/f/68.jpg",
                //messageText = "Aliquam a magna arcu tellus pellentesque mi pellentesque. Feugiat et a eget rutrum leo in. Pretium cras amet consequat est metus sodales. Id phasellus habitant dignissim viverra. Nulla non faucibus mus scelerisque diam. Nulla a quis venenatis convallis. Lectus placerat sit cursus parturient metus sagittis at mauris. Pharetra aliquam luctus ac fringilla ultrices."
            )
            }
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(weight = 1f, fill = true)
        )
        {
            /*
            Message(
                received = true,
                avatarUrl = "https://chat-sdk-demo-web.netlify.app/avatars/f/68.jpg",
                messageText = "Aliquam a magna arcu tellus pellentesque mi pellentesque. Feugiat et a eget rutrum leo in. Pretium cras amet consequat est metus sodales. Id phasellus habitant dignissim viverra. Nulla non faucibus mus scelerisque diam. Nulla a quis venenatis convallis. Lectus placerat sit cursus parturient metus sagittis at mauris. Pharetra aliquam luctus ac fringilla ultrices."
            )
            Message(received = false, messageText = "Augue sit et aenean non tortor senectus sed. Sagittis eget in ut magna semper urna felis velit cursus. Enim nunc leo quis volutpat dis.")

            Message(
                received = true,
                avatarUrl = "https://chat-sdk-demo-web.netlify.app/avatars/f/68.jpg",
                messageText = "Aliquam a magna arcu tellus pellentesque mi pellentesque. Feugiat et a eget rutrum leo in. Pretium cras amet consequat est metus sodales. Id phasellus habitant dignissim viverra. Nulla non faucibus mus scelerisque diam. Nulla a quis venenatis convallis. Lectus placerat sit cursus parturient metus sagittis at mauris. Pharetra aliquam luctus ac fringilla ultrices."
            )
            Message(received = false, messageText = "Augue sit et aenean non tortor senectus sed. Sagittis eget in ut magna semper urna felis velit cursus. Enim nunc leo quis volutpat dis.")
            if (typingMessageShown)
            {
                Spacer(Modifier.height(150.dp))
            }
            else
            {
                Spacer(Modifier.height(100.dp))
            }
*/


        }
    }

}