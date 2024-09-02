package com.pubnub.chatsdk.kotlintutorial.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chat.Chat
import com.pubnub.chat.Message
import com.pubnub.chat.types.ChannelType
import com.pubnub.chatsdk.kotlintutorial.ui.testdata.avatarBaseUrl
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral50
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Teal100
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Composable
fun Message(
    chat: Chat?,
    activeChannel: Channel?,
    message: Message?,
    readReceipts: Map<Long, List<String>>,
    received: Boolean,
    presenceIndicator: PresenceIndicator = PresenceIndicator.ONLINE,
) {
    val avatarUrl =
        remember { mutableStateOf("$avatarBaseUrl/avatars/placeholder2.png") }
    val senderName = remember { mutableStateOf("") }
    //  Determine whether the message has been read
    var isRead = false;
    if (activeChannel?.type != ChannelType.PUBLIC) {
        readReceipts.forEach { (messageTimetoken, users) ->
            //Log.d("LOGD", "Message Timetoken: $messageTimetoken was read by users: $users")
            if (messageTimetoken >= message?.timetoken!!) {
                if ((users.isNotEmpty() && !users.contains(chat?.currentUser?.id)) || users.size > 1) {
                    isRead = true
                }
            }
        }
    }
    Log.d(
        "LOGD",
        "IsRead for Message " + message?.text + "(" + message?.timetoken + ") is " + isRead
    )
    LaunchedEffect(message?.userId) {
        chat?.getUser(message!!.userId)?.async { result ->
            result.onSuccess {
                if (it?.profileUrl != null) {
                    avatarUrl.value = it?.profileUrl.toString()
                }
                if (it?.name != null) {
                    senderName.value = it?.name.toString()
                }

            }
        }
    }
    if (received) {
        Row(modifier = Modifier.padding(start = 16.dp, end = 30.dp, top = 20.dp))
        {
            Avatar(
                url = avatarUrl.value,
                present = presenceIndicator,
                size = AvatarSize.SMALL
            )

            Column()
            {
                Text(
                    text = senderName.value,
                    modifier = Modifier
                        .padding(start = 5.dp, bottom = 5.dp),
                    style = Typography.labelSmall,
                )
                Box(
                    modifier = Modifier
                        .padding()
                )
                {

                    Box(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .defaultMinSize(minWidth = 100.dp)
                            .clip(RoundedCornerShape(0.dp, 12.dp, 12.dp, 12.dp))
                            .background(
                                Neutral50
                            )
                            .padding(8.dp)
                    )
                    {
                        Text(
                            text = message?.text ?: "Unspecified",
                            modifier = Modifier
                                .padding(start = 5.dp, bottom = 28.dp),
                            style = Typography.bodySmall,
                        )
                    }
                    //  Area for Emoji

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 5.dp, bottom = 3.dp),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                    )
                    {
                        MessageReaction(message = message, emoji = "\uD83D\uDE00")
                        MessageReaction(message = message, emoji = "\uD83E\uDD76")


                    }
                }
            }

        }
    } else {
        //  Sent message
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 78.dp, end = 16.dp, top = 20.dp))
        {
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .padding()
            )
            {
                Box(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 120.dp)
                        .clip(RoundedCornerShape(12.dp, 0.dp, 12.dp, 12.dp))
                        .background(
                            Teal100
                        )
                        .padding(8.dp)
                )
                {
                    Text(
                        text = message?.text ?: "Unspecified",
                        modifier = Modifier
                            .padding(start = 5.dp, bottom = 28.dp),
                        style = Typography.bodySmall,
                    )

                }
                //  Area for Sent / Read receipts as well as Emoji

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 5.dp, bottom = 3.dp),
                    horizontalArrangement = Arrangement.spacedBy(1.dp),
                )
                {

                    //Log.d("LOGD", "message actions size: " + message?.actions?.size)
                    MessageReaction(message = message, emoji = "\uD83D\uDE00")
                    MessageReaction(message = message, emoji = "\uD83E\uDD76")
                    if (isRead && activeChannel?.type != ChannelType.PUBLIC) {
                        MessageReadReceipt(status = MessageReadReceiptStatus.READ)
                    } else if (activeChannel?.type != ChannelType.PUBLIC) {
                        MessageReadReceipt(status = MessageReadReceiptStatus.SENT)
                    }

                }
            }
        }

    }


}