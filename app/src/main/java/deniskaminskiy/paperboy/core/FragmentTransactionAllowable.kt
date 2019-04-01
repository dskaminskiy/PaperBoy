package deniskaminskiy.paperboy.core

interface FragmentTransactionAllowable {

    /**
     * Метод для проверки состояния активити и фрагментов
     * @return -
     * для активити: true после onCreate и прямо перед onResumeFragments активити; false прямо перед onSaveInstanceState активити
     * для фрагмента: true если фрагмент добавлен и реализация этого метода в родительской активити фрагмента возвращает true
     */
    val isTransactionAllowed: Boolean

}