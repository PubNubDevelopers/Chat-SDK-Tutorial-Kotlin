package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.components.Avatar
import com.pubnub.chatsdk.kotlintutorial.ui.components.AvatarSize
import com.pubnub.chatsdk.kotlintutorial.ui.components.NavIcon
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy200
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy500
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy600
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy800
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun NavBar() {
    PubNubKotlinChatSDKTutorialTheme {

        //Spacer(Modifier.weight(1f))
        HorizontalDivider(color = Navy200, thickness = 1.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .background(color = Navy50)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .height(68.dp)
                .fillMaxWidth()
        ) {
            NavIcon("Home", R.drawable.home, true, isImplemented = true)
            NavIcon("People", R.drawable.people_outline, false, isImplemented = false)
            NavIcon("Mentions", R.drawable.alternate_email, false, isImplemented = false)
            NavIcon("Profile", R.drawable.person, false, isImplemented = true)
        }
    }

}
