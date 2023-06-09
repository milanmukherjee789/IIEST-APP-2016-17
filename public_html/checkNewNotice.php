<?php 
function update_file($result){
	$myFile = "record.txt";
	$json_data = file_get_contents($myFile);
	$parse_data = unserialize($json_data);
	$array1 = json_decode($result, true);		// New from database
	$array2 = json_decode($parse_data, true);	// old from file
	$array1 = $array1['Notice'];
	$array2 = $array2['Notice'];
	echo "--------------------- </br>";
	$reply="";
	$flag=false;
	foreach($array1 as $key => $val) {
  		if (isset($array2[$key])) {
    			if($array1[$key] != $array2[$key]){
    				$flag = true;
    				$reply = $reply.$key.' ';
    			}
  		}
		else{
			$flag = true;
			break;		
		}
	}
	echo $reply;
	//update;
	if($flag){
		$url = 'http://localhost/fcm_notify.php';
		$fh = fopen($myFile, 'w');
		fwrite($fh, serialize($result));
		echo "written";
		fclose($fh);
		$req = 'title='.$reply;
		$ch = curl_init($url);
		curl_setopt($ch, CURLOPT_POST, 1);
		curl_setopt($ch, CURLOPT_POSTFIELDS,$req);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    		$result = curl_exec($ch );
		curl_close( $ch );
		echo $result;
	}
}
	$check_url = "http://localhost/check_new.php";
	$ch = curl_init();
	curl_setopt( $ch,CURLOPT_URL, $check_url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt($ch, CURLOPT_HTTPHEADER, array(                                               
        'Content-Type: text/html; charset=utf-8')                                  
    );  
	$result = curl_exec($ch );
	echo $result;
	curl_close( $ch );
	update_file($result);
 ?>
