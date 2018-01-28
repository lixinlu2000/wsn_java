nodeNum = [320.0;480.0;640.0;800.0;960.0;1120.0;1280.0;1440.0;1600.0];
x = nodeNum';
IWQE = [1.0;0.75;0.75;0.75;0.7;0.8;0.6;0.55;0.65];
y(:,1) = IWQE';
ESA-GBA = [1.0;0.9;1.0;0.85;1.0;0.95;1.0;1.0;1.0];
y(:,2) = ESA-GBA';
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
;set(bar1,'BarWidth',1);
xlabel('网络节点数目（个）','FontSize', fontSize);
ylabel('查询成功率','FontSize', fontSize);
hl = legend('IWQE','ESA-GBA');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','nodeNum_isSuccess_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','nodeNum_isSuccess_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','nodeNum_isSuccess_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','nodeNum_isSuccess_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','nodeNum_isSuccess_bar_HorizontalLegend.png');
