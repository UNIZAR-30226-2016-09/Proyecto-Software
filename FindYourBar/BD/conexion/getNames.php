<?php
/**
 * Devuelve el bar buscado
 */
require_once __DIR__ . '/db_connect.php';
$con = new DB_CONNECT();    //conexion con DB
$parametro = $_REQUEST['nombre'];
$sql = "select b.nombre,descripcion,direccion,edad,horarioApertura,horarioCierre,telefono,email,facebook,i.imagenId from bar b,tiene t, imagen i where nombre like '%".$parametro."%' and i.tipo='principal' and b.nombre=t.nombreId and t.imagenId=i.imagenId";

$result = mysql_query($sql);
$baresInt = array();

if(mysql_num_rows($result)){
    while($row=mysql_fetch_assoc($result)){
        $nombre = $row['nombre'];
        $sqlImagenes = "select distinct i2.imagenId from tiene t2,imagen i2 where t2.nombreId='".$nombre."' and t2.imagenId=i2.imagenId and i2.tipo='secundaria'";
        $resultImagenes = mysql_query($sqlImagenes);
        $imagenesArray = array();
        if(mysql_num_rows($resultImagenes)){
            while($row2=mysql_fetch_assoc($resultImagenes)){   
                $imagen = $row2['imagenId'];
                array_push($imagenesArray, $imagen);
             }
         }
        
        $sqlEventos = "select h.evento from hace h where h.nombre='".$nombre."'";
        $resultEventos = mysql_query($sqlEventos);
        $eventosArray = array();
        if(mysql_num_rows($resultEventos)){
            
            while($row2=mysql_fetch_assoc($resultEventos)){   
                $evento = $row2['evento'];
                array_push($eventosArray, $evento);
             }
         }

        $sqlMusica = "select distinct m.musica from hay m where m.nombre='".$nombre."'";
        $resultMusica = mysql_query($sqlMusica);
        $musicaArray = array();
        if(mysql_num_rows($resultMusica)){
            while($row2=mysql_fetch_assoc($resultMusica)){   
                $musica = $row2['musica'];
                array_push($musicaArray, $musica);
             }
         }

        $bar = array (    
            'nombre' => $row['nombre'],               
            'descripcion' => $row['descripcion'],                
            'direccion' => $row["direccion"],               
            'edad' => $row["edad"],                
            'horarioApertura' => $row["horarioApertura"],             
            'horarioCierre' => $row["horarioCierre"],               
            'telefono' => $row["telefono"],                
            'email' => $row["email"],                
            'facebook' => $row["facebook"],
            'imagenId' => $row["imagenId"],
            'secundaria' => $imagenesArray,
            'eventos' => $eventosArray);
        array_push($baresInt, $bar);       
    }
}
$bares = array("Bar" => $baresInt);

echo json_encode($bares);
?>