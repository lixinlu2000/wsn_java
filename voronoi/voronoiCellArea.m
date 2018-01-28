sdata = load('sdata.txt')
[v , c] = voronoin(sdata);
for i = 1 : size(c ,1)
    ind = c{i}';
    tess_area(i,1) = polyarea( v(ind,1) , v(ind,2) );
end
format bank
tess_area