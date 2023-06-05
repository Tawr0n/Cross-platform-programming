import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun RootContent(modifier: Modifier = Modifier) {
    val model = remember { OrderService() }
    val state = model.state

    MainContent(
        modifier = modifier,
        items = state.items,
        inputProductName= state.inputProductName,
        inputDeliveryAddress= state.inputDeliveryAddress,
        inputCost= state.inputCost,
        inputAmount= state.inputAmount,
        inputPrepayment= state.inputPrepayment,
        inputOrderDate= state.inputOrderDate,
        inputSearch= state.inputSearch,  // <- Add this

        onItemClicked = model::onItemClicked,
        onItemDoneChanged = model::onItemDoneChanged,
        onItemDeleteClicked = model::onItemDeleteClicked,
        onAddItemClicked = model::onAddItemClicked,
        onProductNameChanged = model::onProductNameChanged,
        onDeliveryAddressChanged = model::onDeliveryAddressChanged,
        onCostChanged = model::onCostChanged,
        onAmountChanged = model::onAmountChanged,
        onPrepaymentChanged = model::onPrepaymentChanged,
        onOrderDateChanged = model::onOrderDateChanged,
        onFilterSelected = model::onFilterSelected,
        onSearchChanged = model::onSearchChanged, // <- And this
    )
}


private val OrderService.RootState.editingItem: Order?
    get() = editingItemId?.let(items::firstById)

private fun List<Order>.firstById(id: Long): Order? =
    firstOrNull { it.id == id }
