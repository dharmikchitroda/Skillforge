package com.example.assignmentskillforge

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignmentskillforge.ui.HomeViewModel
import com.example.assignmentskillforge.ui.HomeUiState
import coil.compose.AsyncImage


val TextBlack = Color(0xFF111111)
val BrandTeal = Color(0xFF009688)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val categoryScrollState = rememberScrollState() // for horizontal scroll
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .statusBarsPadding() // auto add padding acroding statubar
            .navigationBarsPadding()  // auto add bottom - padding acroding bottombar
    ) {
        when (val state = uiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = BrandTeal)
                }
            }
            is HomeUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = state.message,
                        color = Color.Red,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadData("") },
                        colors = ButtonDefaults.buttonColors(containerColor = BrandTeal)
                    ) {
                        Text("Retry", color = Color.White)
                    }
                }
            }
            is HomeUiState.Success -> {
                val categories = state.data.categories
                val courses = remember(categories) { categories.flatMap { it.courses } }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    // Header topbar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Welcome back",
                                color = Color(0xFF8E8E93),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Find your next skill",
                                color = TextBlack,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.5).sp
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Notification Icon
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, Color(0xFFEEEEEE), CircleShape)
                                    .clickable {},
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications icon",
                                    tint = TextBlack,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            // Profile Icon
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF009688)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "AS",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = (-0.5).sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Search Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.White)
                            .border(1.dp, Color(0xFFE5E5EA), RoundedCornerShape(28.dp))
                            .clickable { }
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF8E8E93),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Search courses, topics...",
                                color = Color(0xFF8E8E93),
                                fontSize = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Categories Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Categories",
                            color = TextBlack,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "See all",
                            color = BrandTeal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Categories Horizontal List — overscroll glow disabled
                    @OptIn(ExperimentalFoundationApi::class)
                    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(categoryScrollState),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            categories.forEach { category ->
                                CategoryCard(category = category)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Simple scroll indicator inline
                    val maxScroll = if (categoryScrollState.maxValue > 0) categoryScrollState.maxValue.toFloat() else 1f
                    val scrollFraction = categoryScrollState.value.toFloat() / maxScroll
                    val animatedFraction = animateFloatAsState(
                        targetValue = scrollFraction,
                        label = "scrollFraction"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .height(4.dp)
                    ) {
                        // Light grey track
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFE0E0E0))
                        )
                        // Darker thumb
                        val f = animatedFraction.value.coerceIn(0f, 1f)
                        val leadingWeight  = f * 0.6f
                        val trailingWeight = (1f - f) * 0.6f
                        Row(modifier = Modifier.fillMaxWidth()) {
                            if (leadingWeight > 0f)  Spacer(modifier = Modifier.weight(leadingWeight))
                            Box(
                                modifier = Modifier
                                    .weight(0.4f)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(Color(0xFF9E9E9E))
                            )
                            if (trailingWeight > 0f) Spacer(modifier = Modifier.weight(trailingWeight))
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Popular Courses Section Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Popular courses",
                            color = TextBlack,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "See all",
                            color = BrandTeal,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Popular Courses List
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        courses.forEach { course ->
                            CourseCard(course = course)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

// custom CategoryCard
@Composable
fun CategoryCard(category: com.example.assignmentskillforge.data.Category) {
    val parsedColor = remember(category.iconColor) {
        try {
            Color(android.graphics.Color.parseColor(category.iconColor))
        } catch (e: Exception) {
            BrandTeal
        }
    }
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(16.dp))
            .clickable { }
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon box
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(parsedColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                // Colored rounded square inside
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(parsedColor)
                )
            }
            // Texts
            Column {
                Text(
                    text = category.name,
                    color = TextBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 17.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "${category.courseCount} courses",
                    color = Color(0xFF8E8E93),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun CourseCard(course: com.example.assignmentskillforge.data.Course) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(20.dp))
            .clickable { }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Course Thumbnail with Color Gradient Overlay & Border
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 75.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.BottomStart
            ) {
                // Coil AsyncImage to load network URLs, falling back to local resource placeholder
                AsyncImage(
                    model = course.thumbnailUrl,
                    contentDescription = course.title,
                    placeholder = painterResource(id = R.drawable.thumbnail),
                    error = painterResource(id = R.drawable.thumbnail),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Premium deep teal-to-black gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.15f),
                                    Color(0xFF00332C).copy(alpha = 0.8f) // Premium dark teal
                                )
                            )
                        )
                )
                // Text overlay
                Text(
                    text = course.title,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right: Course Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Difficulty Badge
                val isBeginner = course.level.equals("Beginner", ignoreCase = true)
                val badgeColor = if (isBeginner) Color(0xFF00BFA5) else Color(0xFFE67E22)
                Text(
                    text = course.level.uppercase(),
                    color = badgeColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Course Title
                Text(
                    text = course.title,
                    color = TextBlack,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(2.dp))
                
                // Instructor Name
                Text(
                    text = course.instructor.name,
                    color = Color(0xFF8E8E93),
                    fontSize = 13.sp
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                // Info Row (Rating and Time)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFF39C12),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.rating.toString(),
                        color = Color(0xFF8E8E93),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.clock_icon),
                        contentDescription = "Duration",
                        tint = Color(0xFF8E8E93),
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${course.durationHours}h",
                        color = Color(0xFF8E8E93),
                        fontSize = 12.sp
                    )
                }
            }
        }   
    }
}

@Composable
fun CustomScrollIndicator(
    scrollFraction: Float,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit
) {
    // Simple clean indicator — no black background, no arrows
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .height(4.dp)
    ) {
        // Light grey track
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFE0E0E0))
        )
        // Animated dark thumb
        val animatedFraction = animateFloatAsState(
            targetValue = scrollFraction,
            label = "scrollFraction"
        )
        val f = animatedFraction.value.coerceIn(0f, 1f)
        val leadingWeight  = f * 0.6f
        val trailingWeight = (1f - f) * 0.6f
        Row(modifier = Modifier.fillMaxWidth()) {
            if (leadingWeight > 0f)  Spacer(modifier = Modifier.weight(leadingWeight))
            Box(
                modifier = Modifier
                    .weight(0.4f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color(0xFF9E9E9E))
            )
            if (trailingWeight > 0f) Spacer(modifier = Modifier.weight(trailingWeight))
        }
    }
}
