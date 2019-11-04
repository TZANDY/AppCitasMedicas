<?php
    $con = mysqli_connect("localhost", "root", "", "usuarios");
    
    $nombre = $_POST["nombre"];
    $apellido = $_POST["apellido"];
    $dni = $_POST["dni"];
    $email = $_POST["email"];
    $clave = $_POST["clave"];
    $statement = mysqli_prepare($con, "INSERT INTO user (nombre, apellido, dni, email,clave) VALUES (?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssis", $nombre, $apellido, $dni, $email, $clave);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>