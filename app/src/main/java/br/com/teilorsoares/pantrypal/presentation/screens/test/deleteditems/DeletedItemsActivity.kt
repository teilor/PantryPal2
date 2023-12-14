package br.com.teilorsoares.pantrypal.presentation.screens.test.deleteditems

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.teilorsoares.pantrypal.presentation.theme.Brown1100
import br.com.teilorsoares.pantrypal.presentation.theme.Brown200
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DeletedItemsActivity : ComponentActivity() {

    private val viewModel: DeletedItemsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            Text(text = "Relatório - Alimentos Descartados        ",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight(400),
                    color = Brown200,
                    background = Brown1100,)
                )


            LazyColumn(
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(start = 4.dp, top = 42.dp, bottom = 6.dp),

                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.foods) { food ->
                    val deletedDate = remember(food) { SimpleDateFormat("dd/MM/yyyy").format(food.deletedDate) }
                    Text(text = "${food.name} - $deletedDate",
                        style = TextStyle(
                            fontSize = 22.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(400),
                            color = Brown200,)
                        )
                }
            }
        }
    }
}