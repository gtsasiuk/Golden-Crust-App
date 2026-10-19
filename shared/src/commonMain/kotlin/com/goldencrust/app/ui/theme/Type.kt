package com.goldencrust.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import goldencrustapp.shared.generated.resources.Manrope_Medium
import goldencrustapp.shared.generated.resources.Manrope_Regular
import goldencrustapp.shared.generated.resources.Manrope_SemiBold
import goldencrustapp.shared.generated.resources.PlayfairDisplay_Bold
import goldencrustapp.shared.generated.resources.PlayfairDisplay_SemiBold
import goldencrustapp.shared.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun goldenCrustTypography(): Typography {
    val headline = FontFamily(
        Font(Res.font.PlayfairDisplay_SemiBold, FontWeight.SemiBold),
        Font(Res.font.PlayfairDisplay_Bold, FontWeight.Bold),
    )
    val body = FontFamily(
        Font(Res.font.Manrope_Regular, FontWeight.Normal),
        Font(Res.font.Manrope_Medium, FontWeight.Medium),
        Font(Res.font.Manrope_SemiBold, FontWeight.SemiBold),
    )
    return Typography(
        headlineLarge = TextStyle(fontFamily = headline, fontWeight = FontWeight.Bold, fontSize = 32.sp, lineHeight = 40.sp),
        headlineMedium = TextStyle(fontFamily = headline, fontWeight = FontWeight.SemiBold, fontSize = 26.sp, lineHeight = 34.sp),
        titleMedium = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 24.sp),
        bodyLarge = TextStyle(fontFamily = body, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
        bodyMedium = TextStyle(fontFamily = body, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
        labelLarge = TextStyle(fontFamily = body, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
    )
}