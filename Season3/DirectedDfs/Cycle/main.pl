#!/usr/bin/env perl

use 5.011;
use strict;
use warnings;

open STDIN, "<cycle.in";
open STDOUT, ">cycle.out";

my ($n, $m) = split " ", <>;
my @g;
for (my $i = 0; $i < $n; $i++) {
    $g[$i] = [];
}

for (my $i = 0; $i < $m; $i++) {
    my ($u, $v) = split " ", <>;
    $u--;
    $v--;
    push @{$g[$u]}, $v
}

my @used;
my @pos;
my @s;

for (my $i = 0; $i < $n; $i++) {
    if (!defined($pos[$i])) {
        dfs($i);
    }
}

say "NO";

sub dfs {
    my $v = shift;
    if (defined($pos[$v]) && $pos[$v] != -1) {
        say "YES";
        for my $x ($pos[$v] .. @s - 1) {
            print($x + 1, " ");
        }
        print "\n";
        exit 0;
    }
    $pos[$v] = @s;
    push @s, $v;
    for my $to (@{$g[$v]}) {
        dfs($to);
    }
    $pos[$v] = -1;
}
