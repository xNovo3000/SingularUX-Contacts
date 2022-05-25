package org.singularux.contacts.ui.route

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.singularux.contacts.ui.ContactsRoute

@ExperimentalMaterial3Api
@Composable
fun SearchRoute(navController: NavHostController) {
    Scaffold {
        Box(
            modifier = Modifier.padding(it),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(onClick = { navController.popBackStack() }) {
                Text(text = "Go back")
            }
        }
    }
}