
proc gsaVSiwqeEnergy {} {
	compare_nodeNum_energy;
	compare_QueryRegionRate_energy;
	compare_answerSize_energy;
	compare_querySize_energy;
}

proc iwqeVSdgsaVSrsa {} {
	set networkNum 5;
	set queryRegionRate 0.36;
	set answerSize 40;
	set querySize 20;
	set nodeNum 400;
	set networkSize 450;
	
	set start 400;
	set end 1200;
	set gap 400;
	
	for {set nodeNum $start} {$nodeNum<=$end} {incr nodeNum $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
			vputs IWQE;
			viwqe $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize 0 1;
			vputs DGSA;
			vdgsa $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize 0 1;
			vputs RSA;
			vrsa $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize 0 1;
		}
	}
}

#node num 
proc compare_nodeNum_energy {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	set networkSize 450;
	
	set gsaFileName "./result/GSA/gsa_NodeNum.txt";
	set iwqeFileName "./result/GSA/iwqe_NodeNum.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 120;
	set end 600;
	set gap 48;
	for {set nodeNum $start} {$nodeNum<=$end} {incr nodeNum $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqe $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsa  $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}

#query region rate
proc compare_QueryRegionRate_energy {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	set networkSize 450;
	
	set gsaFileName "./result/GSA/gsa_QueryRegionRate.txt";
	set iwqeFileName "./result/GSA/iwqe_QueryRegionRate.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 1;
	set end 10;
	set gap 1;

	for {set queryWidthRate $start} {$queryWidthRate<=$end} {incr queryWidthRate 1} {	
		set queryRegionRate [expr 1.0*$queryWidthRate*$queryWidthRate/100];
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqe $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsa  $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}

#answer size;
proc compare_answerSize_energy {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	set networkSize 450;
		
	set gsaFileName "./result/GSA/gsa_answerSize.txt";
	set iwqeFileName "./result/GSA/iwqe_answerSize.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 10;
	set end 100;
	set gap 10;
	for {set answerSize $start} {$answerSize<=$end} {incr answerSize $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqe $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsa  $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}

#query size;
proc compare_querySize_energy {} {
	set networkNum 100;
	set queryRegionRate 0.36;
	set answerSize 30;
	set querySize 20;
	set nodeNum 480;
	set networkSize 450;
		
	set gsaFileName "./result/GSA/gsa_querySize.txt";
	set iwqeFileName "./result/GSA/iwqe_querySize.txt";
	set gsaFileID [open $gsaFileName "w"];
	set iwqeFileID [open $iwqeFileName "w"];
	set start 10;
	set end 100;
	set gap 10;
	for {set querySize $start} {$querySize<=$end} {incr querySize $gap} {	
	
		for {set networkID 1} {$networkID<=$networkNum} {incr networkID 1} {
		   iwqe $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $iwqeFileID;
		   gsa  $networkID $networkSize $nodeNum $queryRegionRate $answerSize $querySize $gsaFileID;
		}
	}
	close $iwqeFileID;
	close $gsaFileID;
}
	