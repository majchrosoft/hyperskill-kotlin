package contacts.models

data class Organisation(
    var name: String = "",
    var address: String = "",
    override var phone: String = ""
) : Contact(phone) {
    override val isPerson: Boolean = false

    override fun fullName(): String = name

    override fun formatInfo(): String {
        val phoneStr = if (phone.isEmpty()) "[no number]" else phone
        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

        return listOf(
            "Organization name: $name",
            "Address: $address",
            "Number: $phoneStr",
            "Time created: ${createdAt.withSecond(0).withNano(0).format(formatter)}",
            "Time last edit: ${updatedAt.withSecond(0).withNano(0).format(formatter)}"
        ).joinToString("\n")
    }

    override fun validate(): List<String> {
        val errors = mutableListOf<String>()
        validatePhoneNumber(phone)?.let { errors.add(it) }
        return errors
    }

    override fun validateField(field: String, value: String): String? {
        return when (field) {
            "number" -> validatePhoneNumber(value)
            else -> null
        }
    }

    override fun getFields(): List<String> = listOf("name", "address", "number")

    override fun getFieldValue(field: String): String {
        return when (field) {
            "name" -> name
            "address" -> address
            "number" -> phone.ifEmpty { "[no data]" }
            else -> ""
        }
    }

    override fun setFieldValue(field: String, value: String) {
        when (field) {
            "name" -> name = value
            "address" -> address = value
            "number" -> phone = value
        }
    }
}
