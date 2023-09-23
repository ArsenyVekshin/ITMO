<?php
session_start();
$data = (isset($_POST['data'])) ? $_POST['data'] : '3';
switch ($data) {
	case '-11':  $_SESSION['timezone'] = 'Pacific/Apia'; break;
	case '-10':  $_SESSION['timezone'] = 'Pacific/Honolulu'; break;
	case '-9':   $_SESSION['timezone'] = 'America/Anchorage'; break;
	case '-8':   $_SESSION['timezone'] = 'America/Los_Angeles'; break;
	case '-7':   $_SESSION['timezone'] = 'America/Denver'; break;
	case '-6':   $_SESSION['timezone'] = 'America/Chicago'; break;
	case '-5':   $_SESSION['timezone'] = 'America/New_York'; break;
	case '-4':   $_SESSION['timezone'] = 'America/Santiago'; break;
	case '-3':   $_SESSION['timezone'] = 'America/Godthab'; break;
	case '-2':   $_SESSION['timezone'] = 'America/Noronha'; break;
	case '-1':   $_SESSION['timezone'] = 'Atlantic/Azores'; break;
	case '0':    $_SESSION['timezone'] = 'Europe/London'; break;
	case '1':    $_SESSION['timezone'] = 'Europe/Paris'; break;
	case '2':    $_SESSION['timezone'] = 'Europe/Minsk'; break;
	case '3':    $_SESSION['timezone'] = 'Europe/Moscow'; break;
	case '3.3':  $_SESSION['timezone'] = 'Asia/Tehran'; break;
	case '4':    $_SESSION['timezone'] = 'Asia/Tbilisi'; break;
	case '4.3':  $_SESSION['timezone'] = 'Asia/Kabul'; break;
	case '5':    $_SESSION['timezone'] = 'Asia/Yekaterinburg'; break;
	case '5.3':  $_SESSION['timezone'] = 'Asia/Calcutta'; break;
	case '5.45': $_SESSION['timezone'] = 'Asia/Katmandu'; break;
	case '6':    $_SESSION['timezone'] = 'Asia/Novosibirsk'; break;
	case '6.3':  $_SESSION['timezone'] = 'Asia/Rangoon'; break;
	case '7':    $_SESSION['timezone'] = 'Asia/Krasnoyarsk'; break;
	case '8':    $_SESSION['timezone'] = 'Asia/Irkutsk'; break;
	case '9':    $_SESSION['timezone'] = 'Asia/Yakutsk'; break;
	case '9.3':  $_SESSION['timezone'] = 'Australia/Darwin'; break;
	case '10':   $_SESSION['timezone'] = 'Asia/Vladivostok'; break;
	case '11':   $_SESSION['timezone'] = 'Asia/Magadan'; break;
	case '12':   $_SESSION['timezone'] = 'Pacific/Auckland'; break;
	case '13':   $_SESSION['timezone'] = 'Pacific/Tongatapu'; break;
	default:     $_SESSION['timezone'] = 'Europe/Moscow'; break; 
}