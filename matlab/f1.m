function [ output_args ] = f1( as, ab, beta, ar, area, asd)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    syms alpha
    f = as^2*(sin(alpha))^2/(2*(cos(alpha+beta))^2)
    output_args = solve(f)
end

