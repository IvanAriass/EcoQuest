package com.example.myapplication

data class Rol(
    var id: Int,
    var nombreRol: String
) {
    constructor() : this(
        id = 0,
        nombreRol = ""
    )
}
