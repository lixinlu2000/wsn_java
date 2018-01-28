nodeNum = [320.0;480.0;640.0;800.0;960.0;1120.0];
x = nodeNum';
ASA_CA = [7136.8421052631575;11552.0;15069.0;18436.0;21750.0;24920.0];
y(:,1) = ASA_CA';
ASA_LA = [7216.8421052631575;11735.0;15229.0;18612.0;21921.0;25116.0];
y(:,2) = ASA_LA';
IWQE = [9468.421052631578;14916.0;18621.0;22209.0;25577.0;28677.0];
y(:,3) = IWQE';
figure1 = figure;
fontSize = 18;
set(figure1,'Color',[1,1,1]);
axes1 = axes('XTick',nodeNum,'Parent',figure1,'Box','on','FontSize', fontSize);
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
xlabel('Number of sensors','FontSize', fontSize);
ylabel('Comm. cost (Number of bytes)','FontSize', fontSize);
hl = legend('ASA\_CA','ASA\_LA','IWQE');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','nodeNum_packetFrameNum_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','nodeNum_packetFrameNum_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','nodeNum_packetFrameNum_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','nodeNum_packetFrameNum_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','nodeNum_packetFrameNum_bar_HorizontalLegend.png');