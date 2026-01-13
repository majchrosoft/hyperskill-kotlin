package contacts.view

import contacts.models.Organisation
import java.time.format.DateTimeFormatter

class OrganisationViewFormatter {
    fun format(organisation: Organisation): String {
        val phone = if (organisation.phone.isEmpty()) "[no number]" else organisation.phone
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        
        return listOf(
            "Organization name: ${organisation.name}",
            "Address: ${organisation.address}",
            "Number: $phone",
            "Time created: ${organisation.createdAt.withSecond(0).withNano(0).format(formatter)}",
            "Time last edit: ${organisation.updatedAt.withSecond(0).withNano(0).format(formatter)}"
        ).joinToString("\n")
    }
}
