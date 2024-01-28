/**
 * Clase que representa una cuenta bancaria.
 *
 * @property numcuenta Número de cuenta único asociado a la cuenta.
 * @property saldodisp Saldo disponible en la cuenta.
 */
class Cuenta(val numcuenta:String, var saldodisp:Double){

    /**
     * Consulta el saldo actual de la cuenta.
     *
     * @return Mensaje con el número de cuenta y el saldo actual.
     */
    fun consultar_saldo():String{
        return "Tu cuenta $numcuenta tiene un saldo de $saldodisp"
    }

    /**
     * Recibe abonos en la cuenta, aumentando el saldo disponible.
     *
     * @param monto_recibir Monto a abonar en la cuenta.
     */
    fun recibirabonos(monotarecibir: Double){
        saldodisp += monotarecibir
    }

    /**
     * Realiza pagos desde la cuenta, disminuyendo el saldo disponible.
     *
     * @param pago_a_realizar Monto a pagar desde la cuenta.
     */
    fun realizarpagos(pagoarealizar:Double){
        saldodisp -= pagoarealizar
    }
}

/**
 * Clase que representa a una persona con cuentas bancarias.
 *
 * @property dni Número de identificación personal de la persona.
 * @property listadecuentas Array de cuentas asociadas a la persona.
 */
class Persona(private val dni:String, var listadecuentas:Array<Cuenta>){

    init {
        require(listadecuentas.size <= 3) {"La persona no puede tener mas de 3 cuentas"}
    }

    /**
     * Añade una cuenta a la lista de cuentas de la persona.
     *
     * @param cuenta Cuenta a añadir.
     */
    fun anadircuenta(cuenta: Cuenta){
        if (listadecuentas.size >= 3){
            println("Maximo de cuentas alcanzadas")
        }else listadecuentas.plus(cuenta)
    }

    /**
     * Verifica si la persona es morosa
     *
     * @return `true` si la persona es morosa, `false` de lo contrario.
     */
    fun esmoroso():Boolean{
        for (cuenta in listadecuentas){
            if (cuenta.saldodisp <= 0) return true
        }
        return false
    }

    /**
     * Realiza una transferencia entre dos cuentas.
     *
     * @param personaB Persona receptora de la transferencia.
     * @param numcuentaorigen Número de cuenta de origen.
     * @param numcuentadestino Número de cuenta de destino.
     * @param monto_a_pasar Monto a transferir.
     */
    fun transferencia(personaB:Persona, numcuentaorigen: String, numcuentadestino: String, montoapasar:Double){
        val cuentaorigen = listadecuentas.find { it.numcuenta == numcuentaorigen }
        val cuentadestino = personaB.listadecuentas.find { it.numcuenta == numcuentadestino }

        if(cuentadestino != null && cuentaorigen!= null && cuentaorigen.saldodisp <= cuentaorigen.saldodisp ){
            cuentaorigen.realizarpagos(montoapasar)
            cuentadestino.recibirabonos(montoapasar)
            println("Se ha realizado la transferencia")
        }else println("Error en la transferencia")
    }
}

/**
 * funcion principal, donde se hace todo
 */
fun main() {
    val cuenta1 = Cuenta("001", 1000.0)
    val cuenta2 = Cuenta("002", 500.0)
    val cuenta3 = Cuenta("003", 420.0)
    val cuenta4 = Cuenta("004",120.0)

    val arraycuentas = arrayOf(cuenta1,cuenta2)
    val arraycuentas2 = arrayOf(cuenta4)


    val persona1 = Persona("12345678X", arraycuentas)
    persona1.anadircuenta(cuenta3)

    val persona2 = Persona("20601303l",arraycuentas2)
    persona1.listadecuentas[1].realizarpagos(700.0)
    println(persona1.esmoroso())

    persona1.transferencia(persona2,"001","004",400.0)
    println(persona2.listadecuentas[0].consultar_saldo())
    println(persona1.listadecuentas[0].consultar_saldo())
}