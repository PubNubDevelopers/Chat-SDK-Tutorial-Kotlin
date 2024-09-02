package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.User
import com.pubnub.chat.types.ChannelType
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.await
import com.pubnub.chatsdk.kotlintutorial.ui.theme.IconDeselected
import com.pubnub.chatsdk.kotlintutorial.ui.theme.IconSelected
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography


/*
@Preview(showBackground = true)
@Composable
fun TypingIndicatorPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        TypingIndicator(channel = null)
    }
}

 */


@Composable
fun TypingIndicator(channel: Channel
) {
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
            //  This function could be improved by not displaying the message if the current
            //  user is typing, as well as retrieving the user name and avatar from some cached storage
            //Text("Typing: " + typingIndicators.joinToString(", "))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Avatar(
                    url = "https://chat-sdk-demo-web.netlify.app/avatars/placeholder2.png",
                    present = PresenceIndicator.ONLINE,
                    size = AvatarSize.SMALL
                )
                Text(
                    text = "Typing: " + typingIndicators.joinToString(", "),
                    modifier = Modifier
                        .padding(start = 5.dp),
                    style = Typography.labelSmall,
                )
            }
        }
    }
/*    PubNubKotlinChatSDKTutorialTheme {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp).padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Avatar(
                url = "https://chat-sdk-demo-web.netlify.app/avatars/f/65.jpg",
                present = PresenceIndicator.ONLINE,
                size = AvatarSize.SMALL
            )
            Text(
                text = "Typing...",
                modifier = Modifier
                    .padding(start = 5.dp),
                style = Typography.labelSmall,
            )
        }
    }*/
}