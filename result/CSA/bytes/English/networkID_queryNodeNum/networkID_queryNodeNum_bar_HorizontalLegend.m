networkID = [1.0;2.0;3.0;4.0;5.0;6.0;7.0;8.0;9.0;10.0;11.0;12.0;13.0;14.0;15.0;16.0;17.0;18.0;19.0;20.0];
x = networkID';
ASA_CA = [99.75;98.75;99.25;95.5;93.5;98.0;97.75;97.0;98.25;88.5;100.75;97.0;98.0;97.0;99.25;95.25;91.5;97.0;95.0;97.25];
y(:,1) = ASA_CA';
ASA_LA = [112.5;105.25;103.5;105.75;109.25;105.5;110.5;103.75;109.0;103.0;108.0;106.5;107.25;107.25;107.0;107.5;103.0;101.75;106.75;109.0];
y(:,2) = ASA_LA';
IWQE = [93.5;85.25;89.0;89.25;86.5;85.0;89.5;85.75;89.75;83.25;89.0;88.0;89.75;90.5;90.25;87.75;78.0;83.0;86.25;84.75];
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
xlabel('networkID','FontSize', fontSize);
ylabel('Number of cluster head nodes','FontSize', fontSize);
hl = legend('ASA\_CA','ASA\_LA','IWQE');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','networkID_queryNodeNum_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','networkID_queryNodeNum_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','networkID_queryNodeNum_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','networkID_queryNodeNum_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','networkID_queryNodeNum_bar_HorizontalLegend.png');
