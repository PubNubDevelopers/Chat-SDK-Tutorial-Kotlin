package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.avatarBaseUrl
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral300
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.StatusIndicatorSuccess

@Preview(showBackground = true)
@Composable
fun AvatarPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        Avatar("", PresenceIndicator.NOT_SHOWN, AvatarSize.SMALL)
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarPreview2() {
    PubNubKotlinChatSDKTutorialTheme {
        Avatar("$avatarBaseUrl/avatars/avatar01.png", PresenceIndicator.ONLINE, AvatarSize.SMALL)
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarPreview3() {
    PubNubKotlinChatSDKTutorialTheme {
        Avatar("$avatarBaseUrl/avatars/m/65.jpg", PresenceIndicator.OFFLINE, AvatarSize.BIG)
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarPreview4() {
    PubNubKotlinChatSDKTutorialTheme {
        Avatar("$avatarBaseUrl/avatars/f/65.jpg", PresenceIndicator.ONLINE, AvatarSize.BIG)
    }
}

enum class AvatarSize {
    SMALL, BIG
}

enum class PresenceIndicator {
    NOT_SHOWN, OFFLINE, ONLINE
}

@Composable
fun Avatar(
    url: String?,
    present: PresenceIndicator,
    size: AvatarSize,
    editIcon: Boolean = false,
    editActionHandler: () -> Unit = {}

) {
    var imageUrl = url
    var avatarSize = 32.dp
    var indicatorSize = 12.dp
    var indicatorBorderSize = 1.dp
    var presenceColour = StatusIndicatorSuccess
    if (size == AvatarSize.BIG) {
        avatarSize = 64.dp
        indicatorSize = 24.dp
        indicatorBorderSize = 3.dp
    }
    if (present == PresenceIndicator.OFFLINE) {
        presenceColour = Neutral300
    }
    if (imageUrl == "")
    {
        imageUrl = avatarBaseUrl + "/avatars/placeholder2.png"
    }
    Box()
    {
        AsyncImage(
            model = imageUrl,
            placeholder = painterResource(id = R.drawable.account_circle),
            contentDescription = "User Avatar",
            modifier = Modifier
                .clip(CircleShape)
                .size(avatarSize)
        )
        if (present != PresenceIndicator.NOT_SHOWN) {
            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(CircleShape)
                    .background(presenceColour)
                    .align(Alignment.BottomEnd)
                    .border(indicatorBorderSize, Color.White, CircleShape)
                    .padding(indicatorBorderSize)
            )
        }
    }

}