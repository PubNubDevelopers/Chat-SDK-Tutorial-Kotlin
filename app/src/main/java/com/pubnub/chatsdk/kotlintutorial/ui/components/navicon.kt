package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Chat
import com.pubnub.chat.config.ChatConfiguration
import com.pubnub.chat.config.LogLevel
import com.pubnub.chatsdk.kotlintutorial.Greeting
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.await
import com.pubnub.chatsdk.kotlintutorial.createChat
import com.pubnub.chatsdk.kotlintutorial.ui.theme.IconDeselected
import com.pubnub.chatsdk.kotlintutorial.ui.theme.IconSelected
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy500
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy600
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral600
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun NavIconPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        NavIcon("Home", R.drawable.home, true)
    }
}

@Preview(showBackground = true)
@Composable
fun NavIconPreview2() {
    PubNubKotlinChatSDKTutorialTheme {
        NavIcon("People", R.drawable.people_outline, false)
    }
}

@Preview(showBackground = true)
@Composable
fun NavIconPreview3() {
    PubNubKotlinChatSDKTutorialTheme {
        NavIcon("Mentions", R.drawable.alternate_email, true)
    }
}

@Preview(showBackground = true)
@Composable
fun NavIconPreview4() {
    PubNubKotlinChatSDKTutorialTheme {
        NavIcon("Profile", R.drawable.person, false)
    }
}

@Composable
fun NavIcon(label: String, icon: Int, isHighlighted: Boolean, isImplemented: Boolean = true) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = Navy50)
            .height(64.dp)
            .width(width = 74.dp).clickable {
                if (!isImplemented)
                {
                    Toast
                        .makeText(
                            context,
                            "Not implemented",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Icon $label",
            modifier = Modifier
                .size(30.dp)
                .aspectRatio(1f),
            tint= (if (isHighlighted) IconSelected else IconDeselected)
        )
        Text(
            text = "$label",
            style = Typography.labelSmall,
            color = (if (isHighlighted)  Neutral900 else Neutral600),
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f).padding(top = 5.dp)
        )
        if (isHighlighted)
        {
            HorizontalDivider(color = Navy500, thickness = 1.dp)
        }
    }
}