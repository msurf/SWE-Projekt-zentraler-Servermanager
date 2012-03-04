<?php $this->pageTitle=Yii::app()->name; ?>
<h1>Server Status</h1>
<?php
$statusColorMap = array("off" => "red", "busy" => "yellow", "ok" => "green");
$accordionArray = array();
foreach ($clients as $client) {
    $bullet = '<span style="font-size: 16px; color: ' . $statusColorMap[$client["status"]] . '">&bull;</span>';
    $key = $bullet . " " . $client["name"];
    if ($client["status"] == "off") {
        $value = $client["name"] . " is offline";
    } else {
        $value = "<ul>";
        foreach ($client["software"] as $name => $status) {
            if ($status == "on") {
                $value .= "<li>$name</li>";
            }
        }
        $value .= "</ul>";
    }
    $accordionArray[$key] = $value;
}
$this->widget('zii.widgets.jui.CJuiAccordion', array(
        'panels'=>$accordionArray,
        // additional javascript options for the accordion plugin
        // 'options'=>array(
       //  'animated'=>'bounceslide',
      //  ),
));

/*
$this->widget('zii.widgets.jui.CJuiTabs', array(
    'tabs'=>array(
        'StaticTab 1'=>'Content for tab 1',
        'StaticTab 2'=>array('content'=>'Content for tab 2', 'id'=>'tab2'),
        // panel 3 contains the content rendered by a partial view
        //    'AjaxTab'=>array('ajax'=>$ajaxUrl),
    ),
    // additional javascript options for the tabs plugin
    'options'=>array(
        'collapsible'=>true,
    ),
));
*/
?>
