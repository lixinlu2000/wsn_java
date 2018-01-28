networkID = [1.0;2.0;3.0;7.0;9.0;11.0;12.0;13.0;14.0;15.0;16.0;17.0;21.0;23.0;24.0;28.0;29.0;30.0];
x = networkID';
SWinFlood = [15963.483999999771;17411.507999999754;15605.667999999827;13498.151999999829;17090.107999999836;21429.08799999983;21620.335999999665;14639.779999999819;16823.359999999844;17994.251999999826;16841.943999999723;17272.4159999998;16538.511999999828;18077.163999999808;18184.12799999976;14432.551999999852;18650.04799999981;17444.135999999744];
y(:,1) = SWinFlood';
EcstaFlood = [12550.791999999801;13095.763999999841;13880.451999999854;12016.279999999839;13331.02799999989;15388.07199999991;16740.124000000036;11567.63999999985;12651.927999999845;13424.755999999978;14395.599999999868;13531.227999999903;13177.46399999989;13211.231999999853;14917.459999999874;11257.555999999897;15269.320000000018;14394.047999999775];
y(:,2) = EcstaFlood';
EST_MQP_IDC = [11230.692000001161;11981.028000001581;11004.94000000103;11828.320000001262;12604.384000001666;14065.140000001984;13871.704000002574;10373.684000000692;11453.348000001159;11918.612000000934;12597.200000001436;13348.848000001954;11673.804000001432;10311.044000000998;13361.648000001982;9843.06400000049;13470.636000001754;13175.116000001795];
y(:,3) = EST_MQP_IDC';
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
;set(bar1(3),'facecolor','g')
;set(bar1,'BarWidth',1);
xlabel('网络ID','FontSize', fontSize);
ylabel('消耗的能量（mJ）','FontSize', fontSize);
hl = legend('SWinFlood','EcstaFlood','EST\_MQP\_IDC');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_consumedEnergy_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_consumedEnergy_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_consumedEnergy_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_consumedEnergy_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_consumedEnergy_bar_HorizontalLegend.png');
