package com.example.assignmentskillforge

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


val TextBlack = Color(0xFF111111)
val BrandTeal = Color(0xFF009688)

// Data Models
data class Category(
    val title: String,
    val courseCount: Int,
    val bgColor: Color,
    val iconColor: Color
)

data class Course(
    val title: String,
    val author: String,
    val rating: Double,
    val duration: String,
    val difficulty: String,
    val gradientStart: Color,
    val gradientEnd: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    val categoryScrollState = rememberScrollState() // for horizontal scroll
    val coroutineScope = rememberCoroutineScope()

    // Sample Data
    val categories = remember {
        listOf(
            Category("Android\nDevelopment", 2, Color(0xFFE0F2F1), Color(0xFF00BFA5)),
            Category("Backend & APIs", 2, Color(0xFFE8F5E9), Color(0xFF2ECC71)),
            Category("Frontend\nDevelopment", 3, Color(0xFFFFF3E0), Color(0xFFF39C12)),
            Category("UI/UX Design", 4, Color(0xFFF3E5F5), Color(0xFF9B59B6)),
            Category("Data Science", 1, Color(0xFFE1F5FE), Color(0xFF3498DB))
        )
    }

    val courses = remember {
        listOf(
            Course(
                "Kotlin Fundamentals",
                "Aarav Sharma",
                4.7,
                "6.5h",
                "BEGINNER",
                Color(0xFF009688),
                Color(0xFF00796B)
            ),
            Course(
                "Jetpack Compose Essentials",
                "Meera Nair",
                4.8,
                "9h",
                "INTERMEDIATE",
                Color(0xFF512DA8),
                Color(0xFF303F9F)
            ),
            Course(
                "Node.js from Scratch",
                "Sara Khan",
                4.5,
                "7.5h",
                "BEGINNER",
                Color(0xFF009688),
                Color(0xFF00796B)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
            .statusBarsPadding() // auto add padding acroding statubar
            .navigationBarsPadding()  // auto add bottom - padding acroding bottombar
    ) {
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
                            text = "D",
                            color = Color.White,
                            fontSize = 17.sp,
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
            @OptIn(ExperimentalFoundationApi::class) //- provide by google this may be changes in future so must be add this lien
            CompositionLocalProvider(LocalOverscrollConfiguration provides null) { // Parent ek value provide
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
                val leadingWeight  = f * 0.6f // eadingWeight = 0 * 0.6 = 0 and trailingWeight = 1 * 0.6 = 0.6
                val trailingWeight = (1f - f) * 0.6f   // total f*0.6 + (1-f)*0.6 = 0.6
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

// custom CategoryCard
@Composable
fun CategoryCard(category: Category) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .height(170.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
            .clickable { }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon box
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(category.bgColor),
                contentAlignment = Alignment.Center
            ) {
                // White rounded square inside (matches reference)
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(Color.White)
                )
            }
            // Texts
            Column {
                Text(
                    text = category.title,
                    color = TextBlack,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
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
fun CourseCard(course: Course) {
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
            // Left: Course Thumbnail
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 75.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(course.gradientStart, course.gradientEnd)
                        )
                    )
                    .drawBehind {
                        // Decorative circular waves
                        drawCircle(
                            color = Color.White.copy(alpha = 0.15f),
                            radius = size.minDimension * 0.7f,
                            center = Offset(size.width * 0.8f, size.height * 0.2f)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = 0.1f),
                            radius = size.minDimension * 0.5f,
                            center = Offset(size.width * 0.3f, size.height * 0.9f)
                        )
                    }
                    .padding(8.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = course.title,
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right: Course Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Difficulty Badge
                val badgeColor = if (course.difficulty == "BEGINNER") Color(0xFF00BFA5) else  Color(0xFFE67E22)
                Text(
                    text = course.difficulty,
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
                    text = course.author,
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
                    ClockIcon(color = Color(0xFF8E8E93), modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = course.duration,
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

@Composable
fun CustomBellIcon(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Canvas(modifier = modifier.size(22.dp)) {
        val w = size.width
        val h = size.height
        
        // Bell dome
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.18f)
            // Left curve
            cubicTo(w * 0.28f, h * 0.22f, w * 0.22f, h * 0.45f, w * 0.22f, h * 0.68f)
            lineTo(w * 0.12f, h * 0.76f)
            lineTo(w * 0.88f, h * 0.76f)
            lineTo(w * 0.78f, h * 0.68f)
            // Right curve
            cubicTo(w * 0.78f, h * 0.45f, w * 0.72f, h * 0.22f, w * 0.5f, h * 0.18f)
        }
        drawPath(path = path, color = color)
        
        // Bell clapper (bottom circle arc)
        drawArc(
            color = color,
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = true,
            topLeft = Offset(w * 0.41f, h * 0.76f),
            size = Size(w * 0.18f, h * 0.14f)
        )
        
        // Bell top ring
        drawCircle(
            color = color,
            radius = w * 0.07f,
            center = Offset(w * 0.5f, h * 0.12f),
            style = Stroke(width = 1.8.dp.toPx())
        )
    }
}

@Composable
fun ClockIcon(color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.width / 2 - 1.dp.toPx()
        drawCircle(
            color = color,
            radius = radius,
            style = Stroke(width = 1.5.dp.toPx())
        )
        // Hour hand
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x, center.y - radius * 0.5f),
            strokeWidth = 1.5.dp.toPx()
        )
        // Minute hand
        drawLine(
            color = color,
            start = center,
            end = Offset(center.x + radius * 0.4f, center.y),
            strokeWidth = 1.5.dp.toPx()
        )
    }
}
