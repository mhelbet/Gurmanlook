package com.example.gurmanlookapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.gurmanlookapp.R
import com.example.gurmanlookapp.ui.theme.vanilla

@Composable
fun InfoView(modifier: Modifier = Modifier) {
   Box(modifier = Modifier
       .fillMaxSize()
       .background(vanilla),
           contentAlignment = Alignment.Center) {
       Column(
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Text(text = stringResource(R.string.naziv_projekta_gurmanlook), color = Color.Black)
       Text(text = stringResource(R.string.profesor_elmedin_selmanovi), color = Color.Black)
       Text(text = stringResource(R.string.studenti_nejra_pribinja_i_majda_helbet), color = Color.Black)
   }
   }
}
