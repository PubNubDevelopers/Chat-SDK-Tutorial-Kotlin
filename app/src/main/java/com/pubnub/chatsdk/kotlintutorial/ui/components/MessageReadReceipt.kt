package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme

@Preview(showBackground = true)
@Composable
fun MessageReadReceiptsPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageReadReceipt(status = MessageReadReceiptStatus.SENT)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageReadReceiptsPreview2() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageReadReceipt(status = MessageReadReceiptStatus.READ)
    }
}

enum class MessageReadReceiptStatus {
    SENT, READ
}

@Composable
fun MessageReadReceipt(
    status: MessageReadReceiptStatus
) {
    Icon(
        painter = if (status == MessageReadReceiptStatus.SENT) {
            painterResource(id = R.drawable.sent)}
        else {
            painterResource(id = R.drawable.read)},
        contentDescription = "Read Receipts",
        modifier = Modifier
            .height(24.dp)
            .width(24.dp)
            .padding(start = 5.dp)
    )


}