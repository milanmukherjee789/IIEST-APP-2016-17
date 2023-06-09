<?php

	define( '_JEXEC', 1 );
	define('ERROR_CODE',0);
	define( 'DS', '/' );
	define( 'JPATH_BASE', $_SERVER[ 'DOCUMENT_ROOT' ]);
	require_once( JPATH_BASE . DS . 'includes' . DS . 'defines.php' );
	require_once( JPATH_BASE . DS . 'includes' . DS . 'framework.php' );
	require_once( JPATH_BASE . DS . 'libraries' . DS . 'joomla' . DS . 'factory.php' );
	function myErrorHandler($errno, $errstr, $errfile, $errline) {
 		echo ERROR_CODE;
 		echo "ERROR";
 		exit(); 	
	}
	//set_error_handler("myErrorHandler");


	$jc=new JConfig();
	$table_prefix=$jc->dbprefix;
	//$filter_tag=isset($_GET["tag_iiest"])?$_GET["tag_iiest"]:'';
	$filter_tag=array("Students Notices","Faculty Notices","Employment Notices","Public Notices");
	function retrieve_category_column_index($table1){
		$db1 = JFactory::getDBO();
		$query1=$db1->getQuery(true);
		$query1->select('*');
		$query1->from($db1->quoteName($table1));
		$db1->setQuery($query1);
		$results1 = $db1->loadAssocList();;
		$columns1 = array_keys($results1[0]);
		return $columns1[7];
	}
	function retrive_category_id($table,$filter_tag){
		$db = JFactory::getDBO();
		$query=$db->getQuery(true);
		$query->select('*');
		$query->from($db->quoteName($table));
		$db->setQuery($query);
		$db->execute();
		$total_row=$db->getNumRows();
		$results1 = $db->loadAssocList();
		$columns1 = array_keys($results1[0]);
		$total_columns=sizeof($columns1);
		for ($i=0; $i <$total_row ; $i++) { 
			for ($j=0; $j <$total_columns ; $j++) { 
				$value=$results1[$i][$columns1[$j]];
				if ($value==$filter_tag) {
					$column_id=$j;
					$row_id=$i;
					break;
				}
			}
		}
		return $results1[$row_id][$columns1[0]];
	}
	function retrieve_result($category_id,$priority_id,$table1){
		$db2=JFactory::getDBO();
		$query2=$db2->getQuery(true);
		$query2->select( 'COUNT(*)');
		$query2->where($db2->quoteName($category_id)." = ".$db2->quote($priority_id));
    	$query2->from($db2->quoteName($table1));
    	$db2->setQuery($query2);
    	return $db2->loadResult();
	}
	$table1=$table_prefix."content";
	$table=$table_prefix."categories";
	$category_id=retrieve_category_column_index($table1);
   	$obj = array();
	for ($i=0; $i <sizeof($filter_tag); $i++) { 
		$priority_id=retrive_category_id($table,$filter_tag[$i]);
		$total=($filter_tag!='')?retrieve_result($category_id,$priority_id,$table1):0;
		//$obj[$i]=$filter_tag[$i]." => ".$total;
		//echo $obj[$i]."</br>";
		$obj[$filter_tag[$i]]=$total;
	}
	$jobj=json_encode(array('Notice' =>$obj));
	echo $jobj;
?>
