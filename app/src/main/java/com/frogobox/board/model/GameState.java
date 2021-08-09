package com.frogobox.board.model;

/*
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * BaseGameBoard2048
 * Copyright (C) 02/01/2020.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.basegameboard2048.model
 */

import androidx.annotation.NonNull;

import com.frogobox.board.widget.Element;

import java.io.Serializable;

public class GameState implements Serializable {

    public int n = 4;
    public int points = 0;
    public int last_points = 0;

    public int[] numbers;
    public int[] last_numbers;

    public boolean undo = false;

    public GameState(int size) {
        numbers = new int[size * size];
    }

    public GameState(int[][] e) {
        int length = 1;
        for (int[] ints : e) {
            if (ints.length > length)
                length = ints.length;
        }
        this.n = e.length;
        numbers = new int[e.length * e.length];
        int c = 0;
        for (int[] ints : e) {
            for (int anInt : ints) {
                numbers[c++] = anInt;
            }
        }
        last_numbers = numbers;
    }

    public GameState(Element[][] e, Element[][] e2) {
        int length = 1;

        for (Element[] elements : e) {
            if (elements.length > length)
                length = elements.length;
        }

        this.n = e.length;
        numbers = new int[e.length * e.length];
        int c = 0;

        for (Element[] elements : e) {
            for (Element element : elements) {
                numbers[c++] = element.number;
            }
        }

        length = 1;

        for (Element[] elements : e2) {
            if (elements.length > length)
                length = elements.length;
        }

        last_numbers = new int[e2.length * e2.length];
        c = 0;

        for (Element[] elements : e2) {
            for (Element element : elements) {

                last_numbers[c++] = element.number;
            }
        }
    }

    public int getNumber(int i, int j) {
        try {
            return numbers[i * n + j];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getLastNumber(int i, int j) {

        try {
            return last_numbers[i * n + j];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return 0;
    }


    @NonNull
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("numbers: ");
        for (int i : numbers) {
            result.append(i).append(" ");
        }
        result.append(", n: ").append(n);
        result.append(", points: ").append(points);
        result.append(", undo: ").append(undo);
        return result.toString();
    }

}
