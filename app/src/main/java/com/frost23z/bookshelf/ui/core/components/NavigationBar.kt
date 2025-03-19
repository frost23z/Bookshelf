package com.frost23z.bookshelf.ui.core.components

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.R
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun NavigationBar(
	modifier: Modifier = Modifier,
	containerColor: Color = NavigationBarDefaults.containerColor,
	fabOnClick: () -> Unit,
	fabContent: @Composable () -> Unit,
	content: @Composable (RowScope.() -> Unit)
) {
	val density = LocalDensity.current
	Box(){
		FloatingActionButton(
			onClick = fabOnClick,
			containerColor = MaterialTheme.colorScheme.primary,
			shape = CircleShape,
			modifier = Modifier.align(Alignment.Center)
		) {
			fabContent()
		}
		Row(
			modifier = modifier
				.clip(
					shape = BottomAppBarCutoutShape(radiusPx = with(density) { 40.dp.toPx() })
				).background(color = containerColor)
		) {
			content()
		}
	}
}

class BottomAppBarCutoutShape(private val radiusPx: Float) : Shape {
	override fun createOutline(
		size: Size,
		layoutDirection: LayoutDirection,
		density: Density
	): Outline {
		val path = Path().apply {
			val centerX = size.width / 2
			val startAngle = 165f
			val sweepAngle = -150f
			val arcStartX = centerX + radiusPx * cos(toRadians(startAngle.toDouble())).toFloat()
			val arcStartY = radiusPx * sin(toRadians(startAngle.toDouble())).toFloat()
			val arcEndX = centerX + radiusPx * cos(toRadians(startAngle + sweepAngle.toDouble())).toFloat()
			val arcEndY = radiusPx * sin(toRadians(startAngle + sweepAngle.toDouble())).toFloat()

			moveTo(0f, 0f)
			lineTo(centerX - radiusPx * 1.25f, 0f)
			cubicTo(
				centerX - radiusPx * 1.25f,
				0f,
				centerX - radiusPx,
				0f,
				arcStartX,
				arcStartY
			)
			arcTo(
				rect = Rect(
					left = centerX - radiusPx,
					top = -radiusPx,
					right = centerX + radiusPx,
					bottom = radiusPx
				),
				startAngleDegrees = startAngle,
				sweepAngleDegrees = sweepAngle,
				forceMoveTo = false
			)
			cubicTo(
				arcEndX,
				arcEndY,
				centerX + radiusPx,
				0f,
				centerX + radiusPx * 1.25f,
				0f,
			)
			lineTo(size.width, 0f)
			lineTo(size.width, size.height)
			lineTo(0f, size.height)
			close()
		}
		return Outline.Generic(path)
	}
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Preview
@Composable
private fun NavigationBarPreview() {
	Scaffold(
		bottomBar = {
			NavigationBar(
				fabOnClick = {},
				fabContent = {
					androidx.compose.material3.Icon(
						rememberAnimatedVectorPainter(
							animatedImageVector =
							AnimatedImageVector.animatedVectorResource(R.drawable.anim_add),
							atEnd = true
						),
						contentDescription = "Add"
					)
				}
			) {
				NavigationBarItem(
					selected = true,
					icon = { Icon(Icons.Default.Home) },
					onClick = { },
					label = { Text("Home") }
				)
				NavigationBarItem(
					selected = false,
					icon = { Icon(Icons.Default.Search) },
					onClick = { },
					label = { Text("Search") }
				)
				NavigationBarItem(
					selected = false,
					icon = { Icon(Icons.Default.Favorite) },
					onClick = { },
					label = { Text("Favorite") }
				)
			}
		},
//		floatingActionButton = {
//			FloatingActionButton(
//				onClick = { },
//				containerColor = MaterialTheme.colorScheme.primary,
//				shape = CircleShape,
//				modifier = Modifier.offset(y = 44.dp),
//			) {
//				androidx.compose.material3.Icon(
//					rememberAnimatedVectorPainter(
//						animatedImageVector =
//							AnimatedImageVector.animatedVectorResource(R.drawable.anim_add),
//						atEnd = true
//					),
//					contentDescription = "Add"
//				)
//			}
//		},
//		floatingActionButtonPosition = FabPosition.Center
	) { innerPadding ->
	}
}