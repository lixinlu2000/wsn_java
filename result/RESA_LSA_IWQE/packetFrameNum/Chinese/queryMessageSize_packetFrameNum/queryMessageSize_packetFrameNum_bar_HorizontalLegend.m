queryMessageSize = [];
x = queryMessageSize';
IWQE = [];
y(:,1) = IWQE';
RESA_LA = [];
y(:,2) = RESA_LA';
RESA_CA = [];
y(:,3) = RESA_CA';
figure1 = figure;
fontSize = 18;
set(figure1,'Color',[1,1,1]);
axes1 = axes('XTick',queryMessageSize,'Parent',figure1,'Box','on','FontSize', fontSize);
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
xlabel('查询消息大小（字节）','FontSize', fontSize);
ylabel('发送的数据包数目（个）','FontSize', fontSize);
hl = legend('IWQE','RESA\_LA','RESA\_CA');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','queryMessageSize_packetFrameNum_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','queryMessageSize_packetFrameNum_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','queryMessageSize_packetFrameNum_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','queryMessageSize_packetFrameNum_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','queryMessageSize_packetFrameNum_bar_HorizontalLegend.png');
