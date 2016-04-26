<?php
/**
 * Inserta nuevo tipo de musica en la BD
 */
require_once __DIR__ . '/db_connect.php';
$con = new DB_CONNECT();
$tipoMusica = $_REQUEST['tipoMusica'];

$sql = "insert into tipomusica values('".$tipoMusica."')";
$result = mysql_query($sql);
?>