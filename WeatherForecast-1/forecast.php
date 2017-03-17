 <?php
  if (isset($_POST['submit'])) {
    print_form();
    process();
  }
  else {
  print_form();
  }
  function process()
  {
   $street= $_POST['street'];
   $city = $_POST['city'];
   $state =$_POST['state'];
   $address = $street."," .$city. "," .$state;
   $url = "https://maps.google.com/maps/api/geocode/xml?address=" . urlencode($address)."&key=AIzaSyCH7_Znw179ilOA7FhaaYDFLvSBmC6_3hQ" ;
   echo $url;


   $xmlString = file_get_contents($url);
   $xmlObject = simplexml_load_string($xmlString);
   $latitude = $xmlObject->result[0]->geometry->location->lat;
   $longitude = $xmlObject->result[0]->geometry->location->lng;
   $timezone = $xmlObject->result[0]->geometry->location->timezone;


   $APIKey = "465acbc7f3bf2f4c3df72e6a4f5b3e54";
   $extractedValues = $APIKey . "/" . $latitude . "," . $longitude . "?" . "units=" .$_POST['degree']. "&exclude=flags";
   $urlForecast = "https://api.forecast.io/forecast/" . $extractedValues;
   $jsonObject = file_get_contents($urlForecast);

   $JsonParsed = json_decode($jsonObject);
   $weatherCondition = $JsonParsed->currently->summary;
   $weatherTemperature = $JsonParsed->currently->temperature;
   $image = $JsonParsed->currently->icon;


   switch($image){
     case "clear-day":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/clear.png";
                      break;
     case "clear-night":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/clear_night.png";
                        break;
     case "rain":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/rain.png";
                 break;
     case "snow":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/snow.png";
                  break;
     case "sleet":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/sleet.png";
                  break;
     case "wind": $icon = "http://cs-server.usc.edu:45678/hw/hw6/images/wind.png";
                break;
     case "fog":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/fog.png";
                break;
     case "cloudy":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/cloudy.png";
                    break;
     case "partly-cloudy-day":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/cloud_day.png";
                              break;
     case "partly-cloudy-night":$icon = "http://cs-server.usc.edu:45678/hw/hw6/images/cloud_night.png";
                                break;
    }


    $precipitation = $JsonParsed->currently->precipIntensity;
    if($precipitation >= 0 and $precipitation <= 0.002)
    {
      $prec = "None";
    }

    else if($precipitation > 0.002 and $precipitation <= 0.017)
    {
      $prec = "Very Light";

    }

    else if($precipitation > 0.017 and $precipitation <= 0.1)
    {
      $prec = "Light";

    }
    else if($precipitation > 0.1  and $precipitation < 0.4)
    {
      $prec = "Moderate";
    }
    else if($precipitation >= 0.4)
    {
      $prec = "Heavy";
    }
    $RainTemp = $JsonParsed->currently->precipProbability;
    $chanceOfRain = ($RainTemp * 100)."%";
    $WindSpeed = intval($JsonParsed->currently->windSpeed);
    $DewPoint = intval($JsonParsed->currently->dewPoint);
    $tempHumid = $JsonParsed->currently->humidity;
    $Humidity = ($tempHumid * 100)."%";
    $Visibility = intval($JsonParsed->currently->visibility);
    date_default_timezone_set($timezone);
    $SunRise = date('h:i A',$JsonParsed->daily->data[0]->sunriseTime);
    $SunSet = date('h:i A',$JsonParsed->daily->data[0]->sunsetTime);
    switch($_POST['degree'])
    {
      case "si": $WindSpeed = $WindSpeed ."mts/s";
                 $Visibility = $Visibility."mts";
                 break;
      case "us": $WindSpeed = $WindSpeed."mph";
                 $Visibility = $Visibility."mi";
                 break;
    }

    echo '
    <div id="output" style = "align:center;width:250;border:solid;">
    <table>
    <tr><td align="center">'.$weatherCondition.'</td></tr>
    <tr><td align="center">'.$weatherTemperature.'</td></tr>
    <tr><td align="center"><img src = '.$icon.' </td></tr>
    <tr><td>Precipitation:</td><td>'.$prec.'</td></tr>
    <tr><td>Chance Of Rain: </td><td> '.$chanceOfRain.'</td></tr>
    <tr><td>Wind Speed: </td><td> '.$WindSpeed.' </td></tr>
    <tr><td>Dew Point: </td><td> '.$DewPoint.' </td></tr>
    <tr><td>Humidity: </td><td> '.$Humidity.' </td></tr>
    <tr><td>Visibility: </td><td> '.$Visibility.'</td></tr>
    <tr><td>Sunrise: </td><td>'.$SunRise.'</td></tr>
    <tr><td>Sunset: </td><td> '.$SunSet.'</td></tr>
    </table>
    </div>';
}

function print_form()
{

  echo '
  <html>
  <head>
  <script type="text/javascript">
  function checkEmpty()
  {
    var street = document.getElementById("street").value;
    var city = document.getElementById("city").value;
    var state = document.getElementById("state").options.selectedIndex;
    var celsius = document.getElementsByName("degree").checked;
    var Fahrenheit = document.getElementsByName("degree").checked;
    if(street.length < 1)
    {
      alert("Please enter the street");
      return false;
    }
    if(city.length < 1)
    {
      alert("Please enter the city");
      return false;
    }
    if(state == 0)
    {
      alert("Please select a state");
      return false;
    }

  }
  function clearFields()
  {
    document.getElementById("street").value = "";
    document.getElementById("city").value = "";
    document.getElementById("state").value = "";
    document.getElementsByName("degree").checked = false;
    document.getElementsByClassName("output").innerHTML = "";
    window.location.replace("forecast.php");
}
  </script>
  </head>
  <body>
  <form name="forecast" method="POST" action= "forecast.php" >
  <p> Forecast Search </p>
  <div style = "align:center;width:400;border:solid;">
  <table>
  <tr><td><label>Street Address:*</label></td><td><input type="text" name="street" id="street" value="'.$_POST["street"].'"> </td></tr>
  <tr><td><label>City:*</label></td><td><input type="text" name="city" id="city" value=""></td></tr>
  <tr><td><label>State:*</label></td><td><select name="state" id="state" value="">
  <option value="Select your state">Select Your State</option>
	<option value="AL">Alabama</option>
	<option value="AK">Alaska</option>
	<option value="AZ">Arizona</option>
	<option value="AR">Arkansas</option>
	<option value="CA">California</option>
	<option value="CO">Colorado</option>
	<option value="CT">Connecticut</option>
	<option value="DE">Delaware</option>
	<option value="DC">District Of Columbia</option>
	<option value="FL">Florida</option>
	<option value="GA">Georgia</option>
	<option value="HI">Hawaii</option>
	<option value="ID">Idaho</option>
	<option value="IL">Illinois</option>
	<option value="IN">Indiana</option>
	<option value="IA">Iowa</option>
	<option value="KS">Kansas</option>
	<option value="KY">Kentucky</option>
	<option value="LA">Louisiana</option>
	<option value="ME">Maine</option>
	<option value="MD">Maryland</option>
	<option value="MA">Massachusetts</option>
	<option value="MI">Michigan</option>
	<option value="MN">Minnesota</option>
	<option value="MS">Mississippi</option>
	<option value="MO">Missouri</option>
	<option value="MT">Montana</option>
	<option value="NE">Nebraska</option>
	<option value="NV">Nevada</option>
	<option value="NH">New Hampshire</option>
	<option value="NJ">New Jersey</option>
	<option value="NM">New Mexico</option>
	<option value="NY">New York</option>
	<option value="NC">North Carolina</option>
	<option value="ND">North Dakota</option>
	<option value="OH">Ohio</option>
	<option value="OK">Oklahoma</option>
	<option value="OR">Oregon</option>
	<option value="PA">Pennsylvania</option>
	<option value="RI">Rhode Island</option>
	<option value="SC">South Carolina</option>
	<option value="SD">South Dakota</option>
	<option value="TN">Tennessee</option>
	<option value="TX">Texas</option>
	<option value="UT">Utah</option>
	<option value="VT">Vermont</option>
	<option value="VA">Virginia</option>
	<option value="WA">Washington</option>
	<option value="WV">West Virginia</option>
	<option value="WI">Wisconsin</option>
	<option value="WY">Wyoming</option>
</select></td></tr>

<tr><td><label>Degree:*</label></td>
<td><input type="radio" name="degree" value="us" checked="checked"/> Farhenheit
<input type="radio" name="degree" value="si" /> Celsius</td></tr>

<tr><td><button type="submit" name="submit" onclick="return checkEmpty()"> Search </button>
<button type="button" name="clear" onclick="clearFields()"> Clear </button></td></tr>
</table><br/>
* - Mandatory Fields <br/><br/>
<a href="http://forecast.io/" > Powered by Forecast.io </a>

</form>
</div>
</body>
</html>

}
';
?>
