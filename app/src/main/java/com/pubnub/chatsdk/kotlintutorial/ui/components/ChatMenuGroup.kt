package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.User
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy200
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Composable
fun ChatMenuGroup(
    chat: Chat?,
    groupName: String = "Default Group",
    channels: List<Channel>? = null,
    users: List<User>? = null,
    actionIconShown: Boolean = false,
    channelSelected: (selectedChannel: Channel) -> Unit,
    userSelected: (selectedUser: User) -> Unit
) {
    val context = LocalContext.current
    PubNubKotlinChatSDKTutorialTheme {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        )
        {
            HorizontalDivider(color = Navy200, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = groupName,
                    style = Typography.bodyMedium
                )
                if (actionIconShown) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Add",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                            .padding(all = 5.dp)
                            .clickable {
                                Toast
                                    .makeText(
                                        context,
                                        "Not implemented",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                    )
                }

            }

            channels?.forEach {
                //  For direct chats and public chats we know the channel objects
                ChatMenuItem(
                    chatTitle = it.name,
                    avatarUrl = it.custom?.get("profileUrl").toString(),
                    predefinedChannel = it,
                    channelSelected = channelSelected,
                    userSelected = userSelected
                )
            }
            users?.forEach {
                //  For users, we'll create the channel when we want to speak with someone
                if (it.id != chat?.currentUser?.id && it.id != "PUBNUB_INTERNAL_MODERATOR")
                    ChatMenuItem(
                        chatTitle = it.name,
                        avatarUrl = it.profileUrl,
                        predefinedUser = it,
                        channelSelected = channelSelected,
                        userSelected = userSelected
                    )
            }

        }


    }
}