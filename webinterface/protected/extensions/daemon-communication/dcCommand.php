<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of dcCommand
 *
 * @author Tobias
 */
class dcCommand implements dcCommandInterface {
    private $client;
    private $clientId;
    private $direction;
    private $from;
    private $ftpFile;
    private $ftpIp;
    private $id;
    private $info;
    private $name;
    private $parameter;
    private $password;
    private $program;
    private $query;
    private $status;
    private $time;
    private $url;
    private $user;
    
    public function getClient() {
        return $this->client;
    }
    public function getClientId() {
        return $this->clientId;
    }
    public function getDirection() {
        return $this->direction;
    }
    public function getFrom() {
        return $this->from;
    }
    public function getFtpFile() {
        return $this->ftpFile;
    }
    public function getFtpIp() {
        return $this->ftpIp;
    }
    public function getId() {
        return $this->id;
    }
    public function getInfo() {
        return $this->info;
    }
    public function getName() {
        return $this->name;
    }
    public function getParameter() {
        return $this->parameter;
    }
    public function getPassword() {
        return $this->password;
    }
    public function getProgram() {
        return $this->program;
    }
    public function getQuery() {
        return $this->query;
    }
    public function getStatus() {
        return $this->status;
    }
    public function getTime() {
        return $this->time;
    }
    public function getUrl() {
        return $this->url;
    }
    public function getUser() {
        return $this->user;
    }
    public function setClient($client) {
        $this->client = (string) $client;
    }
    public function setClientId($clientId) {
        $this->clientId = (int) $clientId;
    }
    public function setDirection($direction) {
        $this->direction = (string) $direction;
    }
    public function setFrom($from) {
        $this->from = (string) $from;
    }
    public function setFtpFile($ftpFile) {
        $this->ftpFile = (string) $ftpFile;
    }
    public function setFtpIp($ftpIp) {
        $this->ftpIp = (string) $ftpIp;
    }
    public function setId($id) {
        $this->id = (int) $id;
    }
    public function setInfo($info) {
        if (is_array($info)) {
            $this->info = $info;
        } else if (is_object($info) && $info instanceof Traversable) {
            $this->info = iterator_to_array($info);
        } else {
            // Neither array nor traversable => try string parsing!
            $result = array();
            $temp = explode("#", $info);
            foreach ($temp as $v) {
                if (strpos($v, ":") === false) {
                    $result[] = $v;
                } else {
                    list($key, $value) = explode(":", $v, 2);
                    $result[$key] = $value;
                }
            }
            $this->info = $result;
        }
    }
    public function setName($name) {
        $this->name = (string) $name;
    }
    public function setParameter($parameter) {
        $this->parameter = (string) $parameter;
    }
    public function setPassword($password) {
        $this->password = (string) $password;
    }
    public function setProgram($program) {
        $this->program = (string) $program;
    }
    public function setQuery($query) {
        $this->query = (string) $query;
    }
    public function setStatus($status) {
        $this->status = (int) $status;
    }
    public function setTime($time) {
        if (is_object($time) && $time instanceof DateTime) {
            $this->time = $time;
        } else {
            $this->time = DateTime::createFromFormat("U", $time / 1000);
        }
    }
    public function setUrl($url) {
        $this->url = (string) $url;
    }
    public function setUser($user) {
        $this->user = (string) $user;
    }
    public function toXml() {
        throw new BadMethodCallException("not yet implemented");
    }
    public static function fromXml($xml) {
        throw new BadMethodCallException("not yet implemented");
    }
    public static function createPageToHostCommand() {
        $cmd = new dcCommand();
        $cmd->setFrom("website");
        $cmd->setDirection("page2host");
        $cmd->setStatus(100);
        return $cmd;
    }
}
