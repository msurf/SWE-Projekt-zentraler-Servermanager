b<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of dcConnection
 *
 * @author Tobias
 */
interface dcConnectionInterface {
    
    /**
     * @return dcCommandInterface 
     */
    public function sendCommand(dcCommand $command);
}
