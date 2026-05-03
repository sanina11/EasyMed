package pt.ipc.medicapp.navigation

object Routes {
    const val SPLASH        = "splash"
    const val LOGIN         = "login"
    const val REGISTAR      = "registar"
    const val DASHBOARD     = "dashboard"
    const val ADICIONAR     = "adicionar"
    const val HISTORICO     = "historico"
    const val PERFIL        = "perfil"

    // Detalhe da medicação recebe o id como argumento
    const val DETALHE       = "detalhe/{medId}"
    fun detalhe(medId: Long) = "detalhe/$medId"
}
