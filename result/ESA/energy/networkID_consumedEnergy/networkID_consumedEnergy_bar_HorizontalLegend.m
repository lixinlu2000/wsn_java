networkID = [];
x = networkID';
IWQE = [];
y(:,1) = IWQE';
ESA-GBA = [];
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
ylabel('消耗的能量（mJ）','FontSize', fontSize);
hl = legend('IWQE','ESA-GBA');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_consumedEnergy_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_consumedEnergy_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_consumedEnergy_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_consumedEnergy_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_consumedEnergy_bar_HorizontalLegend.png');
