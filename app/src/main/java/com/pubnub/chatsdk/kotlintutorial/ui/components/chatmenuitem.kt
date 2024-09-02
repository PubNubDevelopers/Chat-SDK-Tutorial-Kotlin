package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.User
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy200
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

//@Preview(showBackground = true)
@Composable
fun ChatMenuItem(
    avatarUrl: String? = "",
    chatTitle: String? = "Default Chat Title",
    channelSelected: (selectedChannel: Channel) -> Unit,
    userSelected: (selectedUser: User) -> Unit,
    predefinedChannel: Channel? = null,
    predefinedUser: User? = null
) {
    val context = LocalContext.current
    PubNubKotlinChatSDKTutorialTheme {

        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 8.dp)
                .fillMaxWidth().clickable {
                    if (predefinedChannel != null) {
                        channelSelected(predefinedChannel)
                    }
                    else if (predefinedUser != null) {
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
