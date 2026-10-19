package com.goldencrust.app.ui.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import goldencrustapp.shared.generated.resources.Res
import goldencrustapp.shared.generated.resources.*
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val log = Logger.withTag("Onboarding")

private data class OnboardingPage(
    val image: DrawableResource,
    val title: StringResource,
    val description: StringResource,
)

private val pages = listOf(
    OnboardingPage(Res.drawable.onboarding_1, Res.string.onboarding_title_1, Res.string.onboarding_desc_1),
    OnboardingPage(Res.drawable.onboarding_2, Res.string.onboarding_title_2, Res.string.onboarding_desc_2),
    OnboardingPage(Res.drawable.onboarding_3, Res.string.onboarding_title_3, Res.string.onboarding_desc_3),
    OnboardingPage(Res.drawable.onboarding_4, Res.string.onboarding_title_4, Res.string.onboarding_desc_4),
)

@Composable
fun OnboardingScreen(
    initialPage: Int,
    onPageChange: (Int) -> Unit,
    isDark: Boolean,
    isUk: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onFinish: () -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { pages.size },
    )
    val scope = rememberCoroutineScope()
    val isLast = pagerState.currentPage == pages.lastIndex

    LaunchedEffect(pagerState.currentPage) {
        log.d { "Page changed to ${pagerState.currentPage}" }
        onPageChange(pagerState.currentPage)
    }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Box(Modifier.safeDrawingPadding(), contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier.widthIn(max = 480.dp).fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBar(
                    isDark = isDark,
                    isUk = isUk,
                    onToggleTheme = onToggleTheme,
                    onToggleLanguage = onToggleLanguage,
                )

                HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
                    PageContent(pages[index])
                }

                PageIndicator(pagerState)
                Spacer(Modifier.height(24.dp))

                if (isLast) {
                    Button(onClick = onFinish, modifier = Modifier.fillMaxWidth().height(52.dp)) {
                        Text(stringResource(Res.string.onboarding_start), style = MaterialTheme.typography.labelLarge)
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TextButton(onClick = onFinish) {
                            Text(stringResource(Res.string.onboarding_skip))
                        }
                        Button(onClick = {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        }) {
                            Text(stringResource(Res.string.onboarding_next))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(MaterialTheme.shapes.large),
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun PageIndicator(state: PagerState) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
        repeat(state.pageCount) { i ->
            val selected = state.currentPage == i
            val width by animateDpAsState(if (selected) 24.dp else 8.dp)
            val color by animateColorAsState(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
            Box(Modifier.height(8.dp).width(width).clip(CircleShape).background(color))
        }
    }
}

@Composable
private fun TopBar(
    isDark: Boolean,
    isUk: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
) {
    Box(Modifier.fillMaxWidth().height(48.dp), contentAlignment = Alignment.Center) {
        TextButton(onClick = onToggleLanguage, modifier = Modifier.align(Alignment.CenterStart)) {
            Text(if (isUk) "UA" else "EN", style = MaterialTheme.typography.labelLarge)
        }
        Image(
            painter = painterResource(Res.drawable.logo),
            contentDescription = "Golden Crust",
            modifier = Modifier.height(36.dp),
        )
        TextButton(onClick = onToggleTheme, modifier = Modifier.align(Alignment.CenterEnd)) {
            Text(if (isDark) "☀" else "🌙")
        }
    }
}