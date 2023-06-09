<?php
	function connect()
	{
		$servername = "localhost";
		$username = "IIEST_CST";
		$password = "AcadIiIEs@1$5%";
		$dbname = "FCM_REG";

		try
		{
		    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
		    // set the PDO error mode to exception
		    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		    //echo "success";
	    }
		catch(PDOException $e)
	    {
	    	echo $sql . "<br>" . $e->getMessage();
	    }

		return $conn;
	}
	function disconnect($conn)
	{
		$conn = null;
	} 
?>
