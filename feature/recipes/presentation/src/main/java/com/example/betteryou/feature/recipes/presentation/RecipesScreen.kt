package com.example.betteryou.feature.recipes.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer
import com.example.betteryou.core_ui.util.components.TBCAppAsyncImage
import com.example.betteryou.feature.recipes.presentation.model.Meal
import com.example.betteryou.feature.recipes.presentation.model.RecipeUi

@Composable
fun RecipesScreen(viewModel: RecipesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    RecipesContent(state, viewModel::onEvent)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesContent(state: RecipesState, onEvent: (RecipesEvent) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(TBCTheme.colors.background)
            .padding(bottom=64.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Healthy recipes",
                    style = TBCTheme.typography.headlineMedium,
                    color = TBCTheme.colors.onBackground
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = TBCTheme.colors.transparentBack
            ),
            modifier = Modifier.padding(top = 16.dp),
            actions = {
                IconButton(onClick = {

                }) {
                    Icon(
                        painter = painterResource(R.drawable.search_button_svgrepo_com),
                        contentDescription = "Favorite",
                        tint = TBCTheme.colors.onBackground
                    )
                }
            }
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            items(items = state.categoryList, key = { it.displayName }) { item ->
                CategoryItem(item, item == state.selectedCategory, onClick = {
                    onEvent(RecipesEvent.OnCategoryClick(item))
                })
            }
        }

        Spacer(Modifier.height(Spacer.spacing12))

        LazyColumn(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = state.meals, key = { it.title }) { item ->
                MealItem(item,state.favouriteMeals.contains(item),onFavouriteClick = {onEvent(RecipesEvent.OnFavouriteClick(item))})
            }
        }
    }
}

@Composable
fun CategoryItem(item: Meal, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .wrapContentSize()
            .clip(Radius.radius16)
            .background(
                if (isSelected) {
                    TBCTheme.colors.onBackground
                } else {
                    TBCTheme.colors.border
                }
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.displayName,
            style = TBCTheme.typography.bodyLarge,
            color = if (isSelected) {
                TBCTheme.colors.background
            } else {
                TBCTheme.colors.onBackground
            }
        )
    }
}

@Composable
fun MealItem(item: RecipeUi,isSelected: Boolean,onFavouriteClick:()->Unit) {
    Box(Modifier
        .fillMaxWidth()
        .height(125.dp)
        .clip(Radius.radius12)) {
        Row {
            TBCAppAsyncImage(
                img = item.imageUrl,
                modifier = Modifier.size(125.dp),
                placeholder = R.drawable.ic_launcher_background
            )
            Spacer(Modifier.width(Spacer.spacing12))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "Ingrediend count: ${item.ingredientCount}",
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "Cooking time: ${item.cookingTime}",
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = "Difficulty: ${item.difficulty}",
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
            }
        }
        IconButton(
            onClick = {
                onFavouriteClick()
            }
            ,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        ) {
            Icon(
                painter=painterResource(R.drawable.heart_shape_svgrepo_com),
                null,
                tint = if(isSelected) {
                    TBCTheme.colors.destructiveColor
                }else{
                    TBCTheme.colors.border
                },
                modifier = Modifier
                    .padding(12.dp)
                    .size(28.dp)
            )
        }
    }
}

@Preview
@Composable
fun RecipesScreenPreview() {
    TBCTheme {
        MealItem(
            RecipeUi(
                category = "All",
                title = "Hello iddwidnid",
                imageUrl = "",
                ingredientCount = 5,
                ingredients = listOf("duef", "fhunf", "ifief"),
                cookingTime = "2h",
                difficulty = "hard",
                recipe = "efninfienf"
            ),
            true
        ) {}
    }
}