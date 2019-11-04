<?php
    $con = mysqli_connect("localhost", "root", "", "usuarios");
    
    $dni = $_POST["dni"];
    $clave = $_POST["clave"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM user WHERE dni = ? AND clave = ?");
    mysqli_stmt_bind_param($statement, "ss", $dni, $clave);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $nombre, $apellido, $dni, $email,$clave);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;  
        $response["nombre"] = $nombre;
        $response["apellido"] = $apellido;
        $response["dni"] = $dni;
        $response["email"] = $email;
        $response["clave"] = $clave;
    }
    
    echo json_encode($response);
?>