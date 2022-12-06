package com.moviles.proyectofinaltrabajador2.models

class Usuario {
    var id: Int = 0
    var name: String = ""
    var email: String = ""
    override fun toString(): String {
        return "Usuario(id=$id, name='$name', email='$email')"
    }

}