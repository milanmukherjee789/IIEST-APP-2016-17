<?php
#-------------------------------------------------------------
# Copyright (C) 2014 by web-eau.net  	   	   	   	   	   	   	   	   	 
# Homepage   : www.web-eau.net	   	   	   	   	   	   		 
# Author     : Daniel	    		   	   	   	   	   	   	   	 
# Email      : daniel@web-eau.net 	   	   	   	   	   	   	     
# Version    : 1.0.0                      	   	    	   	   		 
# License    : http://www.gnu.org/copyleft/gpl.html GNU/GPL          
#-------------------------------------------------------------

// no direct access
define( 'COM', $_SERVER[ 'DOCUMENT_ROOT' ]."/"."PURITY"."/");
defined( '_JEXEC' ) or die( 'Restricted access' );


$document = JFactory::getDocument();

$document->addStyleSheet(COM.'plugins/content/responsive-tables/responsive-tables.css');

?>