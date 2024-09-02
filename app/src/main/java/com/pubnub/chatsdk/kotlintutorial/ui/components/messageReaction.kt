package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.EmojiSupportMatch
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Message
import com.pubnub.chatsdk.kotlintutorial.await
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral200
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography
import kotlinx.coroutines.launch

/*
@Preview(showBackground = true)
@Composable
fun MessageReactionPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageReaction(emoji = "\uD83E\uDEE0", count = 1)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageReactionPreview2() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageReaction(emoji = "\uD83E\uDEE0", count = 0)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageReactionPreview3() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageReaction(
            emoji = "\uD83E\uDEF1\uD83C\uDFFC\u200D\uD83E\uDEF2\uD83C\uDFFF",
            count = 500
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageReactionPreview4() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageReaction(emoji = "\uD83E\uDEF0\uD83C\uDFFD", count = 3)
    }
}

 */

@Composable
fun MessageReaction(
    message: Message?,
    emoji: String = "",
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    //val count = remember { mutableStateOf(0) }
    //Log.d("LOGD", "Count1: " + (message?.actions?.get("reactions")?.get(emoji)?.size ?: 0))
    val count = (message?.actions?.get("reactions")?.get(emoji)?.size ?: 0)
    //Log.d("LOGD", "Count2: " + (message?.actions?.get("reactions")?.size ?: 0))
    //Log.d("LOGD", "Count3: " + (message?.actions?.get("reactions")))
    //Log.d("LOGD", "Count4: " + (message?.actions?.get("reactions")?.get(emoji)))
    Row(
        modifier = Modifier
            .border(
                1.dp,
                Neutral200,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Neutral50)
            .padding(horizontal = 6.dp, vertical = 3.dp).clickable {
                scope.launch {
                    message?.toggleReaction(emoji)?.await()
                }
                //Toast
                //    .makeText(
                //        context,
                //        "React to Message",
                //        Toast.LENGTH_SHORT
                //    )
                //    .show()
            },

        )
    {

        Row(

            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = emoji,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        emojiSupportMatch = EmojiSupportMatch.None
                    )
                ),
            )
            Text(
                text = if (count > 0) {
                    "" + count
                } else "",
                style = Typography.labelSmall,
            )
        }

    }

}