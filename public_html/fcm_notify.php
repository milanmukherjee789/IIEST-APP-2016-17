<?php
#API access key from Google API's Console
	require "config.php";
	function populate_id(){
		$DeviceID = array();
		$conn = connect();
		$stmt = $conn ->prepare('SELECT reg_id FROM FCM_USERS');
		$stmt -> execute();
		while (($col1 = $stmt->fetchColumn())) {
        	array_push($DeviceID, $col1);
    	}
    	disconnect($conn);
    	return $DeviceID;
	}
    define( 'API_ACCESS_KEY', 'AAAAVJZsiHA:APA91bE7BVTVeULmKypJBWtCwiYmgY8YP-i85pzYDG6XNpxmeE95YgYsevTqijdf4vQEfPF_e1t_axim_p3Pn2I3hkp7DrszBWty4Xuc9uskSHrrrpg4r4ag_h_nO-8ro89RixdPDMh7' );
    $proxy = "10.31.0.1:8080";
    $proxy = explode(":", $proxy);
    $retrieve_id = populate_ID();
    $message_title = isset($_POST['title'])?$_POST['title']:'';
    $message_body = 'You have some unread '.$message_title.' notices(s).';
#prep the bundle
     $msg = array
          (
		'body' 	=> $message_body,
		'title'	=> 'IIEST,Shibpur',
             	'icon'	=> 'myicon',
              	'sound' => 'mySound'
          );
	$fields = array
			(
				'registration_ids' => $retrieve_id,
				'notification'	=> $msg
			);
	
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt($ch, CURLOPT_PROXY, $proxy[0]);
		curl_setopt($ch, CURLOPT_PROXYPORT, $proxy[1]);
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		curl_close( $ch );
#Echo Result Of FireBase Server
echo $result;
echo '</br> ..... '+$message_title;