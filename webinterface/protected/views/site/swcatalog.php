<h1>Software Catalog</h1>
<?php
$software=array(array('id'=>1,'name'=>'css','description'=>'CounterStrike:Source'));

// $software=array($software); 
$dataProvider=new CArrayDataProvider($software, array(
    'id'=>'data',
    'keyField'=>'id',
    'sort'=>array(
        'attributes'=>array(
             'id',
        ),
    ),
    'pagination'=>array(
        'pageSize'=>10,
    ),
));

$this->widget('zii.widgets.grid.CGridView', array(
    'dataProvider'=>$dataProvider,
     'id'=>'swctg',
     'columns'=>array(
	 //'id',
        'name',          // display the 'title' attribute
        'description',  // display the 'name' attribute of the 'category' relation

	 array(
   	   'class'=>'CButtonColumn',
	   'id'=>'swupdate',
    	   'template'=>'{update}{delete}',
    	   'buttons'=>array
    	   (

             'update' => array
              (
	       'label'=>' Change ',
		 'url'=>'Yii::app()->controller->createUrl("site/swcatalog",array("id" => 1,"cmd"=>"upd"))',
            	// 'click'=>'function(){alert("Test "+$.fn.yiiGridView.getSelection(swctg))',
        	),
             'delete' => array
              (
	       'label'=>' Delete',
        	 'url'=>'Yii::app()->controller->createUrl("site/swcatalog",array("id"=>1,"cmd"=>"del"))',
            	// 'click'=>'function(){alert("hallo");)',
        	),
 	   	),
	),
       
)));
?>
<div class="form">
<?php echo CHtml::beginForm(Yii::app()->controller->createUrl("site/swcatalog",array("cmd"=>"crt"))); ?>
 
    <?php // echo CHtml::errorSummary($model); ?>
 
    <div class="row">
         <?php echo CHtml::TextField('name','',array('size'=>30,)) ?>
         <?php echo CHtml::TextField('description','',array('size'=>102,)) ?>
	  <?php echo CHtml::submitButton('Create') ?>
    </div>
 
 <?php echo CHtml::endForm(); ?>
</div><!-- form -->
