# Prevent Octave from thinking that this is a function file:
1;
global n = 0.02;
global use_momentum = true;
global use_adaptative_eta = true;
global percentage_error_for_adaptative_eta = 3.5;
global a_for_adaptative_eta = 0.005;
global b_for_adaptative_eta = 0.25;
global steps_for_adaptative_eta = 5;
global momentum_alpha = 0.9;
global activation_function = 0; # 0 for hyperbolic tangent or 1 for exponencial.
global data = dlmread("terrain04.data"); # Reads data and stores it in a matrix.
global number_of_cases = floor(0.7*(rows(data)-1));
architecture = [2, 50, 1]; # Each column specifies the amount of neurons in a layer.
global max_layer_neurons = max(architecture);

# Activation function.
function ret = sig_exp(z)
  ret = 1 ./ (1 + e.^-z);
endfunction

# Starting.
weight_matrices = init(architecture);
incremental(weight_matrices,number_of_cases);
