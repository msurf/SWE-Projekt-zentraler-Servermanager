<?php

require_once 'protected/extensions/daemon-communication/dcConnection.php';

$command = dcCommand::createPageToHostCommand();
$command->setName("getrepoliste");
echo $command->toXml();
$conn = new dcConnection("localhost");
var_dump($conn->sendCommand($command));