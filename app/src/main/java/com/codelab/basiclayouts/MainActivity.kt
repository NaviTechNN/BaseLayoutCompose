/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelab.basiclayouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MySootheApp() }
    }
}

// Step: Search bar - Modifiers
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    //Для создания панели поиска используем компонент TextField
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {    //параметр для добавления иконки
                      Icon(imageVector = Icons.Default.Search, //Добавление иконки поиска
                           contentDescription = null
                      )
        },
        //Установка фонового цвета текстового поля
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        //Добавление текста заполнителя
        placeholder = {
                      Text(text = stringResource(id = R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth() //заполняет все пространство по ширине
            .heightIn(min = 56.dp) //высота элемента
    )
}

// Step: Align your body - Alignment
@Composable
fun AlignYourBodyElement(
    //Сделать изображение и текст динамическими
    @DrawableRes drawable: Int, //Параметр изображения
    @StringRes text: Int, //Параметр текста
    modifier: Modifier = Modifier
) {
    // Выравниевание элементов
    Column(horizontalAlignment = Alignment.CenterHorizontally, //Выравнивание по горизонтале
        modifier = modifier) {
        //Добавление картинки
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            //Маштабирование изображения имеет значения Crop, Fit, FillBounds
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp) //Размер изображения
                .clip(CircleShape) //Обрезает изображение, делает его круглым
        )
        //Добавление текста
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.h3, //Добавление стиля к тексту
        modifier = Modifier.paddingFromBaseline( //Отступы с верху 24dp снизу 8dp
            top = 24.dp, bottom = 8.dp
        )
        )
    }
}

// Step: Favorite collection card - Material Surface
@Composable
fun FavoriteCollectionCard(
    //Добавление параметров изображения и текста
    @DrawableRes drawable: Int, //Параметр изображения
    @StringRes text: Int, //Параметр текста
    modifier: Modifier = Modifier
) {
    //Создание карточки
    Surface(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        //Строки
        Row(
            verticalAlignment = Alignment.CenterVertically, //Выравнивание элементов по вертикали
            modifier = Modifier.width(192.dp) //Установка ширины карточки
        ) {
            //Добавление изображение
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)

            )
            //Добавление текста
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.h3, //Стиль текста
                modifier = Modifier.padding(horizontal = 16.dp) //Отступ по горизонтали
            )

        }
    }
}

// Step: Align your body row - Arrangements
@Composable
fun AlignYourBodyRow(
    modifier: Modifier = Modifier
) {
    // Создание горизонтального списка элементов
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp), //Задание отступов между элементами
        contentPadding = PaddingValues(horizontal = 16.dp), //Задание отступов по краям по горизонтали
        modifier = modifier) {
        items(alignYourBodyData) {
            item -> AlignYourBodyElement(drawable = item.drawable, text = item.text)
        }
    }
}

// Step: Favorite collections grid - LazyGrid
@Composable
fun FavoriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    // Создание горизонтального списка элементов из двух строк
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2), //Создание 2 двух строк в сетки
        contentPadding = PaddingValues(horizontal = 16.dp), //Задание отступов по краям по горизонтали
        horizontalArrangement = Arrangement.spacedBy(8.dp) , //Задание отступов между элементами по горизонтали
        verticalArrangement = Arrangement.spacedBy(8.dp), //Задание отступов между элементами по вертикали
        modifier = modifier.height(120.dp) //Высота карточки
    ) {
        items(favoriteCollectionsData) {
            item ->
            FavoriteCollectionCard(
                drawable = item.drawable,
                text = item.text,
                modifier = Modifier.height(56.dp)
            )
        }
    }
}

// Step: Home section - Slot APIs
@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Экан на основе слотов
    Column(modifier) {
        Text(
            text = stringResource(title).uppercase(Locale.getDefault()),//Отображение текста заглавными буквами
            style = MaterialTheme.typography.h2, //Задание стиля текста
        modifier = Modifier
            .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
            .padding(horizontal = 16.dp)
        )
        content()
    }
}

// Step: Home screen - Scrolling
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    // Компоновка главного экрана
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.align_your_body) {
            AlignYourBodyRow()
        }
        HomeSection(title = R.string.favorite_collections) {
            FavoriteCollectionsGrid()
            
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}

// Step: Bottom navigation - Material
@Composable
private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
    // Нижняя навигация
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background, //Установка цвета фона нижней навигации
        modifier = modifier) {
        BottomNavigationItem(
            icon = {
                   //Добавление иконки в меню навигации
                   Icon(imageVector = Icons.Default.Spa, contentDescription = null)
            } ,
            label = {
                //Добавление текста
                    Text(text = stringResource(id = R.string.bottom_navigation_home))
            } ,
            selected = true, //Выбор пункта навигации
            onClick = { /*TODO*/ }) //Действие переход по меню
        BottomNavigationItem(
            icon = {
                   //Добавление иконки
                   Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
            label = {
                    //Добавление название меню навигации
                    Text(text = stringResource(id = R.string.bottom_navigation_profile))
            } ,
            selected = false ,
            onClick = { /*TODO*/ })
    }
}

// Step: MySoothe App - Scaffold
@Composable
fun MySootheApp() {
    // Главный экран
    MySootheTheme() { //Тема приложения
        Scaffold(
            bottomBar = { //Добавление нижней навигации
                SootheBottomNavigation()
            } ) {padding ->
            HomeScreen(Modifier.padding(padding))

        }
    }
}

private val alignYourBodyData = listOf(
    R.drawable.ab1_inversions to R.string.ab1_inversions,
    R.drawable.ab2_quick_yoga to R.string.ab2_quick_yoga,
    R.drawable.ab3_stretching to R.string.ab3_stretching,
    R.drawable.ab4_tabata to R.string.ab4_tabata,
    R.drawable.ab5_hiit to R.string.ab5_hiit,
    R.drawable.ab6_pre_natal_yoga to R.string.ab6_pre_natal_yoga
).map { DrawableStringPair(it.first, it.second) }

private val favoriteCollectionsData = listOf(
    R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
    R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
    R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
    R.drawable.fc4_self_massage to R.string.fc4_self_massage,
    R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
    R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun SearchBarPreview() {
    MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyElementPreview() {
    MySootheTheme {
        AlignYourBodyElement(
            text = R.string.ab1_inversions,
            drawable = R.drawable.ab1_inversions,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionCardPreview() {
    MySootheTheme {
        FavoriteCollectionCard(
            drawable = R.drawable.fc2_nature_meditations,
            text = R.string.fc2_nature_meditations ,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun FavoriteCollectionsGridPreview() {
    MySootheTheme { FavoriteCollectionsGrid() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun AlignYourBodyRowPreview() {
    MySootheTheme { AlignYourBodyRow() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun HomeSectionPreview() {
    MySootheTheme {
        HomeSection(R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun ScreenContentPreview() {
    MySootheTheme { HomeScreen() }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun BottomNavigationPreview() {
    MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MySoothePreview() {
    MySootheApp()
}
