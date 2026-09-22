package com.example.profilecard

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface (
                    modifier = Modifier.fillMaxHeight(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileCardScreen()
                }
            }
        }

        }
    }

@Composable
fun ProfileCardScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        ProfileCard()
    }
}

@Composable
fun ProfileCard() {
    var isFollowing by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image (Fallback to vector icon or use painterResource(R.drawable.profile))
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF6200EE), Color(0xFF03DAC5))
                        )
                    )
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
//                Icon(
//                    imageVector = Icons.Default.Person,
//                    contentDescription = "Profile Picture",
//                    tint = Color.White,
//                    modifier = Modifier.size(70.dp)
//                )

                // If you have a local image in res/drawable/avatar.png:
                /*
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                */
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            Text(
                text = stringResource(id = R.string.name),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Title / Subtitle
            Text(
                text = stringResource(R.string.title),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Short Bio
            Text(
                text = stringResource(R.string.bio),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStat(count = "142", label = "Posts")
                ProfileStat(count = "2.8k", label = "Followers")
                ProfileStat(count = "350", label = "Following")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons (Follow & Message)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { isFollowing = !isFollowing },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFollowing) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(text = if (isFollowing) "Following" else "Follow")
                }

                OutlinedButton(
                    onClick = { /* Handle message action */ },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
//                    Icon(
//                        imageVector = Icons.Default.Email,
//                        contentDescription = null,
//                        modifier = Modifier.size(18.dp)
//                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Message")
                }
            }
        }
    }
}

@Composable
fun ProfileStat(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileCardScreenPreview() {
    MaterialTheme {
        ProfileCardScreen()
    }
}