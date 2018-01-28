function [ output_args ] = f3( as, ab, beta, ar, area, asd )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    syms alpha
    f = ar-as^2*(sin(asd-alpha))^2/(2*(cos(asd-alpha+beta))^2)
    output_args = solve(f)
end


