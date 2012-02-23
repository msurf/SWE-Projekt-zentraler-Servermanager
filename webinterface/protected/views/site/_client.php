<?php
$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'button1',
		'caption'=>'Install Package',
		'value'=>'xyz',
		'onclick'=>'js:function(){alert("Installo button clicked"); this.blur(); return false;}',
		)
);

$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'button2',
		'caption'=>'Update Hardwareinfo',
		'value'=>'xyz',
		'onclick'=>'js:function(){alert("Installo button clicked"); this.blur(); return false;}',
		)
);
$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'button3',
		'caption'=>'Update Softwareinfo',
		'value'=>'xyz',
		'onclick'=>'js:function(){alert("Installo button clicked"); this.blur(); return false;}',
		)
);
$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'button4',
		'caption'=>'Start/Stop Service',
		'value'=>'xyz',
		'onclick'=>'js:function(){alert("Installo button clicked"); this.blur(); return false;}',
		)
);
?>	
