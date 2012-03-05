<h1>Server Commands</h1>
<?php
$clients=array(array('id'=>1,'name'=>'debian1'),array('id'=>2,'name'=>'debian2'));	
$rawhard=array(array('id'=>1,'cpu'=>'C2D@2.667Ghz','ram'=>'2024Mb','arch'=>'i386'),array('id'=>2,'cpu'=>'AMD@1.8Ghz','ram'=>'4096Mb','arch'=>'amd64'));
$rawsoft=array(array('id'=>1,'name'=>'css','description'=>'Installiert CounterStrike:Source','file'=>'css.tar.gz','ftpip'=>'192.168.1.21'));



// echo "<h1> clients $clients[1][1]</h1>";
$panels=array();
foreach ($clients as $client) {
       // $tmp=array($client=>$this->renderPartial('_client',$clients,true));
	$panels=array_merge($panels,array());
}




$this->widget('zii.widgets.jui.CJuiAccordion', array(
	'panels'=>array(
	 $clients[0]['name']=>$this->renderPartial('_client',array('data'=>$clients[0]),true),
	 $clients[1]['name']=>$this->renderPartial('_client',array('data'=>$clients[1]),true),
	//clients[2]=>$this->renderPartial('_client',array('data'=>$clients[2]),true),
        ),
        // additional javascript options for the accordion plugin
       'options'=>array(
       //'animated'=>'bounceslide',
       ),
));

echo "<hl> <br/> <br/> ";

$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'updateswcatalog',
		'buttonType'=>'link',
		'caption'=>'Update Software Catolog',
		'htmlOptions'=>array("style"=>'text-align:center'),
		'value'=>'updswctg',
		'url'=>Yii::app()->controller->createUrl("site/swcatalog",array()),
 		// 'onclick'=>'http://www.google.de',
		// 'onclick'=>'js:function(){alert("Update Software Catlog"); this.blur(); return false;}',
		)
);


?>
