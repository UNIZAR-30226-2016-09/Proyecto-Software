<?php
 /*Get all bares */

// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM bar") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["bar"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $bar = array();
        $bar["nombre"] = $row["nombre"];
        $bar["descripcion"] = $row["descripcion"];
        $bar["direccion"] = $row["direccion"];
        $bar["edad"] = $row["edad"];
        $bar["horarioApertura"] = $row["horarioApertura"];
        $bar["horarioCierre"] = $row["horarioCierre"];
        $bar["telefono"] = $row["telefono"];
        $bar["email"] = $row["email"];
        $bar["facebook"] = $row["facebook"];
        // push single product into final response array
        array_push($response["bar"], $bar);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    // echo no users JSON
    echo json_encode($response);
}
?>