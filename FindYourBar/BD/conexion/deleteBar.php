<?php
/**
 * Elimina los bares en la BD
 */
require_once __DIR__ . '/db_connect.php';
$con = new DB_CONNECT();
$nombre = $_REQUEST['nombre'];

$sql = "delete from hay where nombre='".$nombre."'";
$result = mysql_query($sql);
$sql = "delete from imagen where nombreId='".$nombre."'";
$result = mysql_query($sql);
$sql = "delete from evento where nombre='".$nombre."'";
$result = mysql_query($sql);
$sql = "delete from bar where nombre='".$nombre."'";
$result = mysql_query($sql);
?>