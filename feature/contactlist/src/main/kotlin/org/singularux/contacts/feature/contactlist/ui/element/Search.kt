package org.singularux.contacts.feature.contactlist.ui.element

import android.content.res.Configuration
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarState
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.singularux.contacts.core.ui.ContactsTheme
import org.singularux.contacts.feature.contactlist.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IconButtonWithTooltip(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onClick: () -> Unit
) {
    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
            positioning = TooltipAnchorPosition.Below
        ),
        state = rememberTooltipState(),
        tooltip = {
            PlainTooltip { Text(text = text) }
        }
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painter,
                contentDescription = text
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarInput(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    searchBarState: SearchBarState
) {
    val coroutineScope = rememberCoroutineScope()
    val maybeSoftwareKeyboardController = LocalSoftwareKeyboardController.current
    val searchHintText = stringResource(R.string.search_hint)
    SearchBarDefaults.InputField(
        modifier = modifier,
        textFieldState = textFieldState,
        searchBarState = searchBarState,
        onSearch = { maybeSoftwareKeyboardController?.hide() },
        leadingIcon = {
            if (searchBarState.currentValue == SearchBarValue.Expanded) {
                IconButtonWithTooltip(
                    text = stringResource(R.string.search_action_back),
                    painter = painterResource(R.drawable.arrow_back_24dp),
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd("")
                        coroutineScope.launch { searchBarState.animateToCollapsed() }
                    }
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.search_24dp),
                    contentDescription = searchHintText
                )
            }
        },
        placeholder = {
            Text(text = searchHintText)
        },
        trailingIcon = {
            if (searchBarState.currentValue == SearchBarValue.Expanded) {
                IconButtonWithTooltip(
                    text = stringResource(R.string.search_action_clear),
                    painter = painterResource(R.drawable.close_24dp),
                    onClick = {
                        textFieldState.setTextAndPlaceCursorAtEnd("")
                    }
                )
            }
        }
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Preview() {
    ContactsTheme {
        val state = rememberSearchBarState()
        val inputField = @Composable {
            SearchBarInput(
                textFieldState = rememberTextFieldState(),
                searchBarState = state
            )
        }
        SearchBar(
            inputField = inputField,
            state = state
        )
    }
}