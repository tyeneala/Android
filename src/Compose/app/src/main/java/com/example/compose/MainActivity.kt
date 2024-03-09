package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.modules.Password
import com.example.compose.viewmodels.MyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val clipboard: ClipboardManager = LocalClipboardManager.current
    val model: MyViewModel = viewModel()
    val uiState = model.state.collectAsState()
    var domainName by remember { mutableStateOf("") }
    var keyword by remember { mutableStateOf("") }

    Column {
        Text(
            text = "Password Generator v.1.2024",
            modifier = Modifier.padding(bottom = 32.dp, top = 16.dp, start = 32.dp),
        )

        Text(
            text = "Enter or paste domain name:",
            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp)
        )

        Row(modifier = Modifier.padding(bottom = 16.dp)){
            OutlinedTextField(
                value = domainName,
                onValueChange = { domainName = it },
                isError = !Password.doCheckDomainName(domainName),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(200.dp, 50.dp)
            )
            IconButton(onClick = {
                clipboard.getText()?.text?.let { domainName = it }
            },
                modifier = Modifier.padding(start = 5.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10))) {
                Icon(painterResource(id = R.drawable.baseline_content_paste_black_24dp)
                    , contentDescription = "Paste")
            }
            IconButton(
                onClick = { domainName = "" },
                modifier = Modifier.padding(start = 5.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10))) {
                Icon(painterResource(id = R.drawable.baseline_clear_black_24dp),
                    contentDescription = "Clear")
            }
        }

        Text(
            text = "Enter your keyword:",
            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp)
        )

        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            OutlinedTextField(
                value = keyword,
                onValueChange = { keyword = it },
                singleLine = true,
                isError = !Password.doCheckKeyWord(keyword),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(200.dp, 50.dp)
            )

            IconButton(
                onClick = { keyword = "" },
                modifier = Modifier.padding(start = 5.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10))) {
                Icon(painterResource(id = R.drawable.baseline_clear_black_24dp),
                    contentDescription = "Clear")
            }
        }

        Text(
            text = "Your password:",
            modifier = Modifier.padding(bottom = 16.dp, start = 32.dp)
        )

        Row(modifier = Modifier.padding(bottom = 16.dp)) {
            Text(
                text = uiState.value.password,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(200.dp, 50.dp)
            )
            IconButton(onClick = {
                clipboard.setText(AnnotatedString(uiState.value.password)) },
                modifier = Modifier.padding(start = 5.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(10))) {
                Icon(painterResource(id = R.drawable.baseline_content_copy_black_24dp)
                    , contentDescription = "Copy")
            }
        }


        Button(onClick = { model.doPassword(domainName, keyword) },
            modifier = Modifier.padding(start = 16.dp)) {
            Text(text = "Generate")
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        Greeting()
    }
}
