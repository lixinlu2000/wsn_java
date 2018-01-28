proc initLogger {configFile} {
	java::call PropertyConfigurator configure $configFile;
}

proc randomNetwork {w h num} {

	set network [java::call Network createRandomNetwork $w $h $num];
	return $network;
}

proc randomNetworkWithID {w h num networkID} {

	set network [java::call Network createRandomNetwork $w $h $num $networkID];
	return $network;
}

proc randomNetworkWithHole {w h num centre radius} {
	set network [java::call Network createRandomNetworkWithHole $w $h $num $centre $radius];
	return $network;
}

proc gridNetwork {w h xgap ygap} {
	set network [java::call Network createGridNetwork $w $h $xgap $ygap];
	return $network;
}

proc rect {x1 y1 x2 y2} {
	set r [java::new Rect $x1 $x2 $y1 $y2];
	return $r;
}

proc query { node rect} {
	set query [java::new Query $node $rect];
	return $query;
}

proc knnQuery { node destPos k} {
	set query [java::new KNNQuery $node $destPos $k];
	return $query;
}

proc simpleAlgorithm {algName name query network} {
	global alg;
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
	#$algorithm init;
	return [$algorithm run];
}

proc statistics {algorithm} {
	return [$algorithm getStatistics];
}

proc energyResult {algorithm} {
	set statistics [statistics $algorithm];
	$statistics getConsumedEnergy;
}