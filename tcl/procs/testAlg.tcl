proc iwqe {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "IWQE"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new IWQEAlg $query $network $simulator $param $algName $statistics];
	set iwqeParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $iwqeParam;
	set radioRange [$param getRADIO_RANGE];
	
	#set subRegionWidth [expr sqrt(3)/2*$radioRange];
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	
	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";	
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
	
	#set animator [java::new IWQEAnimator $alg];
	#$animator setSpeed 0.01;
	#$animator start;
}

proc viwqe {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "IWQE"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new IWQEAlg $query $network $simulator $param $algName $statistics];
	set iwqeParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $iwqeParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];

	if {$showAnimator==1} {
		set animator [java::new IWQEAnimator $alg];
		$animator setSpeed $speed;	
		$animator start;
		return $animator;
	}	
}

proc viwqeItinerary {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "IWQE"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new IWQEAlg $query $network $simulator $param $algName $statistics];
	set iwqeParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $iwqeParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];

	if {$showAnimator==1} {
		set animator [java::new IWQEItineraryAnimator $alg];
		$animator setSpeed $speed;	
		$animator start;
		return $animator;
	}	
}


proc dgsa {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "DGSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new DGSAAlg $query $network $simulator $param $algName $statistics];
	set dgsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	
	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";	
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
	
	#set animator [java::new DGSAAnimator $alg];
	#$animator setSpeed 0.01;
	#$animator start;
}

proc vdgsa {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "DGSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new DGSAAlg $query $network $simulator $param $algName $statistics];
	set dgsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new DGSAAnimator $alg];
		$animator setSpeed $speed;	
		$animator start;
		return $animator;
	}	
}

proc dgsaNew {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "DGSA_NEW"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new DGSANewAlg $query $network $simulator $param $algName $statistics];
	set dgsaNewParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaNewParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	
	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";	
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
	
	#set animator [java::new DGSANewAnimator $alg];
	#$animator setSpeed 0.01;
	#$animator start;
}

proc vdgsaNew {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "DGSA_NEW"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new DGSANewAlg $query $network $simulator $param $algName $statistics];
	set dgsaNewParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaNewParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new DGSANewAnimator $alg];
		$animator setSpeed $speed;	
		#$animator start;
		return $animator;
	}	
}

proc vdgsaNewItinerary {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "DGSA_NEW"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new DGSANewAlg $query $network $simulator $param $algName $statistics];
	set dgsaNewParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaNewParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new DGSANewItineraryAnimator $alg];
		$animator setSpeed $speed;	
		#$animator start;
		return $animator;
	}	
}

proc vdgsaNewGridTest {networkSize xgap ygap subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [gridNetwork $networkWidth $networkHeight $xgap $ygap];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "DGSA_NEW"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new DGSANewAlg $query $network $simulator $param $algName $statistics];
	set dgsaNewParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaNewParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new DGSANewAnimator $alg];
		$animator setSpeed $speed;	
		$animator start;
		return $animator;
	}	
}

proc rsa {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "RSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new RSAAlg $query $network $simulator $param $algName $statistics];
	set dgsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaParam;
	set radioRange [$param getRADIO_RANGE];
	
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	
	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";	
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
}

proc vrsa {networkID networkSize nodeNum subRegionWidthRate queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10+2];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "RSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new RSAAlg $query $network $simulator $param $algName $statistics];
	set dgsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $dgsaParam;
	
	set radioRange [$param getRADIO_RANGE];
	set subRegionWidth [expr $subRegionWidthRate*$radioRange];
	$alg initSubQueryRegionAndGridsByRegionWidth  $subRegionWidth;
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
    vputs [energyResult $alg];

	if {$showAnimator==1} {
		set animator [java::new RSAAnimator $alg];
		$animator setSpeed $speed;	
		$animator start;
		return $animator;
	}	
}

proc gsa {networkID networkSize nodeNum queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new GSAAlg $query $network $simulator $param $algName $statistics];
	set gsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $gsaParam;
	set radioRange [$param getRADIO_RANGE];
	set gridWidth [expr sqrt(5)/5*$radioRange];
	set gridHeight $gridWidth; 
	$alg initGridWithGridSize $gridWidth $gridHeight;
	$alg init;
	set success [runAlgorithm $alg];

	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";		
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
	
	#set animator [java::new GSAAnimator $alg];
	#$animator setSpeed 0.01;
	#$animator start;
}

proc vgsa {networkID networkSize nodeNum queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new GSAAlg $query $network $simulator $param $algName $statistics];
	set gsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $gsaParam;
	set radioRange [$param getRADIO_RANGE];
	set gridWidth [expr sqrt(5)/5*$radioRange];
	set gridHeight $gridWidth; 
	$alg initGridWithGridSize $gridWidth $gridHeight;
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
	vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new GSAAnimator $alg];
		$animator setSpeed $speed;
		$animator start;
		return $animator;
	}

}



proc vgsaItinerary {networkID networkSize nodeNum queryRegionRate answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GSA"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new GSAAlg $query $network $simulator $param $algName $statistics];
	set gsaParam [java::new ItineraryAlgParam $answerSize $querySize];
	$alg setAlgParam $gsaParam;
	set radioRange [$param getRADIO_RANGE];
	set gridWidth [expr sqrt(5)/5*$radioRange];
	set gridHeight $gridWidth; 
	$alg initGridWithGridSize $gridWidth $gridHeight;
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
	vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new GSAItineraryAnimator $alg];
		$animator setSpeed $speed;
		$animator start;
		return $animator;
	}

}

proc gknn {networkID networkSize nodeNum k answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set networkRect [$network getRect];
	set destPos [$networkRect getCentre];
	set sink [$network get2LRTNode];
	set query [knnQuery $sink $destPos k];
	set param [java::new Param];
	$param setQUERY_MESSAGE_SIZE $querySize;
	$param setANSWER_SIZE $answerSize;
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GKNN"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new GKNNAlg $query $network $simulator $param $algName $statistics];
	$alg init;
	set success [runAlgorithm $alg];

	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";		
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
	
	#set animator [java::new GSAAnimator $alg];
	#$animator setSpeed 0.01;
	#$animator start;
}

proc vgknn {networkID networkSize nodeNum k answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set networkRect [$network getRect];
	set destPos [$networkRect getCentre];
	set sink [$network get2LRTNode];
	set query [knnQuery $sink $destPos $k];
	set param [java::new Param];
	$param setQUERY_MESSAGE_SIZE $querySize;
	$param setANSWER_SIZE $answerSize;
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GKNN"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new GKNNAlg $query $network $simulator $param $algName $statistics];
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
	vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new GKNNAnimator $alg];
		$animator setSpeed $speed;
		$animator start;
		return $animator;
	}

}

proc iknn {networkID networkSize nodeNum k answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set networkRect [$network getRect];
	set destPos [$networkRect getCentre];
	set sink [$network get2LRTNode];
	set query [knnQuery $sink $destPos k];
	set param [java::new Param];
	$param setQUERY_MESSAGE_SIZE $querySize;
	$param setANSWER_SIZE $answerSize;
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "IKNN"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new IKNNAlg $query $network $simulator $param $algName $statistics];
	$alg init;
	set success [runAlgorithm $alg];

	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";		
	puts -nonewline $fileID $success;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $nodeNum;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $queryRegionRate;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $answerSize;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $querySize;
	puts -nonewline $fileID "\n";
	
	#set animator [java::new GSAAnimator $alg];
	#$animator setSpeed 0.01;
	#$animator start;
}

proc viknn {networkID networkSize nodeNum k answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set networkRect [$network getRect];
	set destPos [$networkRect getCentre];
	set sink [$network get2LRTNode];
	set query [knnQuery $sink $destPos $k];
	set param [java::new Param];
	$param setQUERY_MESSAGE_SIZE $querySize;
	$param setANSWER_SIZE $answerSize;
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "IKNN"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new IKNNAlg $query $network $simulator $param $algName $statistics];
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
	vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new Animator $alg];
		$animator setSpeed $speed;
		$animator start;
		return $animator;
	}
}

proc vgknnUseEvent {networkID networkSize nodeNum k answerSize querySize showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set networkRect [$network getRect];
	set destPos [$networkRect getCentre];
	set sink [$network get2LRTNode];
	set query [knnQuery $sink $destPos $k];
	set param [java::new Param];
	$param setQUERY_MESSAGE_SIZE $querySize;
	$param setANSWER_SIZE $answerSize;
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GKNNUseGridTraverseRingEvent"; 
	set statistics [java::new Statistics $algName];
	set alg [java::new GKNNUseGridTraverseRingEventAlg $query $network $simulator $param $algName $statistics];
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
	vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new Animator $alg];
		$animator setSpeed $speed;
		$animator start;
		return $animator;
	}
}

proc gknnTest {} {
	initLogger "config/log4j.properties";
	set network [randomNetwork 800 600 1000];
	set sink [$network get2LRTNode];
	set rect [rect 200 150 600 450];
	set algName "GKNN";
	set centre [java::new Position 300 300];
	set k 100;
	set query [java::new KNNQuery $sink $centre $k];
	set alg [guiAlgorithm $algName $algName $query $network];
	runAlgorithm $alg;
	vputs [energyResult $alg];
	set animator [java::new GKNNAnimator $alg];
	$animator start;
	return $animator;
}

proc gpsr {} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE GPSR};
	initLogger "config/log4j.properties";
	set network [randomNetwork 800 600 1000];
	set sink [$network getLBNode];
	set rect [rect 200 200 400 400];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	set algName "GPSR"; 
	set statistics [java::new Statistics $algName];
	set algorithm [java::new GPSRAlg $query $network $simulator $param $algName $statistics];
	$algorithm init;
	set na [$sink getAttachment $algName];
	set destPos [$rect getCentre];
	set message [java::new Message 10 0];
	set gpsr [java::new GPSR [java::cast GPSRAttachment $na] $destPos $message $algorithm];
	$algorithm run $gpsr;
	set animator [animator $algorithm];
	$animator start;
	return $animator;
}

proc vBroadcastAlg {algName networkID networkSize nodeNum queryRegionRate showAnimator speed} {
	initLogger "config/log4j.properties";
	set networkWidth $networkSize;
	#set networkWidth [expr sqrt(3)/2*50*10];
	set networkHeight $networkWidth;
	set network [randomNetworkWithID $networkWidth $networkHeight $nodeNum $networkID];
	set sink [$network get2LRTNode];
	set queryRegionWidth [expr sqrt($networkWidth*$networkHeight*$queryRegionRate)];
	set minx [expr ($networkWidth-$queryRegionWidth)/2.0];
	set miny [expr ($networkHeight-$queryRegionWidth)/2.0];
	set maxx [expr ($networkWidth+$queryRegionWidth)/2.0];
	set maxy [expr ($networkHeight+$queryRegionWidth)/2.0];
	set rect [rect $minx $miny $maxx $maxy];
	set query [query $sink $rect];
	set param [java::new Param];
	set simulator [java::new Simulator];
	$simulator addHandleAndTraceEventListener;
	#TAG SWinFlood SWinDepth Ecsta GStar IWQE
	set alg [guiAlgorithm $algName $algName $query $network];
	$alg init;
	set success [runAlgorithm $alg];
	vputs $success;
	vputs [energyResult $alg];
	
	if {$showAnimator==1} {
		set animator [java::new Animator $alg];
		$animator setSpeed $speed;
		$animator start;
		return $animator;
	}

}

proc vtag {networkID networkSize nodeNum queryRegionRate showAnimator speed} {
	set algName "TAG"; 
	#TAG SWinFlood SWinDepth Ecsta GStar IWQE
	vBroadcastAlg $algName $networkID $networkSize $nodeNum $queryRegionRate $showAnimator $speed
}

proc vSWinFlood {networkID networkSize nodeNum queryRegionRate showAnimator speed} {
	set algName "SWinFlood"; 
	#TAG SWinFlood SWinDepth Ecsta GStar IWQE
	vBroadcastAlg $algName $networkID $networkSize $nodeNum $queryRegionRate $showAnimator $speed
}

proc vEcsta {networkID networkSize nodeNum queryRegionRate showAnimator speed} {
	set algName "Ecsta"; 
	#TAG SWinFlood SWinDepth Ecsta GStar IWQE
	vBroadcastAlg $algName $networkID $networkSize $nodeNum $queryRegionRate $showAnimator $speed
}





