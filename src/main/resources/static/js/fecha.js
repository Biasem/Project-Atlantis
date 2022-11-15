function alerta() {
    alert("prueba")
}

function cambiofecha(){
    var fecha = document.getElementById("fecha_inicio").value
    document.getElementById("fecha_final").setAttribute("min",fecha)
console.log(document.getElementById("fecha_final"));
}