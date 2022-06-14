<?php
// Se establece conexión con el servidor mediante el archivo de conexión.php
include 'conexion.php';

// Conexión POST al servidor para no mandar usuario y contraseña por GET
$Username=$_POST['username'];
$Password=$_POST['password'];

// Usuarios de prueba para saber si el query a la base de datos funciona
// Son los parámetros necesarios para poder ejecutar el SQL Injection del reto
//$Username="%' or '1'='1";
//$Password="%' or '1'='1";

// Se lleva a cabo el query para obetener el usuario de la base de datos en formato JSON
$query = "SELECT * FROM users WHERE Username='".$Username."' AND Password='".$Password."'";
$query_result = mysqli_query($conexion, $query);
$table_rows = array();

while($row = mysqli_fetch_assoc($query_result)){
    $table_rows[] = $row;
}

echo json_encode($table_rows);

$conexion->close();
?>
