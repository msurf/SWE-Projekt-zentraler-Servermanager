<?php $this->pageTitle=Yii::app()->name; ?>
<h1>Server Status</h1>
<?php
$this->widget('zii.widgets.jui.CJuiAccordion', array(
        'panels'=>array(
        '<span style="font-size:16px;color:red;">&bull;</span> debian 1'=>'debian 1 is not reachable',
        '<span style="font-size:16px;color:red;">&bull;</span> debian 2'=>'debian 2 is not reachable',

        ),
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
