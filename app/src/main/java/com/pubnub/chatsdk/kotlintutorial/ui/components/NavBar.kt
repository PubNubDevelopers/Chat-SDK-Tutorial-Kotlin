package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy200
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme

@Preview(showBackground = true)
@Composable
fun NavBar(logout: () -> Unit = {}) {
    val context = LocalContext.current
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
            NavIcon("Home", R.drawable.home, true, clickAction = {})
            NavIcon("People", R.drawable.people_outline, false, clickAction = {
                Toast
                    .makeText(
                        context,
                        "Not implemented",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            })
            NavIcon("Mentions", R.drawable.alternate_email, false, clickAction = {
                Toast
                    .makeText(
                        context,
                        "Not implemented",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
            )
            NavIcon("Log Out", R.drawable.exit, false, clickAction = {
                logout()
            })
        }
    }

}
