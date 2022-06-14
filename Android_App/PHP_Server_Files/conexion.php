<?php
// Se establece conexión con el servidor de XAMPP
$hostname='localhost';
$database='login_register';
$username='root';
$password='';

$conexion=new mysqli($hostname,$username,$password,$database);
if($conexion->connect_errno){
    echo "No se ha podido establecer una conexión con el servidor";
}
?>
