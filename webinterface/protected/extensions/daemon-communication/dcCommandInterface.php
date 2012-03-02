<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tobias
 */
interface dcCommandInterface {
    /**
     * @param string $ftpFile
     * @return null 
     */
    public function setFtpFile($ftpFile);
    /**
     * @return string
     */
    public function getFtpFile();
    /**
     * @param string $ftpIp
     * @return null
     */
    public function setFtpIp($ftpIp);
    /**
     * @return string 
     */
    public function getFtpIp();
    
    /**
     * @param int $id
     * @return null 
     */
    public function setId($id);
    /**
     * @return int 
     */
    public function getId();
    /**
     * @param string $url
     * @return null 
     */
    public function setUrl($url);
    /**
     * @return string 
     */
    public function getUrl();
    /**
     * @param string $client
     * @return null 
     */
    public function setClient($client);
    /**
     * @return string 
     */
    public function getClient();
    /**
     * @param int $clientId
     * @return null 
     */
    public function setClientId($clientId);
    /**
     * @return int 
     */
    public function getClientId();
    /**
     * @param string $direction
     * @return null 
     */
    public function setDirection($direction);
    /**
     * @return string 
     */
    public function getDirection();
    /**
     * @param string $from
     * @return null 
     */
    public function setFrom($from);
    /**
     * @return string 
     */
    public function getFrom();
    /**
     * If $info is a string, it will be parsed into an array using the following
     * algorithm:
     * # separates array elements
     * : separates keys and values
     * 
     * @param string|array|Traversable $info
     * @return null 
     */
    public function setInfo($info);
    /**
     * @return array 
     */
    public function getInfo();
    /**
     * @param string $name
     * @return null 
     */
    public function setName($name);
    /**
     * @return string 
     */
    public function getName();
    /**
     * @param string $parameter
     * @return null 
     */
    public function setParameter($parameter);
    /**
     * @return string 
     */
    public function getParameter();
    /**
     * @param string $password
     * @return null 
     */
    public function setPassword($password);
    /**
     * @return string 
     */
    public function getPassword();
    /**
     * @param string $program
     * @return null 
     */
    public function setProgram($program);
    /**
     * @return string 
     */
    public function getProgram();
    /**
     * @param string $query
     * @return null 
     */
    public function setQuery($query);
    /**
     * @return string 
     */
    public function getQuery();
    /**
     * @param int $status
     * @return null 
     */
    public function setStatus($status);
    /**
     * @return int 
     */
    public function getStatus();
    /**
     * If $time is a string, we will try to parse it using DateTime's constructor
     * 
     * @param string|DateTime $time
     * @return null  
     * @throws Exception if turning the string in $time into a DateTime object fails
     */
    public function setTime($time);
    /**
     * @return DateTime 
     */
    public function getTime();
    /**
     * @param string $user
     * @return null 
     */
    public function setUser($user);
    /**
     * @return string 
     */
    public function getUser();
    /**
     * @return string 
     */
    public function toXml();
    /**
     * @param string $xml
     * @return dcCommandInterface 
     */
    public static function fromXml($xml);
    /**
     * @return dcCommandInterface 
     */
    public static function createPageToHostCommand();
}