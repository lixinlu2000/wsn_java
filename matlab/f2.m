function [ output_args ] = f2( as, ab, beta, ar, area, asd )
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
    syms alpha
    f = (ab/2)*(2*as*sin(alpha)/sin(alpha+beta) - ab/tan(alpha+beta))
    output_args = solve(f)
end

