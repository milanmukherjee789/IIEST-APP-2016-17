<?php 
	if(!isset($_POST['id']) || !isset($_POST['e_mail']) )
		exit();
	$registration_id = $_POST['id'];
	$e_mail = $_POST['e_mail'];
	require "config.php";
	$conn = connect();
	try {
		$stmt = $conn->prepare("INSERT INTO FCM_USERS Values(null,'".$registration_id."','".$e_mail."')");
	    $stmt->execute();
	    echo "1";
	} catch (Exception $e) {
		echo "0";
	}
 ?>