package contacts.models

data class Person(
    var firstName: String = "",
    var surName: String = "",
    var birthDate: String = "",
    var gender: String = "",
    override var phone: String = ""
) : Contact(phone) {
    override val isPerson: Boolean = true

    override fun fullName(): String = "$firstName $surName"

    override fun formatInfo(): String {
        val birthDateStr = if (birthDate.isEmpty()) "[no data]" else birthDate
        val genderStr = if (gender.isEmpty()) "[no data]" else gender
        val phoneStr = if (phone.isEmpty()) "[no number]" else phone
        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")

        return listOf(
            "Name: $firstName",
            "Surname: $surName",
            "Birth date: $birthDateStr",
            "Gender: $genderStr",
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

    override fun getFields(): List<String> = listOf("name", "surname", "birth", "gender", "number")

    override fun getFieldValue(field: String): String {
        return when (field) {
            "name" -> firstName
            "surname" -> surName
            "birth" -> birthDate.ifEmpty { "[no data]" }
            "gender" -> gender.ifEmpty { "[no data]" }
            "number" -> phone.ifEmpty { "[no data]" }
            else -> ""
        }
    }

    override fun setFieldValue(field: String, value: String) {
        when (field) {
            "name" -> firstName = value
            "surname" -> surName = value
            "birth" -> birthDate = value
            "gender" -> gender = value
            "number" -> phone = value
        }
    }
}
