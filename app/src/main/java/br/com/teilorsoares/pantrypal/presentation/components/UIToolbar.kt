package br.com.teilorsoares.pantrypal.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.teilorsoares.pantrypal.R
import br.com.teilorsoares.pantrypal.presentation.theme.Brown200

@Composable
fun UIToolbar(
    onBackClick: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_back),
            contentDescription = "Voltar",
            tint = Brown200,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .clickable { onBackClick() }
        )

        Text(
            text = title,
            style = TextStyle(
                fontSize = 36.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight(600),
                color = Brown200,
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f)
        )
    }
}