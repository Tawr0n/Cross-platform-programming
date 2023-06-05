import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File

private const val FILE_PATH = "data.txt"

internal class OrderService {

    var state: RootState by mutableStateOf(initialState())
        private set

    fun onItemClicked(id: Long) {
        setState { copy(editingItemId = id) }
    }

    fun onItemDoneChanged(id: Long, prepayment: Boolean) {
        setState {
            updateItem(id = id) { it.copy(prepayment = prepayment.toString()) }
        }
    }

    fun onItemDeleteClicked(id: Long) {
        setState {
            val updatedItems = items.filterNot { it.id == id }
            updateFile(updatedItems)
            copy(items = updatedItems)
        }
    }

    private fun updateFile(items: List<Order>) {
        val lines =
            items.map { "${it.id},${it.productName},${it.deliveryAddress},${it.cost},${it.amount},${it.prepayment},${it.orderDate}" }
        File(FILE_PATH).writeText(lines.joinToString("\n"))
    }

    fun onAddItemClicked() {
        setState {
            val newItem =
                Order(
                    id = items.maxOfOrNull(Order::id)?.plus(1L) ?: 1L,
                    productName = inputProductName,
                    deliveryAddress = inputDeliveryAddress,
                    cost = inputCost,
                    amount = inputAmount,
                    prepayment = inputPrepayment,
                    orderDate = inputOrderDate
                )

            val updatedItems = items + newItem
            updateFile(updatedItems)

            copy(items = updatedItems, inputProductName = "")

        }
    }

    fun onProductNameChanged(productName: String) {
        setState { copy(inputProductName = productName) }
    }

    fun onDeliveryAddressChanged(deliveryAddress: String) {
        setState { copy(inputDeliveryAddress = deliveryAddress) }
    }

    fun onCostChanged(cost: String) {
        setState { copy(inputCost = cost) }
    }

    fun onAmountChanged(amount: String) {
        setState { copy(inputAmount = amount) }
    }

    fun onPrepaymentChanged(prepayment: String) {
        setState { copy(inputPrepayment = prepayment) }
    }

    fun onOrderDateChanged(orderDate: String) {
        setState { copy(inputOrderDate = orderDate) }
    }


    fun onFilterSelected(filterValue: String?) {
        setState {
            copy(
                items = if (filterValue == null) {
                    initialState().items
                } else {
                    initialState().items.filter { it.prepayment == filterValue }
                }
            )
        }
    }

    fun onSearchChanged(search: String) {
        setState { copy(inputSearch = search) }
        setState {
            copy(
                items = if (search == "") {
                    initialState().items
                } else {
                    initialState().items.filter { it.deliveryAddress.contains(search, ignoreCase = true) }
                }
            )
        }
    }


    private fun RootState.updateItem(id: Long, transformer: (Order) -> Order): RootState {
        val updatedItems = items.updateItem(id = id, transformer = transformer)
        updateFile(updatedItems)
        return copy(items = updatedItems)
    }

    private fun List<Order>.updateItem(id: Long, transformer: (Order) -> Order): List<Order> {
        return map { item -> if (item.id == id) transformer(item) else item }
    }

    // Функція для зчитування елементів з файлу
    private fun readItemsFromFile(): List<Order> {
        val file = File(FILE_PATH)
        if (!file.exists()) return emptyList()

        val lines = file.readLines()
        return lines.mapNotNull { line ->
            val parts = line.split(",")
            if (parts.size == 7) {
                val id = parts[0].toLongOrNull()
                val productName = parts[1]
                val deliveryAddress = parts[2]
                val cost = parts[3]
                val amount = parts[4]
                val prepayment = parts[5]
                val orderDate = parts[6]
                if (id != null) {
                    Order(id, productName, deliveryAddress, cost, amount, prepayment, orderDate)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }


    private fun initialState(): RootState {
        val items = readItemsFromFile()

        return RootState(
            items = items
        )
    }

    private inline fun setState(update: RootState.() -> RootState) {
        state = state.update()
    }


    data class RootState(
        val items: List<Order> = emptyList(),
        val inputProductName: String = "",
        val inputDeliveryAddress: String = "",
        val inputCost: String = "",
        val inputAmount: String = "",
        val inputPrepayment: String = "",
        val inputOrderDate: String = "",
        val inputSearch: String = "", // Додайте цей рядок
        val editingItemId: Long? = null,
    )

}

