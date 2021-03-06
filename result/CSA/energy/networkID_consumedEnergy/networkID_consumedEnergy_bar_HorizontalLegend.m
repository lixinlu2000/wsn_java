networkID = [1.0;2.0;3.0;4.0;5.0];
x = networkID';
ASA_CA = [2086.332399999987;2096.583599999968;1807.1843999999764;2257.4687999999687;1954.1775999999836];
y(:,1) = ASA_CA';
ASA_LA = [2147.669599999963;2111.851999999965;1804.0327999999763;2312.5051999999473;1987.0043999999798];
y(:,2) = ASA_LA';
IWQE = [2713.1823999998046;2667.1807999998387;2312.723199999926;2933.9539999997405;2482.8899999998803];
y(:,3) = IWQE';
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
xlabel('����ID','FontSize', fontSize);
ylabel('���ĵ�������mJ��','FontSize', fontSize);
hl = legend('ASA\_CA','ASA\_LA','IWQE');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_consumedEnergy_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_consumedEnergy_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_consumedEnergy_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_consumedEnergy_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_consumedEnergy_bar_HorizontalLegend.png');
