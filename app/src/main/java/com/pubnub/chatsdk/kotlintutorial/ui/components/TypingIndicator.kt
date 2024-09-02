package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.types.ChannelType
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.avatarBaseUrl
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography


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
            //  SUGGESTED IMPROVEMENT:
            //  This function could be improved by not displaying the message if the current
            //  user is typing, as well as retrieving the user name and avatar from some cached storage
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Avatar(
                    url = "$avatarBaseUrl/avatars/placeholder2.png",
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
}
