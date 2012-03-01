<h1>Server Commands</h1>
<?php
$this->widget('zii.widgets.jui.CJuiAccordion', array(
        'panels'=>array(
	'client 1'=>$this->renderPartial('_client',null,true),
	'client 2'=>$this->renderPartial('_client',null,true),
	'client 3'=>$this->renderPartial('_client',null,true),
	'client 4'=>$this->renderPartial('_client',null,true),
	'client 5'=>$this->renderPartial('_client',null,true),	     
        ),
        // additional javascript options for the accordion plugin
        // 'options'=>array(
       //  'animated'=>'bounceslide',
      //  ),
));
?>