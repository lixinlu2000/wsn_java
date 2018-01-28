
proc gsaVSiwqeEnergyBalance {} {
	compare_nodeNum_energyBalance;
	compare_QueryRegionRate_energyBalance;
	compare_answerSize_energyBalance;
	compare_querySize_energyBalance;
}

proc iwqeEnergyBalance {networkID nodeNum queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth 450;
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
	set regionWidth [expr sqrt(3)/2*$radioRange];
	$alg initSubQueryRegionByRegionWidth  $regionWidth;
	$alg init;
	
	set count -1;
	set success 1;
	while {$success == 1} {
		incr count;
		set success [$alg run];
		$alg reInit;
	}	
	
	set algBlackBox [$alg getAlgBlackBox];
	set hasNoEnergy [$algBlackBox isHasNoEnergy];
	
	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";	
	puts -nonewline $fileID $hasNoEnergy;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $count;
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


proc gsaEnergyBalance {networkID nodeNum queryRegionRate answerSize querySize fileID} {
	initLogger "config/log4j.properties";
	set networkWidth 450;
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
	
	set count -1;
	set success 1;
	while {$success == 1} {
		incr count;
		set success [$alg run];
		$alg reInit;
	}	
	
	set algBlackBox [$alg getAlgBlackBox];
	set hasNoEnergy [$algBlackBox isHasNoEnergy];
	

	puts -nonewline $fileID $networkID;
	puts -nonewline $fileID "\t";		
	puts -nonewline $fileID $hasNoEnergy;
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID [energyResult $alg];
	puts -nonewline $fileID "\t";
	puts -nonewline $fileID $count;
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

proc viwqeEnergyBalance {networkID nodeNum queryRegionRate answerSize querySize} {
	initLogger "config/log4j.properties";
	set networkWidth 450;
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
	set regionWidth [expr sqrt(3)/2*$radioRange];
	$alg initSubQueryRegionByRegionWidth  $regionWidth;
	$alg init;
	
	set count -1;
	set success 1;
	while { $success == 1 } {
		incr count;		
		set success [$alg run];
		$alg reInit;
	}	
	
	set algBlackBox [$alg getAlgBlackBox];
	set hasNoEnergy [$algBlackBox isHasNoEnergy];
	
	vputs $hasNoEnergy;
    vputs [energyResult $alg];
	vputs $count;
	
	#set animator [java::new IWQEAnimator $alg];
	#$animator setSpeed 0.5;
	#$animator start;
}


proc vgsaEnergyBalance {networkID nodeNum queryRegionRate answerSize querySize} {
	initLogger "config/log4j.properties";
	set networkWidth 450;
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
	
	set count -1;
	set success 1;
	while {$success == 1} {
		incr count;
		set success [$alg run];
		$alg reInit;
	}	
	
	set algBlackBox [$alg getAlgBlackBox];
	set hasNoEnergy [$algBlackBox isHasNoEnergy];

	vputs $hasNoEnergy;
	vputs [energyResult $alg];
	vputs $count;
	
	set animator [java::new GSAAnimator $alg];
	$animator setSpeed 0.5;
	$animator start;
}


#node num 
proc compare_nodeNum_energyBalance {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	
	set gsaFileName "./result/GSA/gsa_NodeNum_energyBalance.txt";
	set iwqeFileName "./result/GSA/iwqe_NodeNum_energyBalance.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 120;
	set end 600;
	set gap 48;
	for {set nodeNum $start} {$nodeNum<=$end} {incr nodeNum $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqeEnergyBalance $networkID $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsaEnergyBalance  $networkID $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}

#query region rate
proc compare_QueryRegionRate_energyBalance {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	
	set gsaFileName "./result/GSA/gsa_QueryRegionRate_energyBalance.txt";
	set iwqeFileName "./result/GSA/iwqe_QueryRegionRate_energyBalance.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 1;
	set end 10;
	set gap 1;

	for {set queryWidthRate $start} {$queryWidthRate<=$end} {incr queryWidthRate 1} {	
		set queryRegionRate [expr 1.0*$queryWidthRate*$queryWidthRate/100];
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqeEnergyBalance $networkID $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsaEnergyBalance  $networkID $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}

#answer size;
proc compare_answerSize_energyBalance {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	
	set gsaFileName "./result/GSA/gsa_answerSize_energyBalance.txt";
	set iwqeFileName "./result/GSA/iwqe_answerSize_energyBalance.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 10;
	set end 100;
	set gap 10;
	for {set answerSize $start} {$answerSize<=$end} {incr answerSize $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqeEnergyBalance $networkID $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsaEnergyBalance  $networkID $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}

#query size;
proc compare_querySize_energyBalance {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	
	set gsaFileName "./result/GSA/gsa_querySize_energyBalance.txt";
	set iwqeFileName "./result/GSA/iwqe_querySize_energyBalance.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 10;
	set end 100;
	set gap 10;
	for {set querySize $start} {$querySize<=$end} {incr querySize $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqeEnergyBalance $networkID $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsaEnergyBalance  $networkID $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}
	