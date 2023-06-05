import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    items: List<Order>,
    inputProductName: String,
    inputDeliveryAddress: String,
    inputCost: String,
    inputAmount: String,
    inputPrepayment: String,
    inputOrderDate: String,
    inputSearch: String,

    onItemClicked: (id: Long) -> Unit,
    onItemDoneChanged: (id: Long, prepayment: Boolean) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
    onAddItemClicked: () -> Unit,
    onProductNameChanged: (String) -> Unit,
    onDeliveryAddressChanged: (String) -> Unit,
    onCostChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onPrepaymentChanged: (String) -> Unit,
    onOrderDateChanged: (String) -> Unit,
    onFilterSelected: (String?) -> Unit,
    onSearchChanged: (String) -> Unit,
) {
    Column(modifier) {
        Box(Modifier.background(Color.White)) {
            TopAppBar(
                title = {
                    Text(
                        text = "Список замовлень",
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black
                    )
                },
                actions = {
                    Search(
                        inputSearch = inputSearch,
                        onSearchChanged = onSearchChanged,
                        onFilterSelected = onFilterSelected
                    )
                    Filter(
                        onFilterSelected = onFilterSelected
                    )

                },
                backgroundColor = Color.White,
                modifier = Modifier
                    .drawBehind {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                    .padding(8.dp) // Відступи навколо блоку
            )
        }





        Box(Modifier.weight(1F)) {
            // Filter the items by the search input before passing them to ListContent
            ListContent(
                items = items.filter { it.deliveryAddress.contains(inputSearch, ignoreCase = true) },
                onItemClicked = onItemClicked,
                onItemDoneChanged = onItemDoneChanged,
                onItemDeleteClicked = onItemDeleteClicked
            )
        }

        Input(
            productName = inputProductName,
            deliveryAddress = inputDeliveryAddress,
            cost = inputCost,
            amount = inputAmount,
            prepayment = inputPrepayment,
            orderDate = inputOrderDate,
            onProductNameChanged = onProductNameChanged,
            onDeliveryAddressChanged = onDeliveryAddressChanged,
            onCostChanged = onCostChanged,
            onAmountChanged = onAmountChanged,
            onPrepaymentChanged = onPrepaymentChanged,
            onOrderDateChanged = onOrderDateChanged,
            onAddClicked = onAddItemClicked
        )
    }
}

@Composable
private fun ListContent(
    items: List<Order>,
    onItemClicked: (id: Long) -> Unit,
    onItemDoneChanged: (id: Long, prepayment: Boolean) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items) { item ->
                Item(
                    item = item,
                    onClicked = { onItemClicked(item.id) },
                    onDoneChanged = { onItemDoneChanged(item.id, it) },
                    onDeleteClicked = { onItemDeleteClicked(item.id) }
                )

                Divider()
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = listState)
        )
    }
}

@Composable
private fun Item(
    item: Order,
    onClicked: () -> Unit,
    onDoneChanged: (Boolean) -> Unit,
    onDeleteClicked: () -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = onClicked)) {
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString("Назва товару: ${item.productName}  |  " +
                    "Адреса доставки: ${item.deliveryAddress}  |  Вартість: ${item.cost}  |  Кількість: ${item.amount}" +
                    "  |  Передоплата: ${item.prepayment}  |  Дата замовлення: ${item.orderDate}"),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onDeleteClicked) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(10.dp))
    }
}

@Composable
private fun Input(
    productName: String,
    deliveryAddress: String,
    cost: String,
    amount: String,
    prepayment: String,
    orderDate: String,
    onProductNameChanged: (String) -> Unit,
    onDeliveryAddressChanged: (String) -> Unit,
    onCostChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onPrepaymentChanged: (String) -> Unit,
    onOrderDateChanged: (String) -> Unit,
    onAddClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            value = productName,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onProductNameChanged,
            label = { Text(text = "Назва товару", fontSize = 12.sp) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = deliveryAddress,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onDeliveryAddressChanged,
            label = { Text(text = "Адреса для доставки", fontSize = 12.sp) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = cost,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onCostChanged,
            label = { Text(text = "Вартість", fontSize = 12.sp) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = amount,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onAmountChanged,
            label = { Text(text = "Кількість", fontSize = 12.sp) }
        )


        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = prepayment,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onPrepaymentChanged,
            label = { Text(text = "Проведення передоплати", fontSize = 12.sp) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        OutlinedTextField(
            value = orderDate,
            modifier = Modifier
                .weight(weight = 1F),
            onValueChange = onOrderDateChanged,
            label = { Text(text = "Дата замовлення", fontSize = 12.sp) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {
            onAddClicked()
            onProductNameChanged("")
            onDeliveryAddressChanged("")
            onCostChanged("")
            onAmountChanged("")
            onPrepaymentChanged("")
            onOrderDateChanged("")
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,

                )
        }
    }
}


@Composable
private fun Filter(
    onFilterSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<Boolean?>(null) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Filter")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            DropdownMenuItem(
                onClick = {
                    selectedFilter = null
                    onFilterSelected(null)
                    expanded = false
                }
            ) {
                Text(text = "Усі")
            }

            DropdownMenuItem(
                onClick = {
                    selectedFilter = true
                    onFilterSelected("true")
                    expanded = false
                }
            ) {
                Text(text = "Оплачені")
            }

            DropdownMenuItem(
                onClick = {
                    selectedFilter = false
                    onFilterSelected("false")
                    expanded = false
                }
            ) {
                Text(text = "Неоплачені")
            }
        }
    }
}


@Composable
private fun Search(
    inputSearch: String,
    onSearchChanged: (String) -> Unit,
    onFilterSelected: (String?) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {

        OutlinedTextField(
            value = inputSearch,
            onValueChange = onSearchChanged,
            modifier = Modifier.width(200.dp),  // Remove the fixed height
            placeholder = { Text(text = "Search") },
            singleLine = true,  // Set singleLine to true to keep the label visible when typing
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { /* Execute search here */ }),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black,
                textColor = Color.Black

            )
        )

        Spacer(modifier = Modifier.width(8.dp))

    }
}

