package contacts.models

import java.io.Serializable
import java.time.LocalDateTime

abstract class Contact(
    open var phone: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) : Serializable {
    abstract val isPerson: Boolean
    abstract fun validate(): List<String>
    abstract fun validateField(field: String, value: String): String?
    abstract fun fullName(): String
    abstract fun formatInfo(): String

    abstract fun getFields(): List<String>
    abstract fun getFieldValue(field: String): String
    abstract fun setFieldValue(field: String, value: String)

    fun getSearchableString(): String {
        return getFields().joinToString(" ") { getFieldValue(it) } + " " + phone
    }

    companion object {
        val rules = mapOf(
            "phone" to """^\+?(\(\w+\)([\s-]\w+)*|\w+([\s-]\(\w+\))?([\s-]\w+)*|)$""".toRegex()
        )
        val messages = mapOf(
            "phone" to "Wrong number format!"
        )
    }

    protected fun validatePhoneNumber(number: String): String? {
        val regex = rules["phone"]!!
        if (number.isNotEmpty() && !regex.matches(number)) {
            return messages["phone"]
        }
        return null
    }
}
