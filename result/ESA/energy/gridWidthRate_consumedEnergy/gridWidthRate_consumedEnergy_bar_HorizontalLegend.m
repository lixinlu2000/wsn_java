gridWidthRate = [0.56;0.6000000000000001;0.6400000000000001;0.6800000000000002;0.7200000000000002;0.7600000000000002;0.8000000000000003;0.8400000000000003;0.8800000000000003;0.9200000000000004;0.9600000000000004];
x = gridWidthRate';
IWQE = [3624.3879111107317;3580.453688888482;3550.5846222218497;3432.5064888885386;3374.3740444440646;3348.032088888544;3207.10711111078;3210.3836444441326;3168.8693894733424;3165.6048842102005;3166.466273683895];
y(:,1) = IWQE';
ESA-GBA = [2838.264355555453;2763.7209777776693;2772.1839111110235;2703.10151111103;2636.2078222221207;2664.9384888888144;2560.2383111110294;2584.9725333332626;2544.5646315788545;2563.029810526234;2629.7290947367683];
y(:,2) = ESA-GBA';
figure1 = figure;
fontSize = 18;
set(figure1,'Color',[1,1,1]);
axes1 = axes('XTick',gridWidthRate,'Parent',figure1,'Box','on','FontSize', fontSize);
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
xlabel('网格宽度占通信半径的比率','FontSize', fontSize);
ylabel('消耗的能量（mJ）','FontSize', fontSize);
hl = legend('IWQE','ESA-GBA');
%Legend Location North,South,East,West
set(hl,'Orientation','horizontal','Location','North','FontSize', fontSize);
print(gcf,'-depsc','gridWidthRate_consumedEnergy_bar_HorizontalLegend.eps');
print(gcf,'-djpeg','gridWidthRate_consumedEnergy_bar_HorizontalLegend.jpeg');
print(gcf,'-dtiff','gridWidthRate_consumedEnergy_bar_HorizontalLegend.tif');
print(gcf,'-dbitmap','gridWidthRate_consumedEnergy_bar_HorizontalLegend.bmp');
print(gcf,'-dpng','gridWidthRate_consumedEnergy_bar_HorizontalLegend.png');
