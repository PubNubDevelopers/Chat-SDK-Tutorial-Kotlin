package com.pubnub.chatsdk.kotlintutorial.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chat.Channel
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy200
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral300
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Neutral600
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme

@Preview(showBackground = true)
@Composable
fun MessageInputPreview1() {
    PubNubKotlinChatSDKTutorialTheme {
        MessageInput(activeChannel = null, sendMessage = {}, startTyping = {})
    }
}


@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    activeChannel: Channel?,
    sendMessage: (messageText: String) -> Unit,
    startTyping: () -> Unit
) {
    var messageText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    PubNubKotlinChatSDKTutorialTheme {
        Column(
            modifier = modifier
        ) {
            HorizontalDivider(color = Navy200, thickness = 1.dp)

            activeChannel?.let { TypingIndicator(it) }

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .height(68.dp)
                    .fillMaxWidth()

            )

            {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it; startTyping() },
                    singleLine = true,
                    label = { Text("Type Message") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        cursorColor = Navy900,
                        focusedBorderColor = Neutral600,
                        unfocusedBorderColor = Neutral300
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            sendMessage(messageText)
                            messageText = ""
                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }
        }


    }
}