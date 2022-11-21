function cambiofecha(){
    var fecha = document.getElementById("fecha_inicio").value ;
    document.getElementById("fecha_final").setAttribute("min",fecha) ;
}

function setfecha() {
    var fecha = document.getElementById("fecha_inicio").valueAsDate;
    document.getElementById("fecha_inicio").setAttribute(fecha);
}