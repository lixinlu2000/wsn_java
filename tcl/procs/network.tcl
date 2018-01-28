proc initLogger {configFile} {
	java::call PropertyConfigurator configure $configFile;
}

proc randomNetwork {w h num} {
	set network [java::call Network createRandomNetwork $w $h $num];
	return $network;
}

proc randomNetworkWithHole {w h num centre radius} {
	set network [java::call Network createRandomNetworkWithHole $w $h $num $centre $radius];
	return $network;
}

proc rect {x1 y1 x2 y2} {
	set r [java::new Rect $x1 $x2 $y1 $y2];
	return $r;
}

proc centre {rect} {
}

proc query { node rect} {
	set query [java::new Query $node $rect];
	return $query;
}

proc knnQuery { ode destPos k} {
	set query [java::new KNNQuery $node $destPos $k];
	return $query;
}

proc simpleAlgorithm {algName name query network} {
	set af [java::call AlgorithmFactory getInstance];
	set alg [$af createSimpleAlgorithm $algName $name $query $network];
	return $alg;
}

proc guiAlgorithm {algName name query network} {
	set af [java::call AlgorithmFactory getInstance];
	set alg [$af createGuiAlgorithm $algName $name $query $network];
	return $alg;
}

proc animator {algorithm} {
	set animator [java::new Animator $algorithm];
	return $animator;
}


proc runAlgorithm {algorithm} {
	$algorithm init;
	$algorithm run;
}

proc example {} {
	puts {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	puts {initLogger "config/log4j.properties";};
	puts {set network [randomNetwork 800 600 1000];};
	puts {set sink [$network getLBNode];};
	puts {set rect [rect 200 200 400 400];};
	puts {set query [query $sink $rect];};
	puts {set algName "TAG"; }; 
	puts {set alg [guiAlgorithm $algName $algName $query $network];};
	puts {runAlgorithm $alg;};
	puts {set animator [animator $alg];};
	puts {$animator start;};
}

proc iwqeExample {} {
	puts {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	puts {initLogger "config/log4j.properties";};
	puts {set network [randomNetwork 800 600 3000];};
	puts {set sink [$network get2LRTNode];};
	puts {set rect [rect 200 150 600 450];};
	puts {set query [query $sink $rect];};
	puts {set algName "IWQE"; }; 
	puts {set alg [guiAlgorithm $algName $algName $query $network];};
	puts {runAlgorithm $alg;};
	puts {set animator [animator $alg];};
	puts {$animator start;};
}

proc iwqe {} {
	puts {#TAG SWinFlood SWinDepth Ecsta GStar IWQE};
	initLogger "config/log4j.properties";
	set network [randomNetwork 800 600 3000];
	set sink [$network get2LRTNode];
	set rect [rect 200 150 600 450];
	set query [query $sink $rect];
	set algName "IWQE";
	set alg [guiAlgorithm $algName $algName $query $network];
	runAlgorithm $alg;
	set animator [animator $alg];
	$animator start;
}

