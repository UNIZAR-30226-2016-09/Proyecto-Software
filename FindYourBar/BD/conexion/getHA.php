<?php
/**
 * Devuelve los datos de todos los bares de la base de datos
 */
require_once __DIR__ . '/db_connect.php';
$con = new DB_CONNECT();    //conexion con DB
$parametro = $_REQUEST['nombre'];
$parametro = (double) $parametro;
$sql = "select * from bar where horarioApertura>='".$parametro."'";
$result = mysql_query($sql);
$baresInt = array();
if(mysql_num_rows($result)){
	while($row=mysql_fetch_assoc($result)){
		$bar = array (            
			'nombre' => $row['nombre'],               
        	'descripcion' => $row['descripcion'],                
            'direccion' => $row["direccion"],               
            'edad' => $row["edad"],                
            'horarioApertura' => $row["horarioApertura"],             
            'horarioCierre' => $row["horarioCierre"],               
            'telefono' => $row["telefono"],                
            'email' => $row["email"],                
            'facebook' => $row["facebook"]);
        array_push($baresInt, $bar);
	}
}
$bares = array("Bar" => $baresInt);
echo json_encode($bares);
mysql_close($con);
?>