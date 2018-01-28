networkID = [1.0;2.0;3.0;4.0;5.0;6.0;7.0;8.0;9.0;10.0;11.0;12.0;13.0;14.0;15.0;16.0;17.0;18.0;19.0;20.0];
x = networkID';
IWQE = [0.05460750853242321;0.05653710247349823;0.046263345195729534;0.04697986577181208;0.040892193308550186;0.042105263157894736;0.048109965635738834;0.058419243986254296;0.04844290657439446;0.055944055944055944;0.03691275167785235;0.041379310344827586;0.039285714285714285;0.03873239436619718;0.05985915492957746;0.04081632653061224;0.03333333333333333;0.04452054794520548;0.04436860068259386;0.04745762711864407];
y(:,1) = IWQE';
ESA-GBA = [0.9556313993174061;0.9752650176678446;0.7900355871886121;0.9899328859060402;0.966542750929368;0.9929824561403509;0.9828178694158075;0.9759450171821306;0.9826989619377162;0.9895104895104895;0.9899328859060402;0.996551724137931;0.9821428571428571;0.9683098591549296;0.8274647887323944;0.9863945578231292;0.9866666666666667;0.9931506849315068;0.9863481228668942;0.9796610169491525];
y(:,2) = ESA-GBA';
figure1 = figure;
fontSize = 18;
set(figure1,'Color',[1,1,1]);
axes1 = axes('XTick',networkID,'Parent',figure1,'Box','on','FontSize', fontSize);
xgap = (max(x)-min(x))/size(x,2)/2;
xlim(axes1,[min(x)-xgap max(x)+xgap]);
maxy = max(max(y));
miny = min(min(y));
ygap1 = (maxy - miny)/10;
ygap2 = (maxy - miny)/5;
ylim(axes1,[miny-ygap1 maxy+ygap2]);
hold on;
bar1 = bar(x,y,'Parent',axes1);
set(bar1(1),'facecolor','r')
;set(bar1(2),'facecolor','b')
;set(bar1,'BarWidth',1);
xlabel('网络ID','FontSize', fontSize);
ylabel('查询结果质量','FontSize', fontSize);
hl = legend('IWQE','ESA-GBA');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_queryResultCorrectRate_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_queryResultCorrectRate_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_queryResultCorrectRate_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_queryResultCorrectRate_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_queryResultCorrectRate_bar_HorizontalLegend.png');
