networkID = [1.0;2.0;3.0;4.0;5.0;6.0;7.0;8.0;9.0;10.0;11.0;12.0;13.0;14.0;15.0;16.0;17.0;18.0;19.0;20.0];
x = networkID';
IWQE = [1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0];
y(:,1) = IWQE';
ESA-GBA = [1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0];
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
xlabel('����ID','FontSize', fontSize);
ylabel('��ѯ�ɹ���','FontSize', fontSize);
hl = legend('IWQE','ESA-GBA');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_isSuccess_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_isSuccess_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_isSuccess_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_isSuccess_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_isSuccess_bar_HorizontalLegend.png');
