package com.marius.dagenstegneserie.parsers;

import com.marius.dagenstegneserie.Cartoon;

public interface CartoonProvider {

    String findUrlFor(Cartoon cartoon);
}
