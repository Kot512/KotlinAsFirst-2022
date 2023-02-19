@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "Телефонная книга".
 *
 * Общая сложность задания -- средняя, общая ценность в баллах -- 14.
 * Объект класса хранит список людей и номеров их телефонов,
 * при чём у каждого человека может быть более одного номера телефона.
 * Человек задаётся строкой вида "Фамилия Имя".
 * Телефон задаётся строкой из цифр, +, *, #, -.
 * Поддерживаемые методы: добавление / удаление человека,
 * добавление / удаление телефона для заданного человека,
 * поиск номера(ов) телефона по заданному имени человека,
 * поиск человека по заданному номеру телефона.
 *
 * Класс должен иметь конструктор по умолчанию (без параметров).
 */
class PhoneBook {
    override fun hashCode(): Int = phoneList.hashCode()

    private val phoneList = mutableMapOf<String, MutableSet<String>>()

    private fun deletePhone(name: String, phone: String): Unit {
        phoneList[name]!!.forEach {
            if (formatted(phone) == formatted(it))
                phoneList[name]!!.remove(it)
        }
    }

    private fun formatCheck(number: String): Boolean =
        number.matches(Regex("""[^A-zА-я ]+"""))

    private fun formatted(phone: String): String =
        phone.replace(Regex("""\D+"""), "")

    private fun formatted(phones: Set<String>): Set<String> =
        phones.map { formatted(it) }.toMutableSet()


    /**
     * Добавить человека.
     * Возвращает true, если человек был успешно добавлен,
     * и false, если человек с таким именем уже был в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun addHuman(name: String): Boolean =
        if (phoneList[name] == null) {
            phoneList[name] = mutableSetOf<String>()
            true
        } else false

    /**
     * Убрать человека.
     * Возвращает true, если человек был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * (во втором случае телефонная книга не должна меняться).
     */
    fun removeHuman(name: String): Boolean =
        if (phoneList[name] != null) {
            phoneList.remove(name)
            true
        } else false

    /**
     * Добавить номер телефона.
     * Возвращает true, если номер был успешно добавлен,
     * и false, если человек с таким именем отсутствовал в телефонной книге,
     * либо у него уже был такой номер телефона,
     * либо такой номер телефона зарегистрирован за другим человеком.
     */
    fun addPhone(name: String, phone: String): Boolean =
        if (
            !formatCheck(phone) ||
            formatted(phone) in formatted(phoneList.values.flatten().toSet()) ||
            phoneList[name] == null
        ) false else {
            phoneList[name]!! += phone
            true
        }


    /**
     * Убрать номер телефона.
     * Возвращает true, если номер был успешно удалён,
     * и false, если человек с таким именем отсутствовал в телефонной книге
     * либо у него не было такого номера телефона.
     */
    fun removePhone(name: String, phone: String): Boolean =
        if (
            formatCheck(phone) &&
            phoneList[name] != null &&
            formatted(phone) in formatted(phoneList[name]!!)
        ) {
            deletePhone(name, phone)
            true
        } else false

    /**
     * Вернуть все номера телефона заданного человека.
     * Если этого человека нет в книге, вернуть пустой список
     */
    fun phones(name: String): Set<String> =
        phoneList[name]?.toSet() ?: setOf<String>()

    /**
     * Вернуть имя человека по заданному номеру телефона.
     * Если такого номера нет в книге, вернуть null.
     */
    fun humanByPhone(phone: String): String? {
        phoneList.forEach { (name, phones) ->
            if (formatted(phone) in formatted(phones))
                return name
        }
        return null
    }

    /**
     * Две телефонные книги равны, если в них хранится одинаковый набор людей,
     * и каждому человеку соответствует одинаковый набор телефонов.
     * Порядок людей / порядок телефонов в книге не должен иметь значения.
     */
    override fun equals(other: Any?): Boolean {
        if (other !is PhoneBook) return false
        this.phoneList.forEach { (name, phones) ->
            if (
                other.phoneList[name] == null ||
                formatted(phones) != formatted(other.phoneList[name]!!)
            ) return false
        }
        return true
    }
}