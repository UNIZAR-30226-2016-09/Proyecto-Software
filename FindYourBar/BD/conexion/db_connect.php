<?php
 /**
 * Clase para conectar con la base de datos
 */
class DB_CONNECT {
 
    /**
    * Constructor
    */
    function __construct() {
        $this->connect();
    }
 
    /**
    * Destructor
    */
    function __destruct() {
        $this->close();
    }
 
    /**
     * Conexion con la base de datos que devuelve el cursor de la conexion
     */
    function connect() {
        //Importar variables de la base y realizar conexion con DB mysql
        require_once __DIR__ . '/db_config.php';
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
        mysql_set_charset("UTF8", $con); 
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());
        return $con;
    }
 
    /**
     * Cierre de la conexion con BD
     */
    function close() {
        mysql_close();
    } 
}
?>