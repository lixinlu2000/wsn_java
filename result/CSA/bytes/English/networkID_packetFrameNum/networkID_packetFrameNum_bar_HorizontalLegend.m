networkID = [1.0;2.0;3.0;4.0;5.0;6.0;7.0;8.0;9.0;10.0;11.0;12.0;13.0;14.0;15.0;16.0;17.0;18.0;19.0;20.0];
x = networkID';
ASA_CA = [18950.0;18630.0;18025.0;18435.0;18140.0;18650.0;19025.0;17465.0;18840.0;18605.0;19215.0;19235.0;18040.0;18790.0;17915.0;17695.0;17870.0;18875.0;19090.0;19480.0];
y(:,1) = ASA_CA';
ASA_LA = [19180.0;18645.0;18105.0;18655.0;18475.0;18830.0;19280.0;17570.0;18960.0;18915.0;19260.0;19315.0;18360.0;19015.0;18045.0;17985.0;18155.0;18840.0;19265.0;19695.0];
y(:,2) = ASA_LA';
IWQE = [22715.0;22075.0;21695.0;22425.0;22135.0;22220.0;22810.0;21480.0;22585.0;22570.0;22990.0;23060.0;21770.0;22720.0;21640.0;21015.0;21115.0;22175.0;22805.0;22835.0];
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
xlabel('Network ID','FontSize', fontSize);
ylabel('Comm. cost (Number of bytes)','FontSize', fontSize);
hl = legend('ASA\_CA','ASA\_LA','IWQE');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_packetFrameNum_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_packetFrameNum_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_packetFrameNum_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_packetFrameNum_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_packetFrameNum_bar_HorizontalLegend.png');
