networkID = [];
IWQE = [];
RESA_LA = [];
RESA_CA = [];
figure1 = figure;
%figure1 = figure;
fontSize = 18;
set(figure1,'Color',[1,1,1]);
axes1 = axes('XTick',networkID,'Parent',figure1,'Box','on','FontSize', fontSize);
xlim(axes1,[min(networkID),max(networkID)]);
hold on;
plot(networkID,IWQE,'r+-','Parent',axes1);
hold on;
plot(networkID,RESA_LA,'bo-','Parent',axes1);
hold on;
plot(networkID,RESA_CA,'gs-','Parent',axes1);
xlabel('网络ID','FontSize', fontSize);
ylabel('发送的数据包数目（个）','FontSize', fontSize);
hl = legend('IWQE','RESA\_LA','RESA\_CA');
set(hl,'Location','Best','FontSize', fontSize);
print(gcf,'-depsc','networkID_packetFrameNum.eps');
print(gcf,'-djpeg','networkID_packetFrameNum.jpeg');
print(gcf,'-dtiff','networkID_packetFrameNum.tif');
print(gcf,'-dbitmap','networkID_packetFrameNum.bmp');
print(gcf,'-dpng','networkID_packetFrameNum.png');
