package com.goldencrust.app.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import goldencrustapp.shared.generated.resources.Res
import goldencrustapp.shared.generated.resources.ic_moon
import goldencrustapp.shared.generated.resources.ic_sun
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppTopBar(
    isDark: Boolean,
    isUk: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))

    Box(modifier.fillMaxWidth().height(48.dp), contentAlignment = Alignment.Center) {
        OutlinedButton(
            onClick = onToggleLanguage,
            modifier = Modifier.align(Alignment.CenterStart).height(44.dp),
            shape = CircleShape,
            border = border,
            contentPadding = PaddingValues(horizontal = 18.dp),
        ) {
            Text(if (isUk) "UA" else "EN", style = MaterialTheme.typography.labelLarge)
        }

        GoldenCrustLogo(markHeight = 32.dp)

        OutlinedIconButton(
            onClick = onToggleTheme,
            modifier = Modifier.align(Alignment.CenterEnd).size(44.dp),
            shape = CircleShape,
            border = border,
        ) {
            Icon(
                painter = painterResource(if (isDark) Res.drawable.ic_sun else Res.drawable.ic_moon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp),
            )
        }
    }
}