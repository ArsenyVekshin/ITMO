<?php
$start_time = microtime(true);

header("Content-Type: application/json; charset=utf-8");

function abort(int $code, string $message) {
    http_response_code($code);
    echo "{\"message\":\"$message\"}";
    exit;
}

$method = $_SERVER["REQUEST_METHOD"];
if ($method !== "GET") {
    abort(405, "$method not allowed");
}

$x = $_GET["x"];
$y = $_GET["y"];
$r = $_GET["r"];

if ($x < -5 || $x > 5) abort(400, "X not in range!"); //len
if ($y < -5 || $y > 3) abort(400, "Y not in range!");
if ($r < 1 || $r > 3) abort(400, "R not in range!");

$hit = false;

if ($x <= 0 && $y >= 0) {
    $hit = ($x*$x + $y*$y) <= $r*$r ; //($x/2>= -$r) && ($y <= $r);
} elseif ($x <= 0 && $y <= 0) {
    $hit = ($x >= -$r) && ($y >= -$r);
} elseif ($x >= 0 && $y <= 0) {
    $hit = ($x - $y) <= $r;
}

$end_time = microtime(true);
$script_time = number_format(($end_time - $start_time) * 1000, 3, '.', '');
date_default_timezone_set('Europe/Moscow');
$end_datetime = date("d/m/Y H:i:s", (int) $end_time);


?>
{
"x": <?=$x?>,
"y": <?=$y?>,
"r": <?=$r?>,
"result": <?=$hit ? '"area"' : '"miss"' ?>,
"now": "<?=$end_datetime?>",
"script_time": <?=$script_time?>
}