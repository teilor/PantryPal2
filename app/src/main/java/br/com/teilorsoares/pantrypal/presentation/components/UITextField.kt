package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown300
import br.com.teilorsoares.pantrypal.presentation.theme.Brown350
import br.com.teilorsoares.pantrypal.presentation.theme.Brown500
import br.com.teilorsoares.pantrypal.presentation.util.clickableWithoutRipple

@Composable
fun UITextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done
    ),
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus(force = true)
                softwareKeyboardController?.hide()
            }
        ),
        decorationBox = { innerTextField ->
            Column {
                Text(
                    text = label,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = Brown350
                    ),
                    modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
                )

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Brown500,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .fillMaxWidth()
                        .background(
                            color = Brown1300,
                            shape = RoundedCornerShape(size = 4.dp)
                        )
                        .clickableWithoutRipple(
                            onClick = {
                                focusManager.clearFocus(force = true)
                                focusRequester.requestFocus()
                            }
                        )
                        .padding(start = 12.dp)
                        .padding(vertical = 8.dp)
                ) {
                    innerTextField()
                }
            }
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight(400),
            color = Brown300,
        ),
        modifier = modifier.focusRequester(focusRequester)
    )
}