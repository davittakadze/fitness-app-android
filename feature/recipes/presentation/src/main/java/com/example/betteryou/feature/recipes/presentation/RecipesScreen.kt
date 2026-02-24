package com.example.betteryou.feature.recipes.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
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
    if (state.isSearching) {
        Box(
            Modifier
                .fillMaxSize()
                .background(TBCTheme.colors.background)
                .padding(16.dp)
        ) {
            if (state.selectedMeal != null) {
                ModalBottomSheet(
                    onDismissRequest = { onEvent(RecipesEvent.OnDismissSheet) },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = TBCTheme.colors.background
                ) {
                    BottomSheet(item = state.selectedMeal)
                }
            }

            Column(Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)) {

                Spacer(Modifier.height(32.dp))

                TBCAppTextField(
                    value = state.searchQuery,
                    onValueChange = { onEvent(RecipesEvent.OnSearchQueryChange(it)) },
                    placeholder = stringResource(R.string.search_recipes),
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onEvent(RecipesEvent.OnSearchClose) }
                        ) {
                            Icon(
                                contentDescription = null,
                                tint = TBCTheme.colors.onBackground,
                                painter = painterResource(R.drawable.close_svgrepo_com)
                            )
                        }
                    }
                )

                Spacer(Modifier.height(16.dp))

                val filteredMeals = state.meals.filter {
                    it.title.contains(state.searchQuery, ignoreCase = true)
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredMeals, key = { it.id }) { item ->
                        MealItem(
                            item = item,
                            isSelected = state.favouriteMeals.contains(item),
                            onFavouriteClick = {
                                onEvent(RecipesEvent.OnFavouriteClick(item))
                            },
                            onItemClick = { onEvent(RecipesEvent.OnItemClick(item)) }
                        )
                    }
                }
            }
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            if (state.selectedMeal != null) {
                ModalBottomSheet(
                    onDismissRequest = { onEvent(RecipesEvent.OnDismissSheet) },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = TBCTheme.colors.background
                ) {
                    BottomSheet(item = state.selectedMeal)
                }
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .background(TBCTheme.colors.background)
                    .padding(bottom = 100.dp)
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.healthy_recipes_title),
                            style = TBCTheme.typography.headlineMedium,
                            color = TBCTheme.colors.onBackground
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = TBCTheme.colors.transparentBack
                    ),
                    modifier = Modifier.padding(top = 16.dp),
                    actions = {
                        IconButton(onClick = { onEvent(RecipesEvent.OnSearchClick) }) {
                            Icon(
                                painter = painterResource(R.drawable.search_button_svgrepo_com),
                                contentDescription = null,
                                tint = TBCTheme.colors.onBackground
                            )
                        }
                    }
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(12.dp)
                ) {
                    items(items = state.categoryList, key = { it }) { item ->
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
                    val filteredMeals = state.selectedCategory.let { selected ->
                        if (selected == Meal.ALL) {
                            return@let state.meals
                        } else {
                            state.meals.filter { it.category == state.selectedCategory.toString().lowercase().replaceFirstChar { if(it.isLowerCase()) it.titlecase() else it.toString()} }
                        }
                    }
                    items(items = filteredMeals, key = { it.title }) { item ->
                        MealItem(
                            item = item,
                            isSelected = state.favouriteMeals.contains(item),
                            onFavouriteClick = { onEvent(RecipesEvent.OnFavouriteClick(item)) },
                            onItemClick = { onEvent(RecipesEvent.OnItemClick(item)) }
                        )
                    }
                }
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
            text =
            when(item){
                Meal.ALL -> stringResource(R.string.all)
                Meal.BREAKFAST -> stringResource(R.string.breakfast)
                Meal.LUNCH -> stringResource(R.string.lunch)
                Meal.DINNER -> stringResource(R.string.dinner)
            },
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
fun MealItem(
    item: RecipeUi,
    isSelected: Boolean,
    onFavouriteClick: () -> Unit,
    onItemClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clip(Radius.radius12)
            .clickable(
                onClick = {
                    onItemClick()
                }
            )
    ) {
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
                    text = stringResource(R.string.ingredient_count, item.ingredientCount),
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = stringResource(R.string.cooking_time, item.cookingTime),
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = stringResource(R.string.difficulty, item.difficulty),
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
            }
        }
        IconButton(
            onClick = {
                onFavouriteClick()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.heart_shape_svgrepo_com),
                null,
                tint = if (isSelected) {
                    TBCTheme.colors.destructiveColor
                } else {
                    TBCTheme.colors.border
                },
                modifier = Modifier
                    .padding(12.dp)
                    .size(28.dp)
            )
        }
    }
}

//Bottom sheet:
@Composable
fun BottomSheet(
    item: RecipeUi,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TBCAppAsyncImage(
            img = item.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            placeholder = R.drawable.icon_workout_screen
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            InfoCard(item)

            Spacer(Modifier.height(20.dp))

            Text(
                text = item.title,
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.ingredients),
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.onBackground
            )

            Spacer(Modifier.height(12.dp))

            (item.ingredients).forEach { item ->
                Text(
                    text = item,
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing4))
            }
            Spacer(Modifier.height(Spacer.spacing12))
            Text(
                text = stringResource(R.string.recipe),
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.onBackground
            )
            Spacer(Modifier.height(Spacer.spacing12))
            Text(
                text = item.recipe,
                style = TBCTheme.typography.bodyMedium,
                color = TBCTheme.colors.onBackground
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoCard(item: RecipeUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Radius.radius16)
            .border(1.dp, TBCTheme.colors.border, Radius.radius16)
            .background(TBCTheme.colors.border.copy(alpha = 0.2f))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.difficulty_without_dots),
            item.difficulty,
            R.drawable.chef_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.cooking_time_without_dots),
            item.cookingTime,
            R.drawable.timer_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.ingredient_count_without_dots),
            item.ingredientCount.toString(),
            R.drawable.cooking_food_in_a_hot_casserole_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun InfoItem(title: String, value: String, @DrawableRes icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = TBCTheme.colors.onBackground,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = title,
            color = TBCTheme.colors.onBackground,
            style = TBCTheme.typography.bodySmallest
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            color = TBCTheme.colors.onBackground,
            style = TBCTheme.typography.bodyLarge
        )
    }
}
