<?php
$street= $_GET['street'];
$city = $_GET['city'];
$state =$_GET['state'];
$address = $street."," .$city. "," .$state;
$url = "https://maps.google.com/maps/api/geocode/xml?address=" . urlencode($address) ;
$xmlString = file_get_contents($url);
$xmlObject = simplexml_load_string($xmlString);
$latitude = $xmlObject->result[0]->geometry->location->lat;
$longitude = $xmlObject->result[0]->geometry->location->lng;

$APIKey = "465acbc7f3bf2f4c3df72e6a4f5b3e54";
$extractedValues = $APIKey . "/" . $latitude . "," . $longitude . "?" . "units=" .$_GET['degree']. "&exclude=flags";
$urlForecast = "https://api.forecast.io/forecast/" . $extractedValues;
$jsonObject = file_get_contents($urlForecast);
echo $jsonObject;
?>
