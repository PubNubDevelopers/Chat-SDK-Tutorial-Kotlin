package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.avatarBaseUrl
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun HeaderPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        Header(chatLayout = false, title = "PubNub")
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview2() {
    PubNubKotlinChatSDKTutorialTheme {
        Header(
            chatLayout = true,
            title = "Sarah Johannsen",
            avatarUrl = avatarBaseUrl + "/group/globe1.png",
            avatarPresence = PresenceIndicator.ONLINE
        )
    }
}

@Composable
fun Header(
    chatLayout: Boolean = false,
    backAction: () -> Unit = {},
    title: String = "PubNub",
    avatarUrl: String = "",
    avatarPresence: PresenceIndicator = PresenceIndicator.NOT_SHOWN
) {
    val context = LocalContext.current
    PubNubKotlinChatSDKTutorialTheme {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Navy900)
                .padding(horizontal = 2.dp, vertical = 2.dp)
                .fillMaxWidth()
        ) {
            if (!chatLayout) {
                Surface(
                    color = Navy50, shape = RoundedCornerShape(50), modifier = Modifier
                        .height(65.dp)
                        .aspectRatio(1f)
                        .padding(all = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pn_logo_red_tint),
                        contentDescription = "PubNub Chat",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(5.dp)
                            .padding(all = 10.dp)
                            .aspectRatio(1f)

                    )

                }
                Text(
                    text = title,
                    modifier = Modifier
                        .height(65.dp)
                        .padding(start = 5.dp)
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    style = Typography.bodySmall,
                    color = Navy50
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(65.dp)
                        .aspectRatio(1f)
                        .padding(all = 15.dp)
                        .clickable {
                            backAction()
                        },
                    tint = Color.White
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 65.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Avatar(
                        url = avatarUrl,
                        present = avatarPresence,
                        size = AvatarSize.SMALL
                    )
                    Text(
                        text = title,
                        modifier = Modifier
                            .height(65.dp)
                            .padding(start = 10.dp)
                            .wrapContentHeight(align = Alignment.CenterVertically),
                        style = Typography.bodySmall,
                        color = Navy50
                    )
                }
            }


            Spacer(Modifier.weight(1f))
            if (!chatLayout) {
                Image(
                    painter = painterResource(id = R.drawable.notifications_none),
                    contentDescription = "Notifications",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 20.dp)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "Notifications not implemented",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                )
            }

        }
    }
}