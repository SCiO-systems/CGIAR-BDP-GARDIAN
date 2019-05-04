<?php

require 'php_elastic/vendor/autoload.php';
require 'php_functions/publication_functions.php';
use Elasticsearch\ClientBuilder;



function utf8_converter($array)
{
    array_walk_recursive($array, function(&$item, $key){
        if(!mb_detect_encoding($item, 'utf-8', true)){
                $item = utf8_encode($item);
        }
    });

    return $array;
}


function searchElastic($keyword){

        $hosts = ['127.0.0.1:9200'];

        $client = ClientBuilder::create()
                    ->setHosts($hosts)
                    ->build();



        $params_datasets = [
                'index' => 'datasets_2_0',
                'type' => 'datasets_2_0_records',
                'size' => 5,
                'from' => 0,
                'body' => [
                        'query' => [
                                'match' => [
                                        '_all' => $keyword
                                ]
                        ]
                ]
        ];


        $response_dataset = $client->search($params_datasets);

        $response=utf8_converter($response_dataset);

        return $response;



}





$id = $_GET["id"];

$jdFAIR=retrieveFAIR($id);
$publication_metadata = retrievePublication($id);
$json_geonames_terms = retrieveGeonames($id);
$authors = retrieveAuthors($id);



$authors_list = '';
$first = 1;
foreach($authors as &$author_value){


	if($first == 1){
		$authors_list = $author_value;
		$first = 0;
	}else{
	        $authors_list = $authors_list.", ".$author_value;
	}
}





$no_geo = empty(json_decode($json_geonames_terms));
$refers_to = '';
if($no_geo == FALSE){
        $refers_to = '<div class="boxed post-meta post-meta-files white-bg">
          <h3 class="post-meta__title h2">REFERS TO</h2>
          <div class="post-meta__content">
                <div id="map_small"  style="width: 100%; height: 200px;margin-bottom: 30px;"></div>
                <center><a href="" class="btn button button-orange toggle-lightbox-2">VIEW LARGE</a></center>
          </div>
</div>
';

}




$summary = $publication_metadata["publication"]["summary"];

$block_summary = '';
if(strlen($summary)>0){

        $block_summary = '
        <div class="boxed column small-12 post-content white-bg">
            <h3 class="h1">SUMMARY</h3>
                <div class="post-text" style="font-size:21px">
                        <p>'.$summary.'</p>
                </div>
        </div> 
';

}






$title = $publication_metadata["publication"]["title"];
$citation = $publication_metadata["publication"]["citation"];

$category = $publication_metadata["publication"]["category"];
$doi = $publication_metadata["publication"]["doi"];
$hdl = $publication_metadata["publication"]["hdl"];
$providers = $publication_metadata["publication"]["providers"];




$permalinks = '';
if(strlen($doi)>5){

if( strpos( $doi, 'https://dx.doi.org/' ) !== false ) {
	
	$permalinks = $permalinks.'<div class="post-meta__content fa fa-link"><a class="post-link " target="_blank" href="'.$doi.'">DOI</a></div><br>';
}
else {
	$permalinks = $permalinks.'<div class="post-meta__content fa fa-link"><a class="post-link " target="_blank" href="https://dx.doi.org/'.$doi.'">DOI</a></div><br>';
}
}

if(strlen($hdl)>5){

        if(strpos($hdl, 'http') === false){

                $permalinks = $permalinks.'<div class="post-meta__content fa fa-link"><a class="post-link" target="_blank" href="https://hdl.handle.net/'.$hdl.'">HDL</a></div><br>';

        }else{

                $permalinks = $permalinks.'<div class="post-meta__content fa fa-link"><a class="post-link" target="_blank" href="'.$hdl.'">HDL</a></div><br>';

        }

}


foreach($providers as &$provider_value){

	$permalinks = $permalinks.'<div class="post-meta__content fa fa-link"><a class="post-link " target="_blank" href="'.$provider_value["provider_link"].'">URL</a></div><br>';
}



$providers_id = '';
$first = 1;

$hasmany = 0;
foreach($providers as &$provider_value){

	preg_match('#\((.*?)\)#', $provider_value["provider_id"], $match);

       	$provider_id = $match[1];

	if($first == 1){
		$providers_id = $provider_id;;
			$first = 0;
	}else{
		 $providers_id = $providers_id.', '.$provider_id;
	}

	$hasmany.=1;

}

if($hasmany == 1){

	foreach($providers as &$provider_value){

        	$provider_id = $provider_value["provider_id"];
               	$providers_id = $provider_id;

	}

}	
$permalinks = $permalinks.$providers_text;



$search_title = $publication_metadata["publication"]["title"];

$res_0=searchElastic($search_title);
//print_r($res_0);
$related_datasets='<ul class="related">';

$double_check = array();
for($l=0;$l<5;$l++){
	$dataset_id = 0;
	if($res_0["hits"]["total"]>0){

		$dataset_id = $res_0["hits"]["hits"][$l]["_source"]["dataset_id"];
        	//$citation = $res_0["hits"]["hits"][$l]["_source"]["cg.identifier.citation"];
		$title_dataset = $res_0["hits"]["hits"][$l]["_source"]["cg.title"];
		$date = $res_0["hits"]["hits"][$l]["_source"]["cg.date.production"];

		//container_name
		//$container_name = retrieveContainer($publication_id);

	}



	if( in_array($dataset_id, $double_check)){

	}else if ($dataset_id!=0) {

		$list_item= '<li class="related__item">
                	<a style="font-size:21px" href="/dataset.php?id='.$dataset_id.'" class="related__item--link">'.$title_dataset.'</a></li><br>';

		array_push($double_check,$dataset_id);

	}

	$related_datasets.=$list_item;

}

$related_datasets.='</ul>';


$json_agrovoc=retrieveAGROVOC($id);
$no_onto = empty(json_decode($json_agrovoc));

$ontology_terms = '';
if($no_onto == FALSE){
        $ontology_terms =  '<div class="boxed column small-12 post-content white-bg">
            <h3 class="h1">KEYWORDS</h3>
                <div class="post-text">
                        <div id="agrovoc_cloud" style="margin: 0 auto; width: 100%;max-width: 600px;height: 200px;"> </div>
                </div>
        </div>';

}


$head = '<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>GARDIAN Publication</title>
    <link href="https://fonts.googleapis.com/css?family=Roboto+Condensed:300,300i,400,400i,700,700i&amp;subset=latin-ext" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="assets/bower_components/openlayers/ol.js"></script>
    <script src="assets/bower_components/angular/angular.min.js"></script>
    <script src="assets/bower_components/angular-sanitize/angular-sanitize.min.js"></script>
    <script src="assets/bower_components/underscore/underscore-min.js"></script>
    <script src="assets/bower_components/angular-route/angular-route.min.js"></script>
    <script src="assets/bower_components/ngstorage/ngStorage.min.js"></script>
    <script src="assets/bower_components/angular-openlayers-directive/dist/angular-openlayers-directive.min.js"></script>
	<script src="assets/js/app.js"></script>
    <link rel="stylesheet" href="assets/bower_components/openlayers/ol.css" type="text/css">

	<script src="vendor/popper/popper.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>   
    <script src="vendor/datatables/jquery.dataTables.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.js"></script>
        <script src="vendor/chart.js/Chart.min.js"></script>


    <script src="assets/js/scripts.build.js"></script>
    <script src="assets/js/map.js"></script>

    <script src="assets/js/highfair.js"></script>
    <!-- jvecrtor map demo script -->
   	 <script src="assets/js/map.js"></script>
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/highcharts-more.js"></script>
	<script src="https://code.highcharts.com/modules/solid-gauge.js"></script>




	<script type="text/javascript">var jdFAIR ='.$jdFAIR.'</script>
	<script type="text/javascript">var jGeoTerms ='.$json_geonames_terms.'</script>
	<script type="text/javascript">var jTerms ='.$json_agrovoc.'</script>


	<script src="assets/js/jqcloud.min.js"></script>
        <script src="assets/js/agrovoc_cloud.js"></script>
	<script src="assets/js/collapse.js"></script>
        <script src="assets/js/newMap.js"></script>
        <script src="assets/js/largeMap.js"></script>
        <script src="assets/js/privacyView.js"></script>
        <script src="assets/js/termsView.js"></script>
	<script src="assets/js/disclaimerView.js"></script>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-107803386-4"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag("js", new Date());

  gtag("config", "UA-107803386-4");
</script>




  </head>
';

$head_end = '</html>';

$body = ' <body>
    <!-- start header -->
    <header class="site-header">
      <div class="site-header__top black-bg">
        <div class="row">
          <div class="column shrink">
            <figure class="logo">
              <a href="./index.php"><img src="assets/img/logo.png" /></a>
            </figure>
          </div>
	  <div class="column align-self-bottom">
		<nav class="main-menu">
		<ul class="main-menu__items-wrap clearfix">
                	<li class="main-menu__item"><a style="color:inherit" href="./index.php" title="Home Page">home</a></li>
                	<li class="main-menu__item current"><a style="color:inherit" href="./search.php" title = "See your search results here">search</a></li>
                	<li class="main-menu__item"><a style="color:inherit" href="./exploration.php" title = "Explore geo-referenced or semantically-enabled data here">data exploration</a></li>
                	<li class="main-menu__item" style="float:right"><a style="color:inherit" href="./analytics.php">analytics</a></li>
                	<li class="main-menu__item" style="float:right"><a style="color:inherit" href="./services.php">services</a></li>
                	<li class="main-menu__item" style="float:right"><a style="color:inherit" href="./about.php">about</a></li>

		</ul>




            </nav>
  
	</div>
        </div>
      </div>
      <div class="site-header__middle gray-bg" ng-app="GardianSearchApp" ng-controller="searchBootstrapController">
        <div class="row">
          <div class="column small-12">
            <form class="search-form" ng-submit="goToSearch()">
              <div class="row">
                <div class="column to-expand">
                  <div class="input-group">
                  <input id="keywords" ng-model="$root.query" class="input-group-field search-form__input-text" type="text" name="keyword" placeholder="{{$root.query && $root.query !=\'*\' ? $root.query : \'Search for\'}}" />
                  
                  <span class="input-group-label fa fa-search"></span>
                </div>
                </div>
                <div class="column shrink text-center">
                  <fieldset>
                    <div class="checkbox">
                      <input class="search-form__checkbox" type="radio" name="r1" ng-model="access.value" id="checkbox-all" value="all" checked/><label for="checkbox-all">All</label>
                    </div>
                    <div class="checkbox">
                      <input class="search-form__checkbox" type="radio" name="r1" ng-model="access.value" id="checkbox-open" value="open" /><label for="checkbox-open">Open</label>
                    </div>
                    <div class="checkbox">
                      <input class="search-form__checkbox" type="radio" name="r1" ng-model="access.value" id="checkbox-limited" value="limited" /><label for="checkbox-limited">Restricted</label>
                    </div>
                  </fieldset>
                </div>
                <div class="column shrink text-center">
                    <fieldset>
                      <div class="checkbox">
                        <input class="search-form__checkbox" type="radio" name="o1" ng-model="operator.value" id="checkbox-and" value="and" checked/><label for="checkbox-and">And</label>
                      </div>
                      <div class="checkbox">
                        <input class="search-form__checkbox" type="radio" name="o1" ng-model="operator.value" id="checkbox-or" value="or" /><label for="checkbox-or">Or</label>
                      </div>
                  </fieldset>
                </div>
                <div class="column shrink">
                  <button class="btn button button-white" type="submit">SEARCH</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </header>
    <!-- end header -->
    <!-- start main -->
    <main class="main row">
      <!-- start main content -->
      <artilce class="column small-12 row medium-9 main-content ">
          <div class="boxed column small-12 post-title white-bg">
	    <h2 class="h3" style="font-size:23px">'.$title.'</h2>
	    <div>'.strtoupper($category).'</div>
            <div class="post-sharing post-sharing-top">
            </div>
              <div class="post-text">
                <h1 class="post-title"><b>AVAILABLE FROM</b><br>'.$providers_id .'</h1>
            </div>


          </div>

	<div class="boxed column small-12 post-content white-bg">
            <h3 class="h1">AUTHORS</h3>
                <div class="post-text" style="font-size:21px">
                        <p>'.$authors_list.'</p>
                </div>
        </div>


'.$block_summary.'



'.$ontology_terms.'



          <div class="boxed column small-12 post-related white-bg">
            <h3 class="h1">RELEVANT DATASETS</h3>'.$related_datasets.'
          </div>
        </div>
      </artilce>
      <!-- end main content -->
      <!-- start sidebar -->
      <aside class="column small-12 medium-3 sidebar">
	<!-- start post access rights -->
        <div class="boxed post-meta post-meta-access-rights white-bg">
          <h3 class="post-meta__title h2">CITATION</h3>
          <div class="post-meta__content">
            
           '.$citation.' 
          </div>
        </div>
        <!-- end post access rights -->

        <!-- start post permalinks -->
        <div class="boxed post-meta post-meta-permalinks white-bg">
          <h3 class="post-meta__title h2">LINKS</h3>'.$permalinks.'
        </div>
        <!-- end post permalinks -->
<!-- start download files -->
	'.$refers_to.'	
        <!-- end download files -->


        <!-- start download files -->
<div class="boxed post-meta post-meta-files white-bg">
          <h3 class="post-meta__title h2">FAIR COMPLIANCE</h2>
          <div class="post-meta__content">
		<center><div id="fair" style="width: 180px; height: 270px; margin: 0 auto"></div></center>
		<center><a href="./metrics.php" target="_blank" class="btn button button-orange">VIEW METRICS</a></center>

          </div>
        </div>
		
        <!-- end download files -->
      </aside>
      <!-- end sidebar -->


    </main>
    <!-- end main -->


<!-- start footer -->
    <footer class="site-footer orange-bg">
      <div class="row site-footer__top">
        <figure class="column small-6 site-footer__logo">
          <a href="./index.php"><img src="assets/img/logo.png" /></a>
        </figure>

        <figure class="column small-6 logo logo-cgiar" style="padding-top:10px">
              <a href="http://bigdata.cgiar.org/" target="_blank"><img style="float:right;width:150px " src="assets2/img/CGIAR_footer.png"></a>
         </figure>

        </div>
      <div class="row site-footer__bottom" style="padding-top:5px">

        <div class="column small-8 text-left">
          <nav class="column small-11 align-self-bottom footer-menu text-left" style="padding-left:0px">
          <ul class="footer-menu__items-wrap clearfix text-left" style="float:left;padding-top:10px">
            <li class="footer-menu__item toggle-lightbox-privacy"><a style="color:white">Privacy</a></li>
            <li class="footer-menu__item toggle-lightbox-disclaimer"><a style="color:white">Disclaimer</a></li>
            <li class="footer-menu__item toggle-lightbox-terms"><a style="color:white">Terms of Use</a></li>
          </ul>
        </nav>

        </div>
<div class="column small-4 text-right"><spam style="position: relative;color:white;top:-15px;">powered by</spam>
<a href="https://scio.systems" target="_blank">
<img style="vertical-align: baseline;" src="https://scio.systems/storage/2018/12/SCIO-sLOGO-WHITE.png" height="40"></a>
</div>




      </div>
    </footer>
    <!-- end footer -->



<div class="lightbox_disclaimer hide">
      <div class="lightbox__content">
        <i class="lightbox__close toggle-lightbox-disclaimer fa fa-times"></i>


<div style="padding-left:20px;padding-right:20px;width:100%;height:100%;overflow: auto">

<br><center><h2>DISCLAIMER NOTICE</h2></center>


<div style="padding:10px;border:1px solid black;">

<p style="text-align: justify;text-justify: inter-word;">
		GARDIAN is an online portal that provides access to agricultural research data produced by <a style="color:blue" target="_blank" href="https://www.cgiar.org/">CGIAR</a> Centers and their implementing partners. It is administered by the <a style="color:blue" target="_blank" href="https://bigdata.cgiar.org/">CGIAR Platform for Big Data in Agriculture</a> (which is co-led by the International Center for Tropical Agriculture and the International Food Policy Research Institute). Access to GARDIAN is provided as a public good in accordance with <a href="https://www.cgiar.org/how-we-work/strategy/" target="_blank" style="color:blue">CGIAR’s mission</a> to reduce poverty, improve food and nutrition security, and improve natural resources and ecosystem services. 
		</p>

<p style="text-align: justify;text-justify: inter-word;">The CGIAR Platform for Big Data in Agriculture is committed to maintaining accurate and current content and promoting responsible data management practices, however, as responsible stewards of donor funds we must mitigate our legal exposure, particularly in relation to third party content that we do not create or control. This notice explains how we disclaim warranties and limit our liability associated with your use of GARDIAN.</p>


<p style="text-align: justify;text-justify: inter-word;"><center><strong>Use of GARDIAN and its contents is subject to our <a href="./terms.php" target="_blank" style="color:blue">TERMS OF USE</a>  which incorporate this DISCLAIMER NOTICE by reference.</strong></center>
<strong><center>PLEASE DO NOT ACCESS OR USE GARDIAN IF YOU DO NOT AGREE TO THESE  TERMS OF USE.</center></strong>
</p>

</div>


<div>
<br><p style="text-align: justify;text-justify: inter-word;">Except to the extent prohibited by law no warranties or guarantees are provided as to the accuracy, reliability, ownership, non-infringement, fitness for a particular purpose, security or any legal or regulatory compliance status of any content or service that is accessible or discoverable through GARDIAN including through websites, platforms, tools or services linked to or associated with GARDIAN. In no event shall the administrators of GARDIAN be liable for any loss or damages of any nature arising in connection with access to or use of any such content except to the extent such liability cannot be excluded or limited under applicable law.</p>


<br><p style="text-align: justify;text-justify: inter-word;">The administrators of GARDIAN promote a culture of open data with minimal to no restrictions on rights of re-use (for example through CC-BY licensing or CC-O public domain designation), however, we do not control and are not responsible for content created by third-parties. It is possible that content accessed or discoverable through GARDIAN is subject to specific terms of use or rights claimed by third-parties, including but not limited to, patent, copyright, other intellectual property rights, privacy protection, informed consent and/or biodiversity-related access and benefit-sharing rights. The user assumes all responsibility regarding the rights of third-parties as may exist in relation to content which is accessible or discoverable through GARDIAN.</p><br>


</div>

</div>

</div>
</div>




<div class="lightbox_terms hide">
      <div class="lightbox__content">
        <i class="lightbox__close toggle-lightbox-terms fa fa-times"></i>





<div style="padding-left:20px;padding-right:20px;width:100%;height:100%;overflow: auto">

<br><center><h2>TERMS OF USE</h2></center>

	<div style="padding:10px;border:1px solid black;">

		<p style="text-align: justify;text-justify: inter-word;">
		GARDIAN is an online portal that provides access to agricultural research data produced by <a style="color:blue" target="_blank" href="https://www.cgiar.org/">CGIAR</a> Centers and their implementing partners. It is administered by the <a style="color:blue" target="_blank" href="https://bigdata.cgiar.org/">CGIAR Platform for Big Data in Agriculture</a> (which is co-led by the International Center for Tropical Agriculture and the International Food Policy Research Institute). Access to GARDIAN is provided as a public good in accordance with <a href="https://www.cgiar.org/how-we-work/strategy/" target="_blank" style="color:blue">CGIAR’s mission</a> to reduce poverty, improve food and nutrition security, and improve natural resources and ecosystem services. 
		</p>
		<br>
		<p style="text-align: justify;text-justify: inter-word;">
		<center><strong>Use of GARDIAN and its contents is subject to these TERMS OF USE which incorporate our <br> <a style="color:blue" target="_blank" href="./privacy.php">PRIVACY NOTICE</a> and</strong><strong> <a href="./disclaimer.php" target="_blank" style="color:blue" >DISCLAIMER NOTICE</a>  by reference.</center></strong>
		<strong><center>PLEASE DO NOT ACCESS OR USE GARDIAN IF YOU DO NOT AGREE TO THESE  TERMS OF USE.</center></strong>

		</p>
		<br>

		<p style="text-align: justify;text-justify: inter-word;"> <i>We may change these TERMS OF USE by updating this page, our <a style="color:blue" target="_blank" href="./privacy.php">PRIVACY NOTICE</a> or our <a href="./disclaimer.php" target="_blank" style="color:blue" >DISCLAIMER NOTICE</a> at any time and it is your responsibility to check you are still willing to be bound by them.</i></p>
		

		
		
		

	</div>

	
	<div>

		<br><strong>YOUR PRIVACY</strong>
		<p style="text-align: justify;text-justify: inter-word;">We respect user privacy and manage any personally identifiable information collected by us from you through GARDIAN in accordance with our <a style="color:blue" target="_blank" href="./privacy.php">PRIVACY NOTICE</a> incorporated by reference herein. 
		</p>
		<br><strong>OUR LIABILITY/DISCLAIMER</strong>
		<p style="text-align: justify;text-justify: inter-word;">We strive to ensure that GARDIAN and its contents are accurate and current. However, in accordance with our <a href="./disclaimer.php" target="_blank" style="color:blue" >DISCLAIMER NOTICE</a> incorporated by reference herein, we do not accept liability for any losses or damages which may arise from using, relying on, or otherwise associated with GARDIAN. 
		</p>
		<br><strong>THIRD-PARTY CONTENT</strong>
		<p style="text-align: justify;text-justify: inter-word;">
	GARDIAN allows users to discover and access content created by third-parties. We are committed to responsible data practices, including through promotion of best practice guidelines and tools made available through GARDIAN. We reserve the right to modify these TERMS OF USE or to adopt policies imposing minimum standards, pledges or representations regarding third party content that is accessed or discoverable through GARDIAN. However, as we do not create or control third party content we assume no responsibility for third-party content accessed or discoverable through GARDIAN as per our <a style="color:blue" target="_blank" href="./privacy.php">PRIVACY NOTICE</a> and <a href="./disclaimer.php" target="_blank" style="color:blue" >DISCLAIMER NOTICE</a>.</p>
		<br><strong>ACCEPTABLE USE</strong>

	<p style="text-align: justify;text-justify: inter-word;">You may not use the GARDIAN or its content in any way that:
		<ul style="padding-left:30px;text-align: justify;text-justify: inter-word;">
		<li>disrupts the normal working of the website</li>
		<li>infringes any copyright, patent, trademark, trade secret, other proprietary rights, or rights of publicity or privacy in any way</li>
		<li>is illegal or otherwise unlawful</li>
		<li>has not been requested, including un-solicited bulk email or spamming</li>
		<li>is obscene, vulgar abusive, menacing or offensive or may cause or result in harassment, harm, distress or inconvenience to any persons or communities</li>
		<li>facilitates the transmission of any virus or other material that could potentially damage the website, and any computers or internet services that are connected to it</li>
		<li>unfairly impairs the reputation of GARDIAN, the organizations responsible for its administration, or any organizations or individuals that have contributed to or made content accessible or discoverable through GARDIAN (this does not include genuine user feedback regarding the site)</li>

		</ul>
	
	<p style="text-align: justify;text-justify: inter-word;">
		You must not attempt to interfere with the website or its workings. In particular, you must not try to break security, tamper with, hack into, or otherwise disrupt any computer system, server, website, router, or any other device.
	
If we become aware of any violation of these TERMS OF USE, we reserve the right to take action to stop the violation. This may include removing information, suspending, blocking or removing a user, and providing relevant information to external bodies where required by law or regulation.</p>

<br><strong>USE OF WEBSITE, IMAGES AND LOGOS ASSOCIATED WITH GARDIAN</strong>
	<p style="text-align: justify;text-justify: inter-word;">
You do not have to ask permission to link to pages hosted on GARDIAN. The images and logos associated with GARDIAN are subject to Copyright. Use of our logos and/or any other third-party logos associated with or accessed through GARDIAN is not permitted without prior approval from the relevant copyright owner. For the avoidance of doubt, the name, website, images and logos associated with GARDIAN may not be used for advertising, product endorsement, or any other purpose without our prior written consent.
	</p>
<br>





</div>
</div>

	</div>

</div>


<div class="lightbox_privacy hide">
      <div class="lightbox__content">
        <i class="lightbox__close toggle-lightbox-privacy fa fa-times"></i> 

<div style=" padding-left:20px;padding-right:20px;width:100%; height:100%; overflow: auto">

<br><center><h2>PRIVACY NOTICE</h2></center>

<div style="padding:10px ;border:1px solid black;">
<p style="text-align: justify;text-justify: inter-word;">GARDIAN is an online portal that provides access to agricultural research data produced by <a style="color:blue" target="_blank" href="https://www.cgiar.org/">CGIAR</a> Centers and their implementing partners. It is administered by the <a style="color:blue" target="_blank" href="https://bigdata.cgiar.org/">CGIAR Platform for Big Data in Agriculture</a> (which is co-led by the International Center for Tropical Agriculture and the International Food Policy Research Institute). Access to GARDIAN is provided as a public good in accordance with <a href="https://www.cgiar.org/how-we-work/strategy/" target="_blank" style="color:blue">CGIAR’s mission</a> to reduce poverty, improve food and nutrition security, and improve natural resources and ecosystem services.</p>
<p style="text-align: justify;text-justify: inter-word;">The <a style="color:blue" target="_blank" href="https://bigdata.cgiar.org/">CGIAR Platform for Big Data in Agriculture</a> is committed to user privacy and this notice explains how we manage personally identifiable information collected from you when you use GARDIAN. It also explains our practices regarding personally identifiable information of third parties which may be accessible or discoverable through GARDIAN.</p>
<br>
<p style="text-align: justify;text-justify: inter-word;"><strong><center>Use of GARDIAN and its contents is subject to our <a style="color:blue" target="_blank" href="./terms.php">TERMS OF USE</a>  which incorporate this PRIVACY NOTICE by reference.</center><center> PLEASE DO NOT ACCESS OR USE GARDIAN IF YOU DO NOT AGREE TO THE <a style="color:blue" target="_blank" href="./terms.php">TERMS OF USE</a> OR THIS PRIVACY NOTICE.</center></strong></p></div>


<div>

<br><strong>COOKIES AND AUTOMATED TRACKING TOOLS</strong> <p style="text-align: justify;text-justify: inter-word;">We use cookies to collect anonymous information and aggregate it to help us better understand how users interact with GARDIAN. Our use of cookies or other automated tracking tools or services does not involve the collection of any personally identifiable information. As an example, we use Google Analytics software to temporarily store and analyze a variety of information about your visit, however, this information cannot be used by us to identify you as an individual. Further information explaining how Google Analytics collects and uses data is available <a href="https://policies.google.com/technologies/partner-sites" style="color:blue" target="_blank">here</a>.</p>
<p style="text-align: justify;text-justify: inter-word;"><em>If you do not wish to have session or persistent cookies placed on your computer, you can disable them at any time from your web browser. If you opt out of cookies, you will still have access to all GARDIAN information and resources, but you may not be able to use cookie-dependent features.</em></p>


</div>

<div>
<br><strong>YOUR PERSONALLY IDENTIFIABLE INFORMATION</strong><p style="text-align: justify;text-justify: inter-word;"> We request personally identifiable information in relation to particular functions or services associated with GARDIAN. For example, to subscribe for updates we require your first and last name, email address, and organization name and type. We respect user privacy; accordingly, any personally identifiable information you provide is protected by SSL encryption when it is exchanged with GARDIAN and will only be used for internal purposes in accordance with site-specific policies, or in ways to which you have explicitly consented. This information is password-protected, accessible only by designated staff  who require this information to perform their duties, and is retained only for as long as reasonably needed for the purpose you have consented to or as required by law. We do not market, sell or transfer personally identifiable information to third parties unless required by law. </p>
</div>


<div>
<br><strong>THIRD-PARTY CONTENT</strong><p style="text-align: justify;text-justify: inter-word;"> GARDIAN allows users to access content created by third-parties. The administrators of GARDIAN promote responsible practices regarding privacy, however, no responsibility is assumed for third-party content that is discoverable or accessible through GARDIAN, including but not limited to compliance with institutional, legal or regulatory requirements concerning privacy protection or the management of personally identifiable information. Further information is available in our <a href="./disclaimer.php" target="_blank" style="color:blue">DISCLAIMER NOTICE</a> .</p>


</div>

<div>
<br><strong>THIRD-PARTY SITES OR SERVICES</strong><p style="text-align: justify;text-justify: inter-word;"> GARDIAN may link to third-party sites or services. Since we do not control them we are not responsible for their privacy practices and you use such sites and services entirely at your own risk. Further information is available in our <a style="color:blue " href="./disclaimer.php" target="_blank">DISCLAIMER NOTICE</a>. We encourage you to review the privacy policies of third-party sites or services if you are sharing any personally identifiable information with them.</p><br>

</div>

</div>
	</div>
</div>






	 <div class="lightbox hide">
      <div class="lightbox__content">
        <i class="lightbox__close toggle-lightbox-2 fa fa-times"></i>
        <div id="large_map" style="width:100%; height: 100%"></div>
      </div>
    </div>



  </body>';


echo $head.$body;
?>
