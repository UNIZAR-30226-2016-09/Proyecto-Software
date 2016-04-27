<?php
/**
 * Inserta bares en la BD
 */
require_once __DIR__ . '/db_connect.php';
$con = new DB_CONNECT();    //conexion con DB
$nombre = $_REQUEST['nombre'];
$descripcion = $_REQUEST['descripcion'];
$edad = $_REQUEST['edad'];
$edad = (int) $edad;
$direccion = $_REQUEST['direccion'];
$horarioApertura = $_REQUEST['horarioApertura'];
$horarioApertura = (double) $horarioApertura;
$horarioCierre = $_REQUEST['horarioCierre'];
$horarioCierre = (double) $horarioCierre;
$telefono = $_REQUEST['telefono'];
$telefono = (int) $telefono;
$email = $_REQUEST['email'];
$facebook = $_REQUEST['facebook'];
$imgPrinci = $_REQUEST['imgPrinci'];
$imgSecun = $_REQUEST['imgSecun'];
$evento = $_REQUEST['evento'];
$musica = $_REQUEST['musica'];

$sql = "insert into bar values('".$nombre."','".$descripcion."','".$direccion."','".$edad."','".$horarioApertura."','".$horarioCierre."','".$telefono."',
    '".$email."','".$facebook."')";
$result = mysql_query($sql);
$sql = "insert into imagen values('".$nombre."','".$imgPrinci."','principal')";
$result = mysql_query($sql);

for ($x = 0; $x < count($imgSecun); $x++) {
    $sql = "insert into imagen values('".$nombre."','".$imgSecun[$x]."', 'secundaria')";
    $result = mysql_query($sql);
}

for ($x = 0; $x < count($evento); $x++) {
    $sql = "insert into evento values('".$evento[$x]."','".$nombre."')";
    $result = mysql_query($sql);
}

for ($x = 0; $x < count($musica); $x++) {
    $sql = "insert into hay values('".$nombre."','".$musica[$x]."')";
    $result = mysql_query($sql);
}
?>