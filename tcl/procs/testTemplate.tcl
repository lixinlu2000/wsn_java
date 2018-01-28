proc gpsrExample {} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE GPSR};
	vputs {initLogger "config/log4j.properties";};
	vputs {set network [randomNetwork 800 600 1000];};
	vputs {set sink [$network getLBNode];};
	vputs {set rect [rect 200 200 400 400];};
	vputs {set query [query $sink $rect];};
	vputs {set param [java::new Param];};
	vputs {set simulator [java::new Simulator];};
	vputs {$simulator addHandleAndTraceEventListener;};
	vputs {set algName "GPSR"; };
	vputs {set statistics [java::new Statistics $algName];};
	vputs {set algorithm [java::new GPSRAlg $query $network $simulator $param $algName $statistics];};
	vputs {$algorithm init;};
	vputs {set na [$sink getAttachment $algName];};
	vputs {set destPos [$rect getCentre];};
	vputs {set message [java::new Message 10 0];};
	vputs {set gpsr [java::new GPSR [java::cast GPSRAttachment $na] $destPos $message $algorithm];};
	vputs {$algorithm run $gpsr;};
	vputs {set animator [animator $algorithm];};
	vputs {$animator start;};
}

proc kNNExample {} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	vputs {initLogger "config/log4j.properties";};
	vputs {set network [randomNetwork 800 600 1000];};
	vputs {set sink [$network getLBNode];};
	vputs {set rect [rect 100 100 600 600];};
	vputs {set centre [java::new Position 300 300];};
	vputs {set k 10;};
	vputs {set query [java::new KNNQuery $sink $centre $k];};
	vputs {set algName "GKNN"; };
	vputs {set alg [guiAlgorithm $algName $algName $query $network];};
	vputs {runAlgorithm $alg;};
	vputs {vputs [energyResult $alg];};
	vputs {set animator [animator $alg];};
	vputs {$animator start;};
}

proc example {} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	vputs {initLogger "config/log4j.properties";};
	vputs {set network [randomNetworkWithID 700 700 700 1];};
	vputs {set sink [$network get2LRTNode];};
	vputs {set rect [rect 200 200 500 500];};
	vputs {set query [query $sink $rect];};
	vputs {set algName "TAG"; };
	vputs {set alg [guiAlgorithm $algName $algName $query $network];};
	vputs {$alg init;};
	vputs {runAlgorithm $alg;};
	vputs {vputs [energyResult $alg];};
	vputs {set animator [animator $alg];};
	vputs {$animator start;};
}

proc animate {algName} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	initLogger "config/log4j.properties";
	set network [randomNetwork 700 700 500];
	set sink [$network get2LRTNode];
	set rect [rect 100 100 500 500];
	set query [query $sink $rect];
	set algName $algName;
	set alg [guiAlgorithm $algName $algName $query $network];
	$alg init;
	runAlgorithm $alg;
	vputs [energyResult $alg];
	set animator [animator $alg];
	$animator start;
	return $animator;
}

proc iwqeExample {} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	vputs {initLogger "config/log4j.properties";};
	vputs {set network [randomNetwork 800 600 1500];};
	vputs {set sink [$network get2LRTNode];};
	vputs {set rect [rect 200 150 600 450];};
	vputs {set query [query $sink $rect];};
	vputs {set algName "IWQE"; }; 
	vputs {set alg [guiAlgorithm $algName $algName $query $network];};
	vputs {runAlgorithm $alg;};
	vputs {vputs [energyResult $alg];};
	vputs {set animator [animator $alg];};
	vputs {$animator start;};
}

proc gsaExample {} {
	vputs {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	vputs {initLogger "config/log4j.properties";};
	vputs {set network [randomNetwork 800 600 1500];};
	vputs {set sink [$network get2LRTNode];};
	vputs {set rect [rect 200 150 600 450];};
	vputs {set query [query $sink $rect];};
	vputs {set algName "GSA"; }; 
	vputs {set alg [guiAlgorithm $algName $algName $query $network];};
	vputs {#$alg initGrid 10 12;};
	vputs {runAlgorithm $alg;};
	vputs {vputs [energyResult $alg];};
	vputs {set animator [animator $alg];};
	vputs {$animator start;};
}
