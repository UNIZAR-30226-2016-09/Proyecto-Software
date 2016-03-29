<?php
 /**
 * A class file to connect to database
 */
class DB_CONNECT {
 
    // constructor
    function __construct() {
        $this->connect();
    }
 
    // destructor, closing db connection
    function __destruct() {
        $this->close();
    }
 
    /**
     * Connect with database
     */
    function connect() {
        // import database connection variables
        require_once __DIR__ . '/db_config.php';
 
        // Connecting to mysql database
        $con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
 
        // Selecing database
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());
 
        // returing connection cursor
        return $con;
    }
 
    /**
     * Close db connection
     */
    function close() {
        // closing db connection
        mysql_close();
    }

    /*$username = $_GET['root'];
	$password = $_GET[''];*/
 
}
?>