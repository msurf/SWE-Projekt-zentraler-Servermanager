<?php

require_once dirname(__FILE__) . '/dcCommand.php';
require_once dirname(__FILE__) . '/dcConnectionInterface.php';

class dcConnection implements dcConnectionInterface {
    private $host;
    private $port;
    
    const EOT = "\x04";
    
    public function __construct($host, $port = 5550) {
        $this->host = $host;
        $this->port = (int) $port;
    }
    
    public function sendCommand(dcCommand $command) {
        $sock = fsockopen($this->host, $this->port, $errno, $errstr, 1);
        if (!is_resource($sock)) {
            
            throw new Exception("Could not establish connection to {$this->host}:{$this->port} - [$errno] $errstr");
        }
        stream_set_timeout($sock, 1);
        fwrite($sock, $command->toXml() . self::EOT);
        $xml = "";
        $retrycount = 0;
        do {
            $xml .= fread($sock, 4096);
            $retrycount++;
        } while (!feof($sock) && $retrycount < 5);
        fclose($sock);
        return dcCommand::fromXml($xml);
    }
}
