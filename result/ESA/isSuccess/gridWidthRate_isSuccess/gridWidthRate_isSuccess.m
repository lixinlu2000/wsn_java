gridWidthRate = [0.56;0.6000000000000001;0.6400000000000001;0.6800000000000002;0.7200000000000002;0.7600000000000002;0.8000000000000003;0.8400000000000003;0.8800000000000003;0.9200000000000004;0.9600000000000004];
IWQE = [1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0];
ESA-GBA = [1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0;1.0];
figure1 = figure;
%figure1 = figure;
fontSize = 18;
set(figure1,'Color',[1,1,1]);
axes1 = axes('XTick',gridWidthRate,'Parent',figure1,'Box','on','FontSize', fontSize);
xlim(axes1,[min(gridWidthRate),max(gridWidthRate)]);
hold on;
plot(gridWidthRate,IWQE,'r+-','Parent',axes1);
hold on;
plot(gridWidthRate,ESA-GBA,'bo-','Parent',axes1);
xlabel('网格宽度占通信半径的比率','FontSize', fontSize);
ylabel('查询成功率','FontSize', fontSize);
hl = legend('IWQE','ESA-GBA');
set(hl,'Location','Best','FontSize', fontSize);
print(gcf,'-depsc','gridWidthRate_isSuccess.eps');
print(gcf,'-djpeg','gridWidthRate_isSuccess.jpeg');
print(gcf,'-dtiff','gridWidthRate_isSuccess.tif');
print(gcf,'-dbitmap','gridWidthRate_isSuccess.bmp');
print(gcf,'-dpng','gridWidthRate_isSuccess.png');
