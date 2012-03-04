
<?php

// software(name,description,file,ftpip) values ('css','Installiert CounterStrike:Source','css.tar.gz','192.168.1.21')

$rawhard=array(array('id'=>1,'cpu'=>'C2D@2.667Ghz','ram'=>'2024Mb','arch'=>'i386'),
		 array('id'=>2,'cpu'=>'AMD@1.8Ghz','ram'=>'4096Mb','arch'=>'amd64'));
$rawsoft=array(array('id'=>1,'name'=>'css','description'=>'CounterStrike:Source','file'=>'css.tar.gz','ftpip'=>'192.168.1.21','soft'=>1,'service'=>0),
		 array('id'=>2,'name'=>'css','description'=>'CounterStrike:Source','file'=>'css.tar.gz','ftpip'=>'192.168.1.21','soft'=>0,'service'=>1)	
);

// echo $rawhard[0]['id'];
$client=$data['id'];
$rawhard=array($rawhard[$client-1]);
$rawsoft=array($rawsoft[$client-1]); 
$dataProvider=new CArrayDataProvider($rawhard, array(
    'id'=>'id',
    'sort'=>array(
        'attributes'=>array(
             'id',
        ),
    ),
    'pagination'=>array(
        'pageSize'=>1,
    ),
));


$this->widget('zii.widgets.grid.CGridView', array(
    'dataProvider'=>$dataProvider,
    'columns'=>array(
        'cpu',          // display the 'title' attribute
        'ram',  // display the 'name' attribute of the 'category' relation
        'arch',  // display the 'name' attribute of the 'category' relation	
   )
     ,
));

$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'updhwinfoname_'.$client,
		'caption'=>'Update Hardwareinfo',
		'htmlOptions'=>array("style"=>'text-align:center'),
		'value'=>'updhwinfoval_'.$client,
		'onclick'=>'js:function(){alert("Update Hardwareinfo"); this.blur(); return false;}',
		)
);

$softid=$rawsoft[0]['name'];

$dataProvider=new CArrayDataProvider($rawsoft, array(
    'id'=>'id',
    'sort'=>array(
        'attributes'=>array(
             'id',
        ),
    ),
    'pagination'=>array(
        'pageSize'=>10,
    ),
));


$instarr=array(
   	   'class'=>'CButtonColumn',
	   'id'=>'Install_'.$softid.'_'.$client,
    	   'template'=>'{install}',
    	   'buttons'=>array
    	   (
             'install' => array
              (
	       'label'=>'<span style="font-size:16px;color:red;">&bull;</span> Install',
        	 'url'=>'Yii::app()->controller->createUrl("site/commands",array("id"=>$data["id"],"cmd"=>"install","soft"=>"css"))',
            	 // 'click'=>'function(){$("#dialog_id").dialog("open"); return false;}',
        	),
 	   	),
	);

$removearr=array(
   	   'class'=>'CButtonColumn',
	   'id'=>'Remove_'.$softid.'_'.$client,
    	   'template'=>'{remove}',
    	   'buttons'=>array
    	   (
             'remove' => array
              (
	       'label'=>'<span style="font-size:16px;color:green;">&bull;</span> Delete',
        	 'url'=>'Yii::app()->controller->createUrl("site/commands",array("id"=>$data["id"],"cmd"=>"remove","soft"=>"css"))',
        	),
 	   	),
	);


if ( $rawsoft[0]['soft']==1) { $softarr=$removearr;} else {$softarr=$instarr;};

$servstart=	array
	(
   	   'class'=>'CButtonColumn',
	   'id'=>'service',
    	   'template'=>'{start}',
    		'buttons'=>array
    		(

       	 'start' => array
       	 (
		 'label'=>'<span style="font-size:16px;color:red;">&bull;</span>  Start',
        	 'url'=>'Yii::app()->controller->createUrl("site/commands",array("id"=>$data["id"],"cmd"=>"start","soft"=>"css"))',
            //	 'click'=>'function(){$("#dialog_id").dialog("open"); return false;}',
        	),
 	   	),
	);
$servstop=	array
	(
   	   'class'=>'CButtonColumn',
	   'id'=>'service',
    	   'template'=>'{stop}',
    		'buttons'=>array
    		(

       	 'stop' => array
       	 (
		 'label'=>'<span style="font-size:16px;color:green;">&bull;</span> Stop',
        	 'url'=>'Yii::app()->controller->createUrl("site/commands",array("id"=>$data["id"],"cmd"=>"stop","soft"=>"css"))',
            //	 'click'=>'function(){$("#dialog_id").dialog("open"); return false;}',
        	),
 	   	),
	);

if ( $rawsoft[0]['service']==1) { $service=$servstop;} else {$service=$servstart;};


$this->widget('zii.widgets.grid.CGridView', array(
    'dataProvider'=>$dataProvider,
    'columns'=>array(
        'name',          // display the 'title' attribute
        'description',  // display the 'name' attribute of the 'category' relation
        'file',  // display the 'name' attribute of the 'category' relation
        'ftpip',  // display the 'name' attribute of the 'category' relation

	$softarr,

 
	$service,
	 
   ),
	
));



$this->widget('zii.widgets.jui.CJuiButton',
	array(
		'name'=>'updswinfoname_'.$client,
		'caption'=>'Update Softwareinfo',
		'value'=>'updswinfoval_'.$client,
		'onclick'=>'js:function(){alert("Update Softwareinfo"); this.blur(); return false;}',
		)
);
?>	
