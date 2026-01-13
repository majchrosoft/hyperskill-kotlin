package contacts.view

import contacts.models.Contact
import contacts.models.Person
import java.time.format.DateTimeFormatter

class PersonViewFormatter {
    fun format(person: Person): String {
        val birthDate = if (person.birthDate.isEmpty()) "[no data]" else person.birthDate
        val gender = if (person.gender.isEmpty()) "[no data]" else person.gender
        val phone = if (person.phone.isEmpty()) "[no number]" else person.phone
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        
        return listOf(
            "Name: ${person.firstName}",
            "Surname: ${person.surName}",
            "Birth date: $birthDate",
            "Gender: $gender",
            "Number: $phone",
            "Time created: ${person.createdAt.withSecond(0).withNano(0).format(formatter)}",
            "Time last edit: ${person.updatedAt.withSecond(0).withNano(0).format(formatter)}"
        ).joinToString("\n")
    }
}
