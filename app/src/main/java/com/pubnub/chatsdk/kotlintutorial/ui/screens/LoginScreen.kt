package com.pubnub.chatsdk.kotlintutorial.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pubnub.chatsdk.kotlintutorial.R
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy600
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Navy900
import com.pubnub.chatsdk.kotlintutorial.ui.theme.PubNubKotlinChatSDKTutorialTheme
import com.pubnub.chatsdk.kotlintutorial.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    onLoginClicked: (userId: String) -> Unit = {}
) {
    var userId by remember { mutableStateOf("") }
    PubNubKotlinChatSDKTutorialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(color = Color.White)
                .consumeWindowInsets(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chat_logo),
                    contentDescription = "PubNub Chat",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.size(30.dp))
                Text(
                    text = "Log in: Sample Chat App",
                    style = Typography.titleLarge,
                    color = Navy900
                )
                Spacer(modifier = Modifier.size(20.dp))
                Text(
                    text = "Built with the\nPubNub Chat SDK for Kotlin",
                    style = Typography.labelLarge,
                    color = Navy600,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.size(60.dp))
                OutlinedTextField(
                    value = userId,
                    onValueChange = { userId = it },
                    singleLine = true,
                    label = { Text("Choose a User ID") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        cursorColor = Navy900,
                        focusedBorderColor = Navy900,
                        unfocusedBorderColor = Navy600
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onLoginClicked(userId) }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.size(45.dp))
                Button(
                    onClick = {
                        onLoginClicked(userId)
                    },
                    shape = RoundedCornerShape(20),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Navy900)
                ) { Text(text = "Log in", style = Typography.bodyMedium, color = Color.White) }

            }
        }
    }
}