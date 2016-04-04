<?php
$host="localhost"; //replace with database hostname 
$username="ps16"; //replace with database username 
$password="proyectoSoftware1516"; //replace with database password 
$db_name="fyb"; //replace with database name
$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
mysql_set_charset("UTF8", $con);
mysql_select_db("$db_name")or die("cannot select DB");

$parametro = $_REQUEST['nombre'];
$sql = "select * from bar where nombre='".$parametro."'";
$result = mysql_query($sql);
$baresInt = array();
//$bares = array("Bar" => array(array()));
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
    //{"Bar":[{"":"","":""..},{"":"","":"",...}]}
}
$bares = array("Bar" => $baresInt);
echo json_encode($bares);
mysql_close($con);
/*
$bares["nombre"] = $row["nombre"];
        $bares["descripcion"] = $row["descripcion"];
        $bares["direccion"] = $row["direccion"];
        $bares["edad"] = $row["edad"];
        $bares["horarioApertura"] = $row["horarioApertura"];
        $bares["horarioCierre"] = $row["horarioCierre"];
        $bares["telefono"] = $row["telefono"];
        $bares["email"] = $row["email"];
        $bares["facebook"] = $row["facebook"]; */
?>