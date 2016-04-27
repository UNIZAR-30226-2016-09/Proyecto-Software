<?php
/**
 * Devuelve los tipos de musica que hay en la base de datos
 */
require_once __DIR__ . '/db_connect.php';
$con = new DB_CONNECT();    //conexion con DB
$sql = "select * from tipomusica"; 
$result = mysql_query($sql);
$musicaInt = array();

if(mysql_num_rows($result)){
    while($row=mysql_fetch_assoc($result)){
        $mus = array ('musica' => $row['musica']);
        array_push($musicaInt, $mus);       
    }
}
$musica = array("Musica" => $musicaInt);
echo json_encode($musica);
?>