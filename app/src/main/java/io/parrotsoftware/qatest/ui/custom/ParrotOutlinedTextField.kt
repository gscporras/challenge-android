package io.parrotsoftware.qatest.ui.custom

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import io.parrotsoftware.qatest.R

@Composable
@Preview
fun ParrotOutlinedTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String?> = mutableStateOf(""),
    isPasswordTextField: Boolean = false,
    passwordVisibility: MutableState<Boolean> = mutableStateOf(false),
    onNext: () -> Unit = {},
    onGo: () -> Unit = {}
) {
    if(isPasswordTextField) {
        OutlinedTextField(
            modifier = modifier,
            label = {
                Text(
                    text = stringResource(id = R.string.hint_password),
                    style = MaterialTheme.typography.body1
                )
            },
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.onSurface,
                unfocusedLabelColor = Color.Gray,
                cursorColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
            trailingIcon = {
                val image = if (passwordVisibility.value)
                    Icons.Rounded.Visibility
                else Icons.Rounded.VisibilityOff

                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector  = image,
                        "",
                        tint = Color.Gray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = { onGo() }),
            value = value.value ?: "",
            onValueChange = { value.value = it },
        )
    } else {
        OutlinedTextField(
            modifier = modifier,
            label = {
                Text(
                    text = stringResource(id = R.string.hint_email),
                    style = MaterialTheme.typography.body1
                )
            },
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onSurface,
                unfocusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.onSurface,
                unfocusedLabelColor = Color.Gray,
                cursorColor = MaterialTheme.colors.secondary,
                textColor = MaterialTheme.colors.primary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { onNext() }),
            value = value.value ?: "",
            onValueChange = { value.value = it },
        )
    }
}