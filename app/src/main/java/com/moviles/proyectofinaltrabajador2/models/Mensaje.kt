package com.moviles.proyectofinaltrabajador2.models

class Mensaje(
    var message : String = ""
) {
    override fun toString(): String {
        return "Mensaje(message='$message')"
    }
}