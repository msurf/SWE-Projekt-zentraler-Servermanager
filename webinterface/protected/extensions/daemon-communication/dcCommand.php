<?php

require_once dirname(__FILE__) . '/dcCommandInterface.php';
require_once dirname(__FILE__) . '/../fluentdom/src/FluentDOM.php';

class dcCommand implements dcCommandInterface {
    private $client;
    private $clientId;
    private $direction;
    private $from;
    private $ftpFile;
    private $ftpIp;
    private $id;
    /**
     *
     * @var array 
     */
    private $info;
    private $name;
    private $parameter;
    private $password;
    private $program;
    private $query;
    private $status;
    /**
     * @var DateTime 
     */
    private $time;
    private $url;
    private $user;
    
    private static $attributeMap = array(
        "client"     => array("property" => "client",    "type" => "string"),
        "clientId"   => array("property" => "clientID",  "type" => "int"),
        "direction"  => array("property" => "direction", "type" => "string"),
        "from"       => array("property" => "from",      "type" => "string"),
        "ftpFile"    => array("property" => "FTP_File",  "type" => "string"),
        "ftpIp"      => array("property" => "FTP_IP",    "type" => "string"),
        "id"         => array("property" => "ID",        "type" => "int"),
        "info"       => array("property" => "info",      "type" => "string"),
        "name"       => array("property" => "name",      "type" => "string"),
        "parameter"  => array("property" => "parameter", "type" => "string"),
        "password"   => array("property" => "password",  "type" => "string"),
        "program"    => array("property" => "program",   "type" => "string"),
        "query"      => array("property" => "query",     "type" => "string"),
        "status"     => array("property" => "status",    "type" => "int"),
        "time"       => array("property" => "time",      "type" => "string"),
        "url"        => array("property" => "URL",       "type" => "string"),
        "user"       => array("property" => "user",      "type" => "string")
    );
    private static $attributeMapInverse = array(
        "client"     => array("property" => "client",    "type" => "string"),
        "clientID"   => array("property" => "clientId",  "type" => "int"),
        "direction"  => array("property" => "direction", "type" => "string"),
        "from"       => array("property" => "from",      "type" => "string"),
        "FTP_File"   => array("property" => "ftpFile",   "type" => "string"),
        "FTP_IP"     => array("property" => "ftpIp",     "type" => "string"),
        "ID"         => array("property" => "id",        "type" => "int"),
        "info"       => array("property" => "info",      "type" => "string"),
        "name"       => array("property" => "name",      "type" => "string"),
        "parameter"  => array("property" => "parameter", "type" => "string"),
        "password"   => array("property" => "password",  "type" => "string"),
        "program"    => array("property" => "program",   "type" => "string"),
        "query"      => array("property" => "query",     "type" => "string"),
        "status"     => array("property" => "status",    "type" => "int"),
        "time"       => array("property" => "time",      "type" => "string"),
        "URL"        => array("property" => "url",       "type" => "string"),
        "user"       => array("property" => "user",      "type" => "string")
    );
    
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
        $fd = new FluentDom();
        $fd->contentType = "xml";
        $fd->document->encoding = "utf-8";
        $rootNode = $fd->append('<java version="1.6.0_23" class="java.beans.XMLDecoder" />');
        $objectNode = $rootNode->append('<object class="xml.Command" />');
        foreach (self::$attributeMap as $key => $attribute) {
            if ($this->$key == null) {
                continue;
            }
            if ($key == "info") {
                $elements = array();
                foreach ($this->info as $k => $v) {
                    $tmp = "";
                    if (!is_numeric($k)) {
                        $tmp .= "$k:";
                    }
                    $elements[] = $tmp . $v;
                }
                $value = implode("#", $elements);
            } else if ($key == "time") {
                $value = $this->time->format("U") * 1000;
            } else {
                $value = $this->$key;
            }
            $prop = $objectNode->append('<void property="' . $attribute["property"] . '" />');
            $prop->append('<'. $attribute["type"] . ' />')->text($value);
        }
        return $fd->__toString();
    }
    public static function fromXml($xml) {
        $fd = new FluentDom();
        $fd->load($xml);
        $object = $fd->find("/java/object");
        if ($object->attr("class") != "xml.Command") {
            throw new Exception("This method can only unserialize Java objects of type xml.Command!");
        }
        $dcCommand = new dcCommand();
        $children = $object->find("./void");
        foreach ($children as $child) {
            $property = $child->attr("property");
            if (!isset(self::$attributeMapInverse[$property])) {
                continue;
            }
            $attributeMap = self::$attributeMapInverse[$property];
            $setter = "set" . ucfirst($attributeMap["property"]);
            if ($attributeMap["type"] == "string") {
                $dcCommand->$setter($child->find("./string")->text());
            } elseif ($attributeMap["type"] == "int") {
                $dcCommand->$setter($child->find("./int")->text());
            }
        }
        return $dcCommand;
    }
    public static function createPageToHostCommand() {
        $cmd = new dcCommand();
        $cmd->setFrom("website");
        $cmd->setDirection("page2host");
        $cmd->setStatus(100);
        return $cmd;
    }
}
