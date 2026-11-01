package com.goldencrust.app.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import goldencrustapp.shared.generated.resources.Res
import goldencrustapp.shared.generated.resources.golden_crust_logo
import goldencrustapp.shared.generated.resources.golden_crust_logo_dark
import goldencrustapp.shared.generated.resources.logo_tagline
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun GoldenCrustLogo(
    modifier: Modifier = Modifier,
    markHeight: Dp = 36.dp,
) {
    val isDark = MaterialTheme.colorScheme.background.luminance() < 0.5f

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Image(
            painter = painterResource(
                if (isDark) Res.drawable.golden_crust_logo_dark else Res.drawable.golden_crust_logo
            ),
            contentDescription = null,
            modifier = Modifier.height(markHeight),
        )
        Column {
            Text(
                text = "Golden Crust",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp, lineHeight = 22.sp),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(Res.string.logo_tagline),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 9.sp,
                    lineHeight = 12.sp,
                    letterSpacing = 1.sp,
                ),
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}