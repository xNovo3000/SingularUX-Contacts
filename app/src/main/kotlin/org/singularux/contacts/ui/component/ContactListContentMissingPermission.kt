package org.singularux.contacts.ui.component

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale
import org.singularux.contacts.R
import org.singularux.contacts.ui.theme.ContactsTheme

@ExperimentalPermissionsApi
@Composable
fun ContactListContentMissingPermission(
    readContactsPermissionState: PermissionState,
) {
    // Get the current context to build
    val context = LocalContext.current
    // Build
    Column(
        modifier = Modifier
            .systemBarsPadding()
            .padding(
                top = 64.dp,
                bottom = 88.dp,
                start = 24.dp,
                end = 24.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(96.dp),
            imageVector = Icons.Rounded.Block,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.contact_list_missing_permission_rationale),
            style = MaterialTheme.typography.bodyLarge,
        )
        TextButton(
            onClick = {
                if (readContactsPermissionState.status.shouldShowRationale) {
                    readContactsPermissionState.launchPermissionRequest()
                } else {
                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    })
                }
            }
        ) {
            Text(text = stringResource(id = R.string.contact_list_missing_permission_action))
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@ExperimentalPermissionsApi
@Composable
private fun Preview() {
    ContactsTheme {
        Surface {
            ContactListContentMissingPermission(
                readContactsPermissionState = object : PermissionState {
                    override val permission: String
                        get() = Manifest.permission.READ_CONTACTS
                    override val status: PermissionStatus
                        get() = PermissionStatus.Denied(shouldShowRationale = false)
                    override fun launchPermissionRequest() {}
                }
            )
        }
    }
}