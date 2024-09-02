package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.User
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Composable
fun ChatMenuItem(
    avatarUrl: String? = "",
    chatTitle: String? = "Default Chat Title",
    channelSelected: (selectedChannel: Channel) -> Unit,
    userSelected: (selectedUser: User) -> Unit,
    predefinedChannel: Channel? = null,
    predefinedUser: User? = null
) {
    PubNubKotlinChatSDKTutorialTheme {
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 8.dp)
                .fillMaxWidth()
                .clickable {
                    if (predefinedChannel != null) {
                        channelSelected(predefinedChannel)
                    } else if (predefinedUser != null) {
                        userSelected(predefinedUser)
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Avatar(
                url = avatarUrl,
                present = PresenceIndicator.NOT_SHOWN,
                size = AvatarSize.SMALL
            )
            Text(
                text = chatTitle ?: "",
                style = Typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )

        }
    }

}
