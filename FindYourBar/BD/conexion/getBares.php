<?php
$host="localhost"; //replace with database hostname 
$username="ps16"; //replace with database username 
$password="proyectoSoftware1516"; //replace with database password 
$db_name="fyb"; //replace with database name
$con=mysql_connect("$host", "$username", "$password")or die("cannot connect");
mysql_set_charset("UTF8", $con);
mysql_select_db("$db_name")or die("cannot select DB");
$sql = "select * from bar"; 
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
}
$bares = array("Bar" => $baresInt);
echo json_encode($bares);
mysql_close($con);
?>

    